# Basic Authentication Configuration

## Overview

The login page now supports configurable basic username/password authentication. This allows you to hide the basic authentication form when only OIDC providers are desired.

## Environment Variable

### VITE_BASIC_AUTH_ENABLED

Controls whether the basic username/password authentication form is displayed on the login page.

**Default**: `true` (enabled)

**Values**:
- `true` or unset: Basic authentication form is shown
- `false`: Basic authentication form is hidden

## Configuration Examples

### Enable Basic Authentication (Default)
```bash
# Basic auth enabled (default behavior)
VITE_BASIC_AUTH_ENABLED=true
```

### Disable Basic Authentication
```bash
# Hide basic auth form, show only OIDC providers
VITE_BASIC_AUTH_ENABLED=false
```

### Environment-specific Configuration

#### Development Environment
```bash
# .env.development
VITE_BASIC_AUTH_ENABLED=true
VITE_OIDC_OKTA_ENABLED=true
VITE_OIDC_KEYCLOAK_ENABLED=true
```

#### Production Environment
```bash
# .env.production
VITE_BASIC_AUTH_ENABLED=false
VITE_OIDC_OKTA_ENABLED=true
VITE_OIDC_KEYCLOAK_ENABLED=false
```

## Login Page Behavior

### When Basic Auth is Enabled (Default)
- Username/Password form is displayed
- "OR" separator is shown if OIDC providers are enabled
- "Forgot Password" link is displayed
- OIDC provider buttons are shown below the form

### When Basic Auth is Disabled
- Username/Password form is hidden
- No "OR" separator is shown
- "Forgot Password" link is hidden
- Only OIDC provider buttons are displayed

## Use Cases

### Development/Testing
- Enable basic auth for easy testing
- Use hardcoded credentials (admin/structures)
- Quick access without OIDC setup

### Production with OIDC Only
- Disable basic auth for security
- Require OIDC authentication
- No fallback to username/password

### Mixed Authentication
- Enable basic auth as fallback
- Configure OIDC providers
- Allow both authentication methods

## Security Considerations

### When Disabled
- No username/password form is rendered
- Reduces attack surface
- Forces OIDC authentication
- Prevents basic auth bypass attempts

### When Enabled
- Provides fallback authentication
- Useful for development and testing
- Should be disabled in production if OIDC is preferred

## Integration with OIDC

The basic authentication configuration works alongside OIDC provider configuration:

```bash
# Example: OIDC-only setup
VITE_BASIC_AUTH_ENABLED=false
VITE_OIDC_OKTA_ENABLED=true
VITE_OIDC_KEYCLOAK_ENABLED=false
VITE_OIDC_GOOGLE_ENABLED=false
VITE_OIDC_MICROSOFT_ENABLED=false
VITE_OIDC_GITHUB_ENABLED=false
```

## Backend Configuration

The backend `TemporarySecurityService` in `structures-server` provides the basic authentication implementation:

```java
@Component
@ConditionalOnProperty(prefix = "structures.oidc-auth-verifier", name = "enabled", havingValue = "false", matchIfMissing = true)
public class TemporarySecurityService implements SecurityService {
    // Username: admin
    // Password: structures
}
```

## Migration Guide

### From Previous Version
- No changes required - basic auth is enabled by default
- Existing functionality is preserved
- OIDC providers continue to work as before

### To Disable Basic Auth
1. Set `VITE_BASIC_AUTH_ENABLED=false` in your environment
2. Ensure at least one OIDC provider is enabled
3. Test the login flow with OIDC only

### To Enable Basic Auth
1. Set `VITE_BASIC_AUTH_ENABLED=true` (or leave unset)
2. Basic auth form will be displayed
3. Can be used alongside OIDC providers 