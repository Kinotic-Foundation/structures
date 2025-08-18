# Keycloak Identity Provider Setup for Structures

This guide provides a complete setup for using Keycloak as an identity provider with the Structures application.

## Overview

The setup includes:
- **Keycloak 24.0.2** with PostgreSQL database
- **Pre-configured client** for the Structures frontend
- **Test user** for immediate testing
- **Automatic setup script** for easy configuration
- **Frontend integration** with Vue.js login page
- **Backend integration** with OIDC authentication

## Quick Start

### 1. Prerequisites

Ensure you have the following installed:
- Docker and Docker Compose
- `jq` (JSON processor)
- Node.js and npm (for frontend)

```bash
# Install jq if not already installed
brew install jq  # macOS
# or
sudo apt-get install jq  # Ubuntu/Debian
```

### 2. Start the Complete Stack (recommended)

```bash
# Make scripts executable
chmod +x docker-compose/*.sh

# Start everything with one command (includes Keycloak bootstrap)
./docker-compose/start-with-keycloak.sh

# Or manually:
cd structures/docker-compose && \
docker compose -f compose.yml -f compose.ek-m4.override.yml -f compose.gen-schemas.yml up -d && \
docker compose -f compose.keycloak.yml up -d keycloak-db keycloak keycloak-setup
```

This script will:
- Create the Docker network
- Start Elasticsearch and other services
- Start Keycloak with PostgreSQL
- Wait for all services to be ready
- Automatically configure Keycloak with the Structures client
- Create a test user

### 3. Start the Frontend

```bash
cd structures-frontend-next
npm install
npm run dev
```

### 4. Test the Setup

1. Navigate to `http://localhost:5173/login`
2. Click "Continue with Keycloak"
3. Login with test credentials:
   - Username: `testuser@example.com`
   - Password: `password123`

## Manual Setup (Alternative)

If you prefer to set up manually:

### 1. Start Keycloak Only

```bash
# Create network
docker network create structures-network

# Start Keycloak (DB, server, and one-shot bootstrapper)
docker compose -f docker-compose/compose.keycloak.yml up -d keycloak-db keycloak keycloak-setup

# Optionally wait for bootstrapper to complete
docker wait keycloak-setup
```

### 2. Start Backend

```bash
./gradlew :structures-server:bootRun
```

### 3. Start Frontend

```bash
cd structures-frontend-next
npm run dev
```

## Configuration Details

### Keycloak Client Configuration

The setup creates a client with these settings:

- **Client ID**: `structures-client`
- **Client Type**: Public Client (for SPA)
- **Access Type**: Public
- **Standard Flow**: Enabled
- **Direct Access Grants**: Disabled
- **Service Accounts**: Disabled

### Redirect URIs

- `http://localhost:5173/login` - Main redirect URI
- `http://localhost:5173/login/silent-renew` - Silent token renewal

### Web Origins

- `http://localhost:5173` - Frontend origin

### Protocol Mappers

The client includes these protocol mappers:

1. **Email**: Maps user email to `email` claim
2. **Given Name**: Maps first name to `given_name` claim
3. **Family Name**: Maps last name to `family_name` claim
4. **Preferred Username**: Maps username to `preferred_username` claim

## Service URLs

| Service | URL | Description |
|---------|-----|-------------|
| Structures Server | http://localhost:9090 | Main application |
| Structures API | http://localhost:8080 | REST API |
| GraphQL | http://localhost:4000 | GraphQL endpoint |
| Keycloak | http://localhost:8888/auth | Identity provider |
| Keycloak Admin | http://localhost:8888/auth/admin | Admin console |
| Frontend | http://localhost:5173 | Vue.js application |

## Admin Credentials

### Keycloak Admin Console
- **URL**: http://localhost:8888/auth/admin
- **Username**: `admin`
- **Password**: `admin`

### Test User
- **Username**: `testuser@example.com`
- **Email**: `testuser@example.com`
- **Password**: `password123`
- **First Name**: Test
- **Last Name**: User

## Frontend Configuration

The frontend is configured via runtime JSON. Update `structures-frontend-next/public/app-config.json` (or serve `/config/app-config.json`). See `structures-frontend-next/CONFIGURATION.md` for full details. Example Keycloak section:

```json
{
  "oidc": {
    "keycloak": {
      "enabled": true,
      "client_id": "structures-client",
      "authority": "http://localhost:8888/auth/realms/master",
      "redirect_uri": "http://localhost:5173/login",
      "post_logout_redirect_uri": "http://localhost:5173",
      "silent_redirect_uri": "http://localhost:5173/login/silent-renew"
    }
  }
}
```

## Backend Configuration

The backend is configured in `structures-server/src/main/resources/application-development.yml`:

```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://dev-39125344.okta.com/oauth2/default"
      - "http://localhost:8888/auth/realms/master"
    authorization-audiences:
      - "api://default"
      - "structures-client"
```

## Testing the Integration

### 1. API Testing with curl

```bash
# Get a token from Keycloak
TOKEN=$(curl -s -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=testuser@example.com&password=password123&grant_type=password&client_id=structures-client" \
  http://localhost:8888/auth/realms/master/protocol/openid-connect/token | \
  jq -r '.access_token')

# Use the token to access the API
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:9090/health/
```

### 2. Frontend Testing

1. Open `http://localhost:5173/login`
2. Click "Continue with Keycloak"
3. Login with `testuser@example.com` / `password123`
4. Verify you're redirected to the applications page

### 3. Backend Health Check

```bash
# Test backend health
curl http://localhost:9090/health/

# Test with authentication
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:9090/health/
```

## Troubleshooting

### Common Issues

1. **Keycloak Not Starting**
   ```bash
   # Check logs
   docker compose -f docker-compose/compose.keycloak.yml logs keycloak
   
   # Check if port 8888 is available
   lsof -i :8888
   ```

2. **Bootstrapper Fails**
   ```bash
   # Show bootstrap container logs
   docker logs keycloak-setup | tail -n 200

   # Check if Keycloak is ready
   curl -sf http://localhost:8888/auth/health/ready
   ```

3. **Frontend Connection Issues**
   - Check browser console for CORS errors
   - Verify Keycloak is running on port 8080
   - Ensure redirect URIs match exactly

4. **Backend Authentication Issues**
   ```bash
   # Check backend logs
   ./gradlew :structures-server:bootRun
   
       # Verify issuer URL
    curl http://localhost:8888/auth/realms/master/.well-known/openid-configuration
   ```

5. **Network Issues**
   ```bash
   # Check if network exists
   docker network ls | grep structures-network
   
   # Create network if missing
   docker network create structures-network
   ```

### Debug Logging

Enable debug logging for troubleshooting:

```yaml
logging:
  level:
    org.kinotic.structures.internal.config: DEBUG
    org.keycloak: DEBUG
```

### Manual Client Creation

If the setup script fails, manually create the client:

1. Login to Keycloak admin console
2. Navigate to Clients → Create
3. Set Client ID to `structures-client`
4. Set Client Protocol to `openid-connect`
5. Set Access Type to `public`
6. Add redirect URIs:
   - `http://localhost:5173/login`
   - `http://localhost:5173/login/silent-renew`
7. Add web origins: `http://localhost:5173`
8. Configure protocol mappers for email, name, etc.

## Production Considerations

### Security
- Change default admin password
- Use HTTPS for all endpoints
- Configure proper CORS settings
- Use strong passwords for users

### Database
- Use external PostgreSQL database
- Configure database backups
- Monitor database performance

### Scaling
- Configure Keycloak clustering
- Use load balancer for high availability
- Monitor resource usage

### Monitoring
- Enable Keycloak metrics
- Configure logging aggregation
- Set up health checks

## Customization

### Using a Custom Realm

1. Create a new realm in Keycloak admin console
2. Update the authority URL in frontend configuration
3. Update the issuer URL in backend configuration
4. Create the client in the new realm

### Adding More Users

1. Login to Keycloak admin console
2. Navigate to Users → Add User
3. Fill in user details
4. Set credentials
5. Assign roles if needed

### Adding Roles

1. Login to Keycloak admin console
2. Navigate to Roles → Add Role
3. Create roles as needed
4. Assign roles to users

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review application logs
3. Verify Keycloak configuration
4. Test with a known valid JWT token
5. Check browser console for frontend issues

## Files Overview

| File | Purpose |
|------|---------|
| `docker-compose/compose-keycloak.yml` | Keycloak and PostgreSQL services |
| `docker-compose/setup-keycloak.sh` | Automated Keycloak configuration |
| `docker-compose/start-with-keycloak.sh` | Complete stack startup script |
| (removed) `docker-compose/KEYCLOAK_README.md` | Consolidated into this guide |
| `structures-frontend-next/src/pages/login/OidcConfiguration.ts` | Frontend OIDC configuration |
| `structures-server/src/main/resources/application-development.yml` | Backend OIDC configuration | 