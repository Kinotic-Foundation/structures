# Microsoft Login Configuration

This guide explains how to configure both Microsoft Enterprise (organizational) and Microsoft Social (personal) login options.

## Configuration Options

### Option 1: Microsoft Enterprise (Organizational Accounts)
For users with Microsoft Entra ID (Azure AD) accounts from their organization.

**Environment Variables:**
```bash
# Enable Microsoft Enterprise OIDC
VITE_OIDC_MICROSOFT_ENABLED=true
VITE_MICROSOFT_CLIENT_ID=your-enterprise-app-client-id
VITE_MICROSOFT_AUTHORITY=https://login.microsoftonline.com/your-tenant-id/v2.0
VITE_MICROSOFT_REDIRECT_URI=http://localhost:5173/login
VITE_MICROSOFT_POST_LOGOUT_REDIRECT_URI=http://localhost:5173
VITE_MICROSOFT_SILENT_REDIRECT_URI=http://localhost:5173/login/silent-renew
```

**Backend Configuration:**
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://sts.windows.net"
    authorization-audiences:
      - "00000003-0000-0000-c000-000000000000"  # Microsoft Graph API
```

### Option 2: Microsoft Social (Personal Accounts)
For users with personal Microsoft accounts (Outlook.com, Hotmail, etc.).

**Environment Variables:**
```bash
# Enable Microsoft Social OIDC
VITE_OIDC_MICROSOFT_SOCIAL_ENABLED=true
VITE_MICROSOFT_SOCIAL_CLIENT_ID=your-consumer-app-client-id
VITE_MICROSOFT_SOCIAL_AUTHORITY=https://login.microsoftonline.com/consumers/v2.0
VITE_MICROSOFT_SOCIAL_REDIRECT_URI=http://localhost:5173/login
VITE_MICROSOFT_SOCIAL_POST_LOGOUT_REDIRECT_URI=http://localhost:5173
VITE_MICROSOFT_SOCIAL_SILENT_REDIRECT_URI=http://localhost:5173/login/silent-renew
```

**Backend Configuration:**
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://login.microsoftonline.com/9188040d-6c67-4c5b-b112-36a304b66dad/v2.0"
    authorization-audiences:
      - "your-consumer-app-client-id"
```

### Option 3: Both Microsoft Options
You can enable both enterprise and social login simultaneously.

**Environment Variables:**
```bash
# Enable both Microsoft options
VITE_OIDC_MICROSOFT_ENABLED=true
VITE_OIDC_MICROSOFT_SOCIAL_ENABLED=true

# Enterprise configuration
VITE_MICROSOFT_CLIENT_ID=your-enterprise-app-client-id
VITE_MICROSOFT_AUTHORITY=https://login.microsoftonline.com/your-tenant-id/v2.0
VITE_MICROSOFT_REDIRECT_URI=http://localhost:5173/login
VITE_MICROSOFT_POST_LOGOUT_REDIRECT_URI=http://localhost:5173
VITE_MICROSOFT_SILENT_REDIRECT_URI=http://localhost:5173/login/silent-renew

# Social configuration
VITE_MICROSOFT_SOCIAL_CLIENT_ID=your-consumer-app-client-id
VITE_MICROSOFT_SOCIAL_AUTHORITY=https://login.microsoftonline.com/consumers/v2.0
VITE_MICROSOFT_SOCIAL_REDIRECT_URI=http://localhost:5173/login
VITE_MICROSOFT_SOCIAL_POST_LOGOUT_REDIRECT_URI=http://localhost:5173
VITE_MICROSOFT_SOCIAL_SILENT_REDIRECT_URI=http://localhost:5173/login/silent-renew
```

**Backend Configuration:**
```yaml
structures:
  oidc-auth-verifier:
    enabled: true
    allowed-issuers:
      - "https://sts.windows.net"  # Enterprise
      - "https://login.microsoftonline.com/9188040d-6c67-4c5b-b112-36a304b66dad/v2.0"  # Social
    authorization-audiences:
      - "00000003-0000-0000-c000-000000000000"  # Microsoft Graph API (Enterprise)
      - "your-consumer-app-client-id"  # Social
```

## UI Display

When both options are enabled, users will see:

1. **"Continue with Microsoft (Work)"** - For organizational accounts
2. **"Continue with Microsoft (Personal)"** - For personal accounts

## Azure Portal Setup

### Enterprise App Registration
1. Go to [Azure Portal](https://portal.azure.com/)
2. Navigate to "Microsoft Entra ID" > "App registrations"
3. Create new registration for organizational accounts
4. Configure authentication for SPA platform
5. Add redirect URIs for your application

### Social App Registration
1. Go to [Azure Portal](https://portal.azure.com/)
2. Navigate to "Microsoft Entra ID" > "App registrations"
3. Create new registration for personal accounts
4. Set "Supported account types" to "Personal Microsoft accounts only"
5. Configure authentication for SPA platform
6. Add redirect URIs for your application

## Testing

### Test Enterprise Login
1. Enable `VITE_OIDC_MICROSOFT_ENABLED=true`
2. Configure enterprise client ID and authority
3. Test with organizational Microsoft account
4. Verify JWT token has enterprise issuer and audience

### Test Social Login
1. Enable `VITE_OIDC_MICROSOFT_SOCIAL_ENABLED=true`
2. Configure social client ID and authority
3. Test with personal Microsoft account (Outlook.com, Hotmail)
4. Verify JWT token has consumer issuer and audience

### Test Both Options
1. Enable both environment variables
2. Configure both client IDs and authorities
3. Test with both account types
4. Verify correct issuer and audience validation

## Key Differences

| Aspect | Enterprise | Social |
|--------|------------|--------|
| **Authority** | `https://login.microsoftonline.com/your-tenant-id/v2.0` | `https://login.microsoftonline.com/consumers/v2.0` |
| **Issuer** | `https://sts.windows.net` | `https://login.microsoftonline.com/9188040d-6c67-4c5b-b112-36a304b66dad/v2.0` |
| **Audience** | Microsoft Graph API or custom | Your consumer app client ID |
| **Accounts** | Organizational accounts | Personal accounts |
| **UI Label** | "Continue with Microsoft (Work)" | "Continue with Microsoft (Personal)" |

## Troubleshooting

### Common Issues

1. **"Invalid issuer" Error**
   - Check that backend `allowed-issuers` includes the correct issuer for your configuration

2. **"Invalid audience" Error**
   - Verify that backend `authorization-audiences` matches the JWT token audience

3. **"Account type not supported" Error**
   - Ensure app registration supports the correct account types
   - Enterprise: Organizational accounts
   - Social: Personal Microsoft accounts only

4. **"Redirect URI mismatch" Error**
   - Verify redirect URIs match exactly between Azure Portal and environment variables

## Related Documentation

- [Microsoft Entra ID (Enterprise)](../oidc-docs/entra/) - Enterprise Microsoft authentication
- [Microsoft Social Login](../oidc-docs/social/microsoft-social.md) - Social Microsoft authentication
- [OIDC Implementation](../../structures-core/OIDC_IMPLEMENTATION.md) - Core OIDC implementation 