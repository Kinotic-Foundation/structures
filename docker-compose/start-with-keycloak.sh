#!/bin/bash

# Start Structures with Keycloak
# This script starts the complete stack including Keycloak identity provider

set -e

echo "Starting Structures with Keycloak..."

# Create network if it doesn't exist
docker network create structures-network 2>/dev/null || true

# Start the main stack
echo "Starting main stack..."
docker-compose -f docker-compose/compose.yml up -d

# Start Keycloak
echo "Starting Keycloak..."
docker-compose -f docker-compose/compose-keycloak.yml up -d

# Wait for services to be ready
echo "Waiting for services to be ready..."

# Wait for Elasticsearch
echo "Waiting for Elasticsearch..."
until curl -f http://localhost:9200/_cluster/health > /dev/null 2>&1; do
    echo "Waiting for Elasticsearch..."
    sleep 5
done

# Wait for Keycloak
echo "Waiting for Keycloak..."
echo "This may take a few minutes on first startup..."

# Then wait for the health endpoint
until curl -f http://localhost:8888/auth/health/ready > /dev/null 2>&1; do
    echo "Waiting for Keycloak health endpoint..."
    sleep 10
done

# Setup Keycloak configuration
echo "Setting up Keycloak configuration..."
./docker-compose/setup-keycloak.sh

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