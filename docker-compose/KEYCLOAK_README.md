# Keycloak Test Realm Configuration

This directory contains the configuration files for setting up a test Keycloak realm for the Structures project.

## Files

### `keycloak-test-realm.json`
Complete realm configuration file that can be imported into Keycloak. This file contains:
- Realm settings (test realm)
- Client configuration (structures-client as a public client)
- Protocol mappers for OIDC claims
- User roles (user, admin)
- Test user (testuser@example.com / password123)

### `keycloak-import-test-realm.sh`
Script to import the realm configuration into Keycloak. This script:
- Waits for Keycloak to be ready
- Gets an admin token
- Imports the realm configuration from the JSON file

### `compose.keycloak.yml`
Docker Compose file that sets up:
- PostgreSQL database for Keycloak
- Keycloak server
- Initialization container that imports the realm

## Usage

### Starting the services
```bash
docker-compose -f docker-compose/compose.keycloak.yml up -d
```

### Viewing logs
```bash
docker logs keycloak-init
```

### Accessing Keycloak
- **URL**: http://localhost:8888/auth
- **Admin Console**: http://localhost:8888/auth/admin
- **Admin Credentials**: admin / admin

### Test Realm Details
- **Realm**: test
- **Client ID**: structures-client (Public Client)
- **Test User**: testuser@example.com / password123
- **Redirect URIs**: 
  - http://localhost:5173/*
  - http://localhost:3000/*
- **Protocol Mappers**: email, name, family name, preferred_username, audience, tenantId

## Modifying the Configuration

To modify the realm configuration:

1. Edit `keycloak-test-realm.json`
2. Restart the services:
   ```bash
   docker-compose -f docker-compose/compose.keycloak.yml down
   docker-compose -f docker-compose/compose.keycloak.yml up -d
   ```

## OIDC Configuration

The structures-client is configured as a public client suitable for web applications:
- `publicClient: true`
- `serviceAccountsEnabled: false`
- Standard OIDC authorization code flow enabled
- Direct access grants enabled for testing

## Frontend Integration

The frontend should be configured to use:
- **Authority**: http://localhost:8888/auth/realms/test
- **Client ID**: structures-client
- **Redirect URI**: http://localhost:5173/auth/callback (or your frontend port)
