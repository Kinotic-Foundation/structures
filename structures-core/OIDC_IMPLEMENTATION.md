# OIDC Authentication Implementation

This document describes the OIDC (OpenID Connect) authentication implementation for the Structures project.

## Overview

The OIDC implementation provides JWT token-based authentication with the following features:

- **JWKS Caching**: Efficient caching of JSON Web Key Sets using Caffeine
- **Issuer Validation**: Validates JWT tokens against configured allowed issuers
- **Audience Validation**: Validates JWT tokens against configured allowed audiences
- **Token Expiration**: Automatically checks token expiration
- **Well-known Configuration**: Fetches and caches OIDC provider configuration
- **Role Extraction**: Extracts user roles from JWT claims
- **Tenant Support**: Supports multi-tenant applications with tenant ID extraction
- **Frontend Integration**: Complete Vue.js frontend integration with navigation support
- **Multi-Provider Support**: Supports Okta, Keycloak, Google, Microsoft, and custom providers
- **State Management**: Robust state handling with base64 encoding and localStorage persistence

## Components

### 1. JwksService
Handles JWKS (JSON Web Key Set) operations:
- Fetches well-known OIDC configuration
- Caches JWKS keys and configurations
- Extracts key information from JWT tokens
- Provides efficient key lookup by issuer and key ID

### 2. OidcAuthVerifier
Main authentication component:
- Implements SecurityService interface
- Validates JWT tokens using JJWT 0.12.x
- Creates Participant objects from JWT claims
- Handles issuer and audience validation
- Supports multiple audience validation

### 3. OidcAuthVerifierProperties
Configuration properties for OIDC:
- `enabled`: Enable/disable OIDC authentication
- `allowedIssuers`: List of allowed OIDC issuers
- `authorizationAudiences`: List of allowed audiences

### 4. Frontend Integration
Vue.js frontend components:
- OIDC login flow with multiple providers (Okta, Keycloak, Google, Microsoft)
- Automatic token handling and refresh
- Navigation after successful authentication
- Error handling and user feedback
- State management with localStorage persistence

## Configuration

### Application Properties

Add the following configuration to your `application.yml`:

```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://your-oidc-provider.com"
      - "https://keycloak.your-domain.com/auth/realms/your-realm"
    authorization-audiences:
      - "your-application-client-id"
      - "your-api-audience"
```

### Example Configurations

#### Okta (Tested and Verified)
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://dev-39125344.okta.com/oauth2/default"
    authorization-audiences:
      - "0oaowrlsm5Ua1vWD85d7"
```

**Frontend Configuration (structures-frontend-next):**
```typescript
// In OidcConfiguration.ts
okta: {
  client_id: '0oaowrlsm5Ua1vWD85d7',
  client_secret: '',
  authority: 'https://dev-39125344.okta.com/oauth2/default',
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
    authorization_endpoint: 'https://dev-39125344.okta.com/oauth2/default/v1/authorize',
    token_endpoint: 'https://dev-39125344.okta.com/oauth2/default/v1/token',
    userinfo_endpoint: 'https://dev-39125344.okta.com/oauth2/default/v1/userinfo',
    end_session_endpoint: 'https://dev-39125344.okta.com/oauth2/default/v1/logout',
    jwks_uri: 'https://dev-39125344.okta.com/oauth2/default/v1/keys'
  }
}
```

#### Keycloak (Tested and Verified)
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "http://localhost:8888/auth/realms/master"
    authorization-audiences:
      - "structures-client"
```

**Frontend Configuration (structures-frontend-next):**
```typescript
// In OidcConfiguration.ts
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
  // No explicit metadata - uses automatic discovery
}
```

**Docker Compose Setup:**
```bash
# Start Keycloak with PostgreSQL
docker-compose -f docker-compose/compose-keycloak.yml up -d

# Setup Keycloak configuration
./docker-compose/setup-keycloak.sh
```

#### Auth0
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://your-tenant.auth0.com/"
    authorization-audiences:
      - "https://your-api-identifier"
```

#### Azure AD
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://login.microsoftonline.com/your-tenant-id/v2.0"
    authorization-audiences:
      - "your-application-client-id"
```

## Frontend State Management

### State Flow
1. **Login Initiation**: 
   - Create base64-encoded state with provider and referer info
   - Store in localStorage via oidc-client-ts library
   - Redirect to OIDC provider

2. **Callback Processing**:
   - Check localStorage for stored state
   - Determine which provider was used
   - Use correct provider for callback
   - Library handles state validation automatically

### State Structure
```typescript
const stateObj = {
  referer: string | null,    // Original destination
  provider: OidcProvider     // Provider used for login
};
```

### Provider Detection
The frontend automatically detects which provider was used by checking localStorage:
```typescript
const stateKey = `oidc.${state}`;
const storedState = localStorage.getItem(stateKey);
const sessionState = JSON.parse(storedState);
const stateObj = JSON.parse(atob(sessionState.data));
const provider = stateObj.provider;
```

## JWT Claims Mapping

The implementation extracts the following information from JWT claims:

| JWT Claim | Participant Field | Description |
|-----------|------------------|-------------|
| `sub` | `id` | Subject identifier |
| `email` | `metadata.email` | User email |
| `name` | `metadata.name` | Full name |
| `preferred_username` | `metadata.name` | Username (fallback) |
| `tenant_id` or `tid` | `tenantId` | Tenant identifier |
| `roles`, `role`, `groups`, `realm_access.roles` | `roles` | User roles |

## Caching Strategy

### JWKS Key Cache
- **TTL**: 1 hour
- **Max Size**: 100 keys
- **Purpose**: Cache individual public keys by issuer and key ID

### Well-known Configuration Cache
- **TTL**: 24 hours
- **Max Size**: 10 configurations
- **Purpose**: Cache OIDC provider discovery documents

## Security Features

### 1. Token Validation
- Verifies JWT signature using public keys from JWKS
- Validates token expiration
- Checks issuer against allowed list
- Validates audience against allowed list (supports multiple audiences)

### 2. State Management
- Base64 encoding for state parameters
- localStorage persistence for state tracking
- Automatic CSRF protection via oidc-client-ts
- Provider detection from stored state

### 3. Error Handling
- Graceful handling of invalid tokens
- Comprehensive logging for debugging
- No sensitive information in error messages

### 4. Cache Security
- Automatic cache expiration
- Size limits to prevent memory issues
- Secure key storage

### 5. Frontend Security
- Secure token storage in cookies
- Automatic token refresh
- Proper logout and session cleanup

## Usage Examples

### Backend API Request
```bash
curl -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..." \
     https://your-api.com/endpoint
```

### Frontend Integration
```javascript
// Automatic OIDC login flow
const userManager = createUserManager('keycloak');
await userManager.signinRedirect();

// Handle callback
const user = await userManager.signinRedirectCallback();
await userState.handleOidcLogin(user);
```

### JavaScript Client
```javascript
// Set authorization header with JWT token
const headers = {
  'Authorization': `Bearer ${jwtToken}`,
  'Content-Type': 'application/json'
};

fetch('/api/endpoint', { headers })
  .then(response => response.json())
  .then(data => console.log(data));
```

## Testing

### Unit Tests
Run the test suite to validate the implementation:

```bash
# Run all OIDC tests
./gradlew test --tests "*Oidc*"

# Run specific test classes
./gradlew test --tests OidcAuthVerifierTest
./gradlew test --tests JwksServiceTest
```

### Integration Testing
For integration testing, you can:

1. Use a test OIDC provider (e.g., Okta test instance)
2. Generate test JWT tokens
3. Mock the JWKS service for controlled testing

### Test Configuration
```yaml
# Test configuration
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://test-issuer.com"
    authorization-audiences:
      - "test-audience"
```

### Frontend Testing
```bash
# Start the frontend development server
cd structures-frontend-next
npm run dev

# Test OIDC login flow
# 1. Navigate to /login
# 2. Click "Continue with Keycloak" or "Continue with Okta"
# 3. Complete authentication
# 4. Verify navigation to /applications
```

### Keycloak Local Testing
```bash
# Start Keycloak
docker-compose -f docker-compose/compose-keycloak.yml up -d

# Setup Keycloak
./docker-compose/setup-keycloak.sh

# Test with test user
# Username: testuser
# Password: password123
```

## Troubleshooting

### Common Issues

1. **"No authorization header found"**
   - Ensure the Authorization header is present
   - Check header name case sensitivity

2. **"Invalid issuer"**
   - Verify the issuer in your JWT token matches allowed issuers
   - Check issuer URL format

3. **"Invalid audience"**
   - Ensure the audience in your JWT token matches allowed audiences
   - Verify client ID configuration

4. **"JWT token verification failed"**
   - Check JWT token format
   - Verify token signature
   - Ensure token is not expired

5. **"Navigation not working after login"**
   - Check browser console for debug logs
   - Verify router configuration
   - Ensure CONTINUUM_UI is properly initialized

6. **"Authority mismatch on settings vs. signin state"**
   - Clear localStorage and try again
   - Check provider configuration
   - Verify automatic discovery is working

### Debug Logging

Enable debug logging for troubleshooting:

```yaml
logging:
  level:
    org.kinotic.structures.internal.config: DEBUG
```

### Frontend Debugging
```javascript
// Check authentication state
console.log('Is authenticated:', userState.isAuthenticated());

// Check navigation
console.log('Current route:', router.currentRoute.value.path);

// Check stored state
console.log('Stored state:', localStorage.getItem('oidc.state.your-client-id'));
```

## Performance Considerations

1. **Cache Tuning**: Adjust cache TTL and size based on your OIDC provider
2. **Network Timeouts**: Configure appropriate timeouts for JWKS fetching
3. **Memory Usage**: Monitor cache memory usage in production
4. **Concurrent Requests**: The implementation is thread-safe and handles concurrent requests

## Security Best Practices

1. **HTTPS Only**: Always use HTTPS in production
2. **Token Validation**: Never skip token validation
3. **Audience Validation**: Always validate audience claims
4. **Issuer Validation**: Strictly validate issuer claims
5. **Token Expiration**: Ensure tokens have reasonable expiration times
6. **Key Rotation**: Monitor for key rotation events from your OIDC provider
7. **State Management**: Use proper state validation to prevent CSRF attacks

## Migration from Other Authentication

To migrate from other authentication methods:

1. Configure OIDC properties
2. Enable OIDC authentication
3. Update client applications to send JWT tokens
4. Test thoroughly in staging environment
5. Deploy to production

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review application logs
3. Verify OIDC provider configuration
4. Test with a known valid JWT token
5. Check browser console for frontend issues 