#!/bin/bash

echo "Waiting for Keycloak to be fully ready..."
sleep 15

echo "Getting admin token..."
ADMIN_TOKEN=$(curl -s -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin&password=admin&grant_type=password&client_id=admin-cli" \
  http://keycloak:8888/auth/realms/master/protocol/openid-connect/token | \
  grep -o '"access_token":"[^"]*"' | cut -d'"' -f4)

if [ -z "$ADMIN_TOKEN" ]; then
    echo "Failed to get admin token"
    exit 1
fi

echo "Admin token obtained"

echo "Importing test realm from configuration file..."
curl -s -X POST \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d @/keycloak-test-realm.json \
  http://keycloak:8888/auth/admin/realms

if [ $? -eq 0 ]; then
    echo "Test realm imported successfully!"
    echo "Realm: test"
    echo "Client ID: structures-client (Public Client)"
    echo "Test user: testuser@example.com / password123"
    echo "Protocol mappers: email, name, family name, preferred_username, audience, tenantId"
else
    echo "Failed to import test realm"
    exit 1
fi
