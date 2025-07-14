# Keycloak Identity Provider Setup

This directory contains the Keycloak configuration for the Structures application.

## Quick Start

### 1. Start Keycloak

```bash
# Start Keycloak with the existing structures network
docker network create structures-network 2>/dev/null || true
docker-compose -f docker-compose/compose-keycloak.yml up -d
```

### 2. Setup Keycloak Configuration

```bash
# Wait for Keycloak to be ready, then run the setup script
./docker-compose/setup-keycloak.sh
```

### 3. Configure Frontend

Update the Keycloak configuration in `structures-frontend-next/src/pages/login/OidcConfiguration.ts`:

```typescript
keycloak: {
  client_id: 'structures-client',
  client_secret: '',
  authority: 'http://localhost:8888/auth/realms/master',
  redirect_uri: 'http://localhost:5173/login',
  post_logout_redirect_uri: 'http://localhost:5173',
  silent_redirect_uri: 'http://localhost:5173/login/silent-renew',
  loadUserInfo: true,
  publicClient: {
    isPublicClient: true,
    responseType: 'code',
    responseMode: 'query'
  },
  metadata: {
    authorization_endpoint: 'http://localhost:8888/auth/realms/master/protocol/openid-connect/auth',
    token_endpoint: 'http://localhost:8888/auth/realms/master/protocol/openid-connect/token',
    userinfo_endpoint: 'http://localhost:8888/auth/realms/master/protocol/openid-connect/userinfo',
    end_session_endpoint: 'http://localhost:8888/auth/realms/master/protocol/openid-connect/logout',
    jwks_uri: 'http://localhost:8888/auth/realms/master/protocol/openid-connect/certs',
  },
}
```

### 4. Configure Backend

Update the backend configuration in `structures-server/src/main/resources/application-development.yml`:

```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "http://localhost:8888/auth/realms/master"
    authorization-audiences:
      - "structures-client"
```

### 5. Test the Setup

1. Start the backend:
   ```bash
   ./gradlew :structures-server:bootRun
   ```

2. Start the frontend:
   ```bash
   cd structures-frontend-next
   npm run dev
   ```

3. Navigate to `http://localhost:5173/login` and click "Continue with Keycloak"

4. Login with the test user:
   - Username: `testuser`
   - Password: `password123`

## Keycloak Configuration Details

### Client Configuration

The setup script creates a client with the following settings:

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

The client includes the following protocol mappers:

1. **Email**: Maps user email to `email` claim
2. **Given Name**: Maps first name to `given_name` claim
3. **Family Name**: Maps last name to `family_name` claim
4. **Preferred Username**: Maps username to `preferred_username` claim

### Test User

A test user is created with the following credentials:

- **Username**: `testuser`
- **Email**: `testuser@example.com`
- **Password**: `password123`
- **First Name**: Test
- **Last Name**: User

## Keycloak Admin Console

Access the Keycloak admin console at: http://localhost:8888/auth/admin

- **Username**: `admin`
- **Password**: `admin`

### Useful Admin Console Features

1. **Users**: Manage users, roles, and permissions
2. **Clients**: View and modify client configurations
3. **Roles**: Create and assign roles to users
4. **Realms**: Manage realm settings and configurations

## Troubleshooting

### Common Issues

1. **Network Connection Issues**
   ```bash
   # Ensure the network exists
   docker network create structures-network
   ```

2. **Keycloak Not Starting**
   ```bash
   # Check logs
   docker-compose -f docker-compose/compose-keycloak.yml logs keycloak
   ```

3. **Setup Script Fails**
   ```bash
   # Ensure jq is installed
   brew install jq  # macOS
   # or
   sudo apt-get install jq  # Ubuntu/Debian
   ```

4. **Frontend Connection Issues**
   - Verify Keycloak is running on port 8080
   - Check browser console for CORS errors
   - Ensure redirect URIs match exactly

5. **Backend Authentication Issues**
   - Verify issuer URL matches exactly
   - Check audience configuration
   - Review backend logs for JWT validation errors

### Debug Logging

Enable debug logging for troubleshooting:

```yaml
logging:
  level:
    org.kinotic.structures.internal.config: DEBUG
    org.keycloak: DEBUG
```

### Manual Client Creation

If the setup script fails, you can manually create the client:

1. Login to Keycloak admin console
2. Navigate to Clients → Create
3. Set Client ID to `structures-client`
4. Set Client Protocol to `openid-connect`
5. Set Access Type to `public`
6. Add redirect URIs and web origins
7. Configure protocol mappers

## Production Considerations

For production deployment:

1. **Security**
   - Change default admin password
   - Use HTTPS for all endpoints
   - Configure proper CORS settings
   - Use strong passwords for users

2. **Database**
   - Use external PostgreSQL database
   - Configure database backups
   - Monitor database performance

3. **Scaling**
   - Configure Keycloak clustering
   - Use load balancer for high availability
   - Monitor resource usage

4. **Monitoring**
   - Enable Keycloak metrics
   - Configure logging aggregation
   - Set up health checks

## Integration with Other Services

### Elasticsearch Integration

If you're using the full stack with Elasticsearch:

```bash
# Start the complete stack
docker-compose -f docker-compose/compose.yml -f docker-compose/compose-keycloak.yml up -d
```

### Custom Realm

To use a custom realm instead of master:

1. Create a new realm in Keycloak admin console
2. Update the authority URL in frontend configuration
3. Update the issuer URL in backend configuration
4. Create the client in the new realm

## API Testing

Test the OIDC integration with curl:

```bash
# Get a token
TOKEN=$(curl -s -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=testuser&password=password123&grant_type=password&client_id=structures-client" \
  http://localhost:8888/auth/realms/master/protocol/openid-connect/token | \
  jq -r '.access_token')

# Use the token
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:9090/health/
``` 