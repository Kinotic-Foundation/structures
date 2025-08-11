# OIDC Documentation

This folder contains comprehensive documentation for OpenID Connect (OIDC) authentication configuration and troubleshooting.

## Documentation Structure

### Provider-Specific Documentation

#### [Microsoft Entra ID (Azure AD)](./entra/)
- **Purpose**: Microsoft Entra ID OIDC configuration and troubleshooting
- **Covers**: Audience configuration, error troubleshooting, security best practices
- **Use Case**: When implementing Microsoft Entra ID authentication

#### [Social Login Providers](./social/)
- **Purpose**: Social login configuration for consumer applications
- **Covers**: Personal account authentication (Google, Microsoft, GitHub)
- **Use Case**: When implementing social login for consumer-facing apps

### Core Documentation

#### [OIDC Implementation Guide](./OIDC_IMPLEMENTATION.md)
- **Purpose**: Core OIDC implementation details and architecture
- **Covers**: JWT validation, JWKS caching, security features, multi-provider support
- **Use Case**: Understanding the underlying OIDC implementation

#### Basic Auth Configuration
- **Purpose**: Configurable basic username/password authentication
- **Covers**: Runtime configuration via app-config JSON, UI control, security considerations
- **Use Case**: When you need to enable/disable basic authentication
- **Reference**: See `structures-frontend-next/CONFIGURATION.md` (section "Basic Authentication")

## Quick Reference

### Common OIDC Providers

| Provider | Documentation | Audience | Issuer |
|----------|---------------|----------|---------|
| **Microsoft Entra ID** | [Entra Docs](./entra/) | `00000003-0000-0000-c000-000000000000` | `https://sts.windows.net` |
| **Microsoft Social** | [Social Docs](./social/) | Your client ID | `https://login.microsoftonline.com/9188040d-6c67-4c5b-b112-36a304b66dad/v2.0` |
| **Google Social** | [Social Docs](./social/) | Your client ID | `https://accounts.google.com` |
| **GitHub Social** | [Social Docs](./social/) | Your client ID | `https://github.com` |
| **Okta** | [Core Docs](../structures-core/OIDC_IMPLEMENTATION.md) | Your client ID | `https://your-okta-domain.com` |
| **Keycloak** | [Core Docs](../structures-core/OIDC_IMPLEMENTATION.md) | `structures-client` | `http://localhost:8888/auth/realms/master` |

### Frontend Configuration

#### Environment Variables
```bash
# Enable OIDC providers
VITE_OIDC_MICROSOFT_ENABLED=true
VITE_OIDC_OKTA_ENABLED=true
VITE_OIDC_KEYCLOAK_ENABLED=true

# Basic authentication control
VITE_BASIC_AUTH_ENABLED=true

# Provider-specific configuration
VITE_MICROSOFT_CLIENT_ID=your-client-id
VITE_MICROSOFT_AUTHORITY=https://login.microsoftonline.com/your-tenant-id/v2.0
```

#### Backend Configuration
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://sts.windows.net"  # Microsoft
      - "https://your-okta-domain.com"  # Okta
    authorization-audiences:
      - "00000003-0000-0000-c000-000000000000"  # Microsoft Graph API
      - "your-client-id"  # Other providers
```

## Architecture Overview

### OIDC Flow
```
1. User clicks "Login with [Provider]"
2. Frontend redirects to OIDC provider
3. User authenticates with provider
4. Provider redirects back with authorization code
5. Frontend exchanges code for JWT token
6. Frontend sends JWT to backend API
7. Backend validates JWT signature and claims
8. Backend creates user session
```

### Security Components
- **JWT Validation**: Signature verification using JWKS
- **Issuer Validation**: Ensures tokens come from trusted providers
- **Audience Validation**: Ensures tokens are intended for your application
- **Expiration Validation**: Ensures tokens haven't expired
- **Role Extraction**: Extracts user roles from JWT claims

## Common Issues

### 1. JWT Signature Validation
- **Error**: "JWT signature does not match locally computed signature"
- **Solution**: Check JWKS endpoint connectivity and issuer configuration
- **Guide**: [Microsoft Entra ID](./entra/) or [Core Implementation](../structures-core/OIDC_IMPLEMENTATION.md)

### 2. Invalid Audience
- **Error**: "Invalid audience" or "AADSTS650053"
- **Solution**: Verify audience configuration matches JWT token
- **Guide**: [Microsoft Audience Configuration](./entra/MICROSOFT_AUDIENCE_CONFIGURATION.md)

### 3. Resource Parameter Issues
- **Error**: "AADSTS901002: The 'resource' request parameter is not supported"
- **Solution**: Use scope parameter instead of resource parameter
- **Guide**: [AADSTS901002 Troubleshooting](./entra/MICROSOFT_AADSTS901002_TROUBLESHOOTING.md)

### 4. Multi-tenant Configuration
- **Error**: "AADSTS50194: Application is not configured as multi-tenant"
- **Solution**: Use tenant-specific endpoint or configure as multi-tenant
- **Guide**: [Microsoft OIDC Troubleshooting](./entra/MICROSOFT_OIDC_TROUBLESHOOTING.md)

## Testing

### 1. Verify JWT Token
```javascript
// Decode JWT in browser console
const token = 'your-jwt-token';
const payload = JSON.parse(atob(token.split('.')[1]));
console.log('Audience:', payload.aud);
console.log('Issuer:', payload.iss);
console.log('Expiration:', new Date(payload.exp * 1000));
```

### 2. Test JWKS Endpoints
```bash
# Microsoft
curl -s "https://login.microsoftonline.com/common/discovery/v2.0/keys" | jq

# Okta
curl -s "https://your-okta-domain.com/oauth2/v1/keys" | jq

# Keycloak
curl -s "http://localhost:8888/auth/realms/master/protocol/openid-connect/certs" | jq
```

### 3. Enable Debug Logging
```yaml
logging:
  level:
    org.kinotic.structures.internal.config: DEBUG
    org.kinotic.structures.internal.security: DEBUG
```

## Security Best Practices

### 1. Token Validation
- Always validate JWT signatures
- Verify issuer claims
- Check audience claims
- Validate token expiration

### 2. Configuration Security
- Use HTTPS in production
- Store secrets securely
- Rotate keys regularly
- Monitor authentication events

### 3. Error Handling
- Don't expose sensitive information in error messages
- Log authentication failures
- Implement rate limiting
- Monitor for suspicious activity

## Development Workflow

### 1. Local Development
```bash
# Start backend with OIDC enabled
./gradlew bootRun

# Start frontend with OIDC providers
cd structures-frontend-next
npm run dev
```

### 2. Testing OIDC Flow
1. Navigate to login page
2. Click "Continue with [Provider]"
3. Complete authentication
4. Verify successful redirect
5. Check JWT token structure

### 3. Debugging Issues
1. Enable debug logging
2. Check browser network tab
3. Verify JWT token structure
4. Test JWKS endpoints
5. Review application logs

## Related Documentation

- [Frontend OIDC Configuration](../structures-frontend-next/src/pages/login/OidcConfiguration.ts)
- [Backend OIDC Implementation](../structures-core/src/main/java/org/kinotic/structures/internal/security/)
- [Security Service Interface](../structures-core/src/main/java/org/kinotic/structures/internal/security/SecurityService.java)

## Support

For issues not covered in these guides:
1. Check provider-specific documentation
2. Review application logs with debug logging
3. Test with provider's OIDC endpoints directly
4. Verify configuration against provider documentation 