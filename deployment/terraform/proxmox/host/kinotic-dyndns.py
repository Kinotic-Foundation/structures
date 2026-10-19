#!/opt/certbot/bin/python3
"""Keeps the host's public hostnames on the router's current public address. The ISP hands
out the address by DHCP, so kinotic-dyndns.timer runs this every few minutes: it asks an echo
service for the address and writes it into the A record of every name the host's certificates
carry in the platform zone, through the Cloudflare API with the DNS token in certbot's dns-cloudflare
credentials file, which sync-secrets.sh writes from kinotic-servers.env.

A record already at the address is left alone. The app server's names are in the customers'
zone, proxied records the dev-server terraform root owns, so they are not touched.
"""
import configparser
import glob
import json
import sys
import urllib.parse
import urllib.request

from cryptography import x509

CERTBOT_INI = "/etc/kinotic/certbot-cloudflare.ini"
LINEAGES = "/etc/letsencrypt/live/*/cert.pem"
ECHO = "https://api.ipify.org"
API = "https://api.cloudflare.com/client/v4"
# the platform zone, where the management and system servers' names are (global/ domain_name)
PLATFORM_ZONE = "kinotic.ai"


def token():
    parser = configparser.ConfigParser()
    with open(CERTBOT_INI) as f:
        parser.read_string("[certbot]\n" + f.read())
    return parser["certbot"]["dns_cloudflare_api_token"]


def hostnames():
    names = set()
    for path in glob.glob(LINEAGES):
        with open(path, "rb") as f:
            cert = x509.load_pem_x509_certificate(f.read())
        names.update(cert.extensions.get_extension_for_class(x509.SubjectAlternativeName).value.get_values_for_type(x509.DNSName))
    return sorted(names)


def public_address():
    with urllib.request.urlopen(ECHO, timeout=15) as response:
        return response.read().decode().strip()


def cloudflare(bearer, method, path, body=None, **query):
    url = API + path + ("?" + urllib.parse.urlencode(query) if query else "")
    data = json.dumps(body).encode() if body is not None else None
    request = urllib.request.Request(url, data=data, method=method,
                                     headers={"Authorization": "Bearer " + bearer, "Content-Type": "application/json"})
    with urllib.request.urlopen(request, timeout=30) as response:
        answer = json.load(response)
    if not answer.get("success"):
        sys.exit(f"Cloudflare {method} {path}: {answer.get('errors')}")
    return answer["result"]


if __name__ == "__main__":
    names = [n for n in hostnames() if n.endswith("." + PLATFORM_ZONE)]
    if not names:
        sys.exit("no certificate for a name in " + PLATFORM_ZONE)
    bearer = token()
    zones = cloudflare(bearer, "GET", "/zones", name=PLATFORM_ZONE)
    if not zones:
        sys.exit("the token sees no zone named " + PLATFORM_ZONE)
    zone_id = zones[0]["id"]
    address = public_address()
    for name in names:
        records = cloudflare(bearer, "GET", f"/zones/{zone_id}/dns_records", type="A", name=name)
        for record in records:
            if record["content"] == address:
                continue
            cloudflare(bearer, "PATCH", f"/zones/{zone_id}/dns_records/{record['id']}", {"content": address})
            print(f"{name}: {record['content']} -> {address}")
