# Microsoft Social Login JWT Token Troubleshooting

## Problem: Access Token is Not a Valid JWT

When using Microsoft social login (personal accounts), you may encounter the error:
```
"JWT signature does not match locally computed signature"
```

This happens because **Microsoft's consumer endpoints often return opaque access tokens instead of JWTs**.

## Root Cause

### Microsoft Consumer Platform Limitations

1. **Opaque Tokens**: Microsoft consumer endpoints return opaque access tokens (not JWTs)
2. **Limited JWT Support**: Consumer accounts don't always support JWT access tokens
3. **Different Token Format**: Consumer tokens may be in a different format than enterprise tokens

### Why This Happens

- **Consumer vs Enterprise**: Microsoft treats consumer accounts differently from enterprise accounts
- **Token Types**: Consumer endpoints may return opaque tokens or different JWT formats
- **Scope Limitations**: Consumer accounts have different scope and permission models

## Solutions

### Solution 1: Use ID Token Instead of Access Token (Recommended)

The most reliable solution is to use the **ID token** (which is always a JWT) instead of the access token.

#### Updated Configuration

**OIDC Configuration:**
```typescript
microsoftSocial: {
  enabled: env.VITE_OIDC_MICROSOFT_SOCIAL_ENABLED === 'true',
  client_id: env.VITE_MICROSOFT_SOCIAL_CLIENT_ID || '',
  client_secret: '',
  authority: env.VITE_MICROSOFT_SOCIAL_AUTHORITY || 'https://login.microsoftonline.com/consumers/v2.0',
  redirect_uri: env.VITE_MICROSOFT_SOCIAL_REDIRECT_URI || '',
  post_logout_redirect_uri: env.VITE_MICROSOFT_SOCIAL_POST_LOGOUT_REDIRECT_URI || '',
  silent_redirect_uri: env.VITE_MICROSOFT_SOCIAL_SILENT_REDIRECT_URI || '',
  loadUserInfo: true,
  scope: 'openid profile email',
  response_type: 'code', // Use Authorization Code flow with PKCE
  response_mode: 'query',
  metadata: {
    authorization_endpoint: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize',
    token_endpoint: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/token',
    userinfo_endpoint: 'https://graph.microsoft.com/oidc/userinfo',
    end_session_endpoint: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/logout',
    jwks_uri: 'https://login.microsoftonline.com/consumers/discovery/v2.0/keys',
  },
}
```

#### Backend Configuration

**Application Properties:**
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://login.microsoftonline.com/9188040d-6c67-4c5b-b112-36a304b66dad/v2.0"
    authorization-audiences:
      - "your-consumer-app-client-id"
```

### Solution 2: Automatic Token Selection

The frontend now automatically detects if the access token is a valid JWT and falls back to the ID token if needed:

```typescript
// In IUserState.ts
public async handleOidcLogin(user: User): Promise<void> {
    // Determine which token to use
    let tokenToUse = user.access_token;
    
    // For Microsoft social login, check if access_token is a valid JWT
    // If not, use the ID token instead
    if (user.access_token && !this.isValidJWT(user.access_token)) {
        console.log('Access token is not a valid JWT, using ID token for Microsoft social login');
        tokenToUse = user.id_token || user.access_token;
    }
    
    // Use the selected token
    connectionInfo.connectHeaders = {
        Authorization: `Bearer ${tokenToUse}`
    }
}
```

## Testing the Fix

### Step 1: Verify Token Types

```javascript
// Check token types in browser console
console.log('Access Token:', user.access_token);
console.log('ID Token:', user.id_token);

// Check if access token is JWT
function isJWT(token) {
    try {
        const parts = token.split('.');
        if (parts.length !== 3) return false;
        const header = JSON.parse(atob(parts[0]));
        const payload = JSON.parse(atob(parts[1]));
        return !!(header.alg && payload.iss && payload.aud);
    } catch (error) {
        return false;
    }
}

console.log('Access Token is JWT:', isJWT(user.access_token));
console.log('ID Token is JWT:', isJWT(user.id_token));
```

### Step 2: Test Login Flow

1. **Enable Microsoft social login**
2. **Configure with ID token response type**
3. **Test with personal Microsoft account**
4. **Check browser console for token selection**

### Step 3: Verify Backend Validation

1. **Check backend logs** for successful JWT validation
2. **Verify issuer and audience** match configuration
3. **Test API calls** with the selected token

## Expected Token Structure

### ID Token (JWT)
```json
{
  "aud": "your-consumer-app-client-id",
  "iss": "https://login.microsoftonline.com/9188040d-6c67-4c5b-b112-36a304b66dad/v2.0",
  "sub": "user-id",
  "exp": 1640995200,
  "iat": 1640991600,
  "name": "User Name",
  "email": "user@outlook.com",
  "preferred_username": "user@outlook.com"
}
```

### Access Token (May be Opaque)
```
// Opaque token format (not JWT)
M.R3_BAY.c0K8nfXlQbNrbRbk... (long opaque string)
```

## Troubleshooting

### Issue: "Only the Authorization Code flow (with PKCE) is supported"

**Cause**: Using unsupported response type for Microsoft consumer endpoints
**Solution**: 
1. Use `response_type: 'code'` (not `'code id_token'`)
2. Microsoft consumer endpoints only support Authorization Code flow with PKCE
3. The oidc-client-ts library handles PKCE automatically

### Issue: Still Getting JWT Validation Errors

**Cause**: Backend still trying to validate opaque access token
**Solution**: 
1. Ensure frontend is using ID token
2. Check that `response_type: 'code'` is set (not `'code id_token'`)
3. Verify backend accepts ID token issuer and audience

### Issue: ID Token Not Available

**Cause**: OIDC client not requesting ID token
**Solution**:
1. Add `response_type: 'code id_token'` to configuration
2. Ensure `scope: 'openid profile email'` includes `openid`
3. Check OIDC client configuration

### Issue: Backend Rejects ID Token

**Cause**: Backend configuration doesn't match ID token claims
**Solution**:
1. Update `allowed-issuers` to include consumer issuer
2. Update `authorization-audiences` to include your client ID
3. Restart backend application

## Alternative Solutions

### Option 1: Use Microsoft Graph API (Enterprise-like)

Configure Microsoft social login to use Microsoft Graph API audience:

```typescript
microsoftSocial: {
  // ... other config
  scope: 'openid profile email https://graph.microsoft.com/User.Read',
  // This may return JWT access tokens
}
```

### Option 2: Use UserInfo Endpoint

Instead of relying on tokens, use the userinfo endpoint:

```typescript
microsoftSocial: {
  // ... other config
  loadUserInfo: true,
  // Fetch user info from userinfo endpoint
}
```

## Best Practices

### 1. Token Selection Strategy
- **Primary**: Use ID token for Microsoft social login
- **Fallback**: Use access token if it's a valid JWT
- **Validation**: Always check token format before use

### 2. Error Handling
- **Graceful Degradation**: Handle both JWT and opaque tokens
- **Clear Logging**: Log token selection decisions
- **User Feedback**: Provide clear error messages

### 3. Security Considerations
- **Token Validation**: Always validate JWT tokens
- **Scope Limitation**: Request minimal scopes needed
- **Token Storage**: Store tokens securely

## Related Documentation

- [Microsoft Social Login Configuration](./MICROSOFT_LOGIN_CONFIGURATION.md)
- [OIDC Implementation](../../structures-core/OIDC_IMPLEMENTATION.md)
- [Microsoft Social Documentation](../oidc-docs/social/microsoft-social.md) 