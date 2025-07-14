#!/bin/bash

# Keycloak Setup Script for Structures
# This script configures Keycloak with a client for the Structures application

set -e

echo "Setting up Keycloak for Structures..."

# Wait for Keycloak to be ready
echo "Waiting for Keycloak to be ready..."
echo "This may take a few minutes on first startup..."

# First wait for the port to be available
until curl -f http://localhost:8888/auth/health/ready >/dev/null 2>&1; do
    echo "Waiting for Keycloak to be ready..."
    sleep 10
done

echo "Keycloak is ready!"

echo "Keycloak is ready!"

# Get admin token
echo "Getting admin token..."
ADMIN_TOKEN=$(curl -s -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=admin&password=admin&grant_type=password&client_id=admin-cli" \
    http://localhost:8888/auth/realms/master/protocol/openid-connect/token | \
    jq -r '.access_token')

if [ "$ADMIN_TOKEN" = "null" ] || [ -z "$ADMIN_TOKEN" ]; then
    echo "Failed to get admin token"
    exit 1
fi

echo "Admin token obtained successfully"

# Create client for Structures
echo "Creating Structures client..."

CLIENT_RESPONSE=$(curl -s -X POST \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "clientId": "structures-client",
        "name": "Structures Application",
        "description": "Structures frontend application",
        "enabled": true,
        "publicClient": true,
        "standardFlowEnabled": true,
        "directAccessGrantsEnabled": false,
        "serviceAccountsEnabled": false,
        "redirectUris": [
            "http://localhost:5173/login",
            "http://localhost:5173/login/silent-renew"
        ],
        "webOrigins": [
            "http://localhost:5173"
        ],
        "attributes": {
            "saml.assertion.signature": "false",
            "saml.force.post.binding": "false",
            "saml.multivalued.roles": "false",
            "saml.encrypt": "false",
            "saml.server.signature": "false",
            "saml.server.signature.keyinfo.ext": "false",
            "exclude.session.state.from.auth.response": "false",
            "saml_force_name_id_format": "false",
            "saml.client.signature": "false",
            "tls.client.certificate.bound.access.tokens": "false",
            "saml.authnstatement": "false",
            "display.on.consent.screen": "false",
            "saml.onetimeuse.condition": "false"
        },
        "protocol": "openid-connect",
        "protocolMappers": [
            {
                "name": "email",
                "protocol": "openid-connect",
                "protocolMapper": "oidc-usermodel-property-mapper",
                "config": {
                    "userinfo.token.claim": "true",
                    "user.attribute": "email",
                    "id.token.claim": "true",
                    "access.token.claim": "true",
                    "claim.name": "email",
                    "jsonType.label": "String"
                }
            },
            {
                "name": "name",
                "protocol": "openid-connect",
                "protocolMapper": "oidc-usermodel-property-mapper",
                "config": {
                    "userinfo.token.claim": "true",
                    "user.attribute": "firstName",
                    "id.token.claim": "true",
                    "access.token.claim": "true",
                    "claim.name": "given_name",
                    "jsonType.label": "String"
                }
            },
            {
                "name": "family name",
                "protocol": "openid-connect",
                "protocolMapper": "oidc-usermodel-property-mapper",
                "config": {
                    "userinfo.token.claim": "true",
                    "user.attribute": "lastName",
                    "id.token.claim": "true",
                    "access.token.claim": "true",
                    "claim.name": "family_name",
                    "jsonType.label": "String"
                }
            },
            {
                "name": "preferred_username",
                "protocol": "openid-connect",
                "protocolMapper": "oidc-usermodel-property-mapper",
                "config": {
                    "userinfo.token.claim": "true",
                    "user.attribute": "username",
                    "id.token.claim": "true",
                    "access.token.claim": "true",
                    "claim.name": "preferred_username",
                    "jsonType.label": "String"
                }
            },
            {
                "name": "audience",
                "protocol": "openid-connect",
                "protocolMapper": "oidc-audience-mapper",
                "config": {
                    "id.token.claim": "true",
                    "access.token.claim": "true",
                    "userinfo.token.claim": "true",
                    "included.client.audience": "structures-client"
                }
            }
        ]
    }' \
    http://localhost:8888/auth/admin/realms/master/clients)

if echo "$CLIENT_RESPONSE" | jq -e '.error' > /dev/null; then
    echo "Failed to create client:"
    echo "$CLIENT_RESPONSE"
    exit 1
fi

echo "Structures client created successfully!"

# Get client ID
CLIENT_ID=$(curl -s -X GET \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    http://localhost:8888/auth/admin/realms/master/clients | \
    jq -r '.[] | select(.clientId == "structures-client") | .id')

echo "Client ID: $CLIENT_ID"

# Create a test user
echo "Creating test user..."

USER_RESPONSE=$(curl -s -X POST \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "username": "testuser",
        "email": "testuser@example.com",
        "firstName": "Test",
        "lastName": "User",
        "enabled": true,
        "emailVerified": true,
        "credentials": [
            {
                "type": "password",
                "value": "password123",
                "temporary": false
            }
        ]
    }' \
    http://localhost:8888/auth/admin/realms/master/users)

if echo "$USER_RESPONSE" | jq -e '.error' > /dev/null; then
    echo "Failed to create user:"
    echo "$USER_RESPONSE"
    exit 1
fi

echo "Test user created successfully!"

# Get realm info for configuration
echo "Getting realm configuration..."

REALM_INFO=$(curl -s -X GET \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    http://localhost:8888/auth/admin/realms/master)

REALM_NAME=$(echo "$REALM_INFO" | jq -r '.realm')

echo "Realm: $REALM_NAME"

echo ""
echo "=========================================="
echo "Keycloak Setup Complete!"
echo "=========================================="
echo ""
echo "Keycloak URL: http://localhost:8888/auth"
echo "Admin Console: http://localhost:8888/auth/admin"
echo "Admin Username: admin"
echo "Admin Password: admin"
echo ""
echo "Realm: $REALM_NAME"
echo "Client ID: structures-client"
echo "Test User: testuser / password123"
echo ""
echo "Frontend Configuration:"
echo "Authority: http://localhost:8888/auth/realms/$REALM_NAME"
echo "Client ID: structures-client"
echo "Redirect URI: http://localhost:5173/login"
echo "Post Logout Redirect: http://localhost:5173"
echo "Silent Redirect: http://localhost:5173/login/silent-renew"
echo ""
echo "Backend Configuration:"
echo "Issuer: http://localhost:8888/auth/realms/$REALM_NAME"
echo "Audience: structures-client"
echo ""
echo "==========================================" 