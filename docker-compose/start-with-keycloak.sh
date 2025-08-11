#!/bin/bash

# Start Structures with Keycloak
# This script starts the complete stack including Keycloak identity provider

set -e

echo "Starting Structures with Keycloak..."

# Create network if it doesn't exist
docker network create structures-network 2>/dev/null || true

# Start the main stack
echo "Starting main stack (all services + test data)..."
pushd docker-compose >/dev/null
docker compose -f compose.yml -f compose.ek-m4.override.yml -f compose.gen-schemas.yml up -d
popd >/dev/null

# Start Keycloak stack (DB, Keycloak, and bootstrapper)
echo "Starting Keycloak (with bootstrap)..."
pushd docker-compose >/dev/null
docker compose -f compose.keycloak.yml up -d keycloak-db keycloak keycloak-setup
popd >/dev/null

# Wait for services to be ready
echo "Waiting for services to be ready..."

# Wait for Elasticsearch
echo "Waiting for Elasticsearch..."
until curl -f http://localhost:9200/_cluster/health > /dev/null 2>&1; do
    echo "Waiting for Elasticsearch..."
    sleep 5
done

# Wait for Keycloak bootstrap (one-shot container) to complete
echo "Waiting for Keycloak bootstrap to complete..."
SETUP_EXIT_CODE=$(docker wait keycloak-setup || echo 1)
if [ "$SETUP_EXIT_CODE" -ne 0 ]; then
    echo "Keycloak bootstrap failed (exit code: $SETUP_EXIT_CODE). Showing last logs:"
    docker logs keycloak-setup | tail -n 200
    exit 1
fi

echo ""
echo "=========================================="
echo "All services started successfully!"
echo "=========================================="
echo ""
echo "Services:"
echo "- Structures Server: http://localhost:9090"
echo "- Structures API: http://localhost:8080"
echo "- GraphQL: http://localhost:4000"
echo "- Keycloak: http://localhost:8888/auth"
echo "- Keycloak Admin: http://localhost:8888/auth/admin"
echo ""
echo "Frontend:"
echo "- Next.js Frontend: http://localhost:5173"
echo ""
echo "Test Credentials:"
echo "- Keycloak Admin: admin / admin"
echo "- Test User: testuser / password123"
echo ""
echo "==========================================" 