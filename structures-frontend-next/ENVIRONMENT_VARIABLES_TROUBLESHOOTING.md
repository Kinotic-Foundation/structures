# Environment Variables Troubleshooting Guide

## Issue: Environment Variables Not Loading from .env.local

If your environment variables from `.env.local` are not being picked up by the Vite development server, follow these troubleshooting steps:

## 1. Check Environment Variable Loading Order

Vite loads environment files in this order (later files override earlier ones):
1. `.env` (loaded first)
2. `.env.local` (loaded second, should override)

## 2. Verify Your .env.local File

Your `.env.local` file should contain:
```bash
# Okta OIDC
VITE_OKTA_CLIENT_ID=0oatiee2ejcw5gD9Z697
VITE_OKTA_AUTHORITY=https://integrator-3872331.okta.com/oauth2/default

# Keycloak OIDC
VITE_KEYCLOAK_CLIENT_ID=structures-client
VITE_KEYCLOAK_AUTHORITY=http://localhost:8888/auth/realms/master
```

## 3. Check for Conflicts in .env File

The main `.env` file should NOT have empty values that might override `.env.local`:

**Problematic .env file:**
```bash
VITE_OKTA_CLIENT_ID=    # Empty value
VITE_OKTA_AUTHORITY=    # Empty value
```

**Solution:** Comment out or remove empty values in `.env`:
```bash
# VITE_OKTA_CLIENT_ID=
# VITE_OKTA_AUTHORITY=
```

## 4. Restart the Development Server

Environment variables are loaded when the server starts. If you change `.env.local`, restart the server:

```bash
# Stop the current server
pkill -f "vite"

# Start the server again
npm run dev
```

## 5. Verify Environment Variables Are Loaded

The application now includes debug logging. Check your browser's developer console for:

```
OIDC Environment Variables: {
  VITE_OIDC_OKTA_ENABLED: "true",
  VITE_OKTA_CLIENT_ID: "0oatiee2ejcw5gD9Z697",
  VITE_OKTA_AUTHORITY: "https://integrator-3872331.okta.com/oauth2/default",
  ...
}
```

## 6. Common Issues and Solutions

### Issue: Variables show as undefined
**Cause:** Missing `VITE_` prefix
**Solution:** All client-side variables must be prefixed with `VITE_`

### Issue: Variables show empty strings
**Cause:** Empty values in `.env` file overriding `.env.local`
**Solution:** Remove or comment out empty values in `.env`

### Issue: Variables not updating after changes
**Cause:** Development server not restarted
**Solution:** Restart the development server

### Issue: Variables work in production but not development
**Cause:** Different environment file loading
**Solution:** Check that `.env.local` exists and has correct values

## 7. Testing Environment Variables

You can test if variables are loaded by adding this to your browser console:

```javascript
console.log('Environment Variables:', {
  VITE_OKTA_CLIENT_ID: import.meta.env.VITE_OKTA_CLIENT_ID,
  VITE_OKTA_AUTHORITY: import.meta.env.VITE_OKTA_AUTHORITY,
  VITE_DEBUG: import.meta.env.VITE_DEBUG
});
```

## 8. File Structure

Your environment files should be structured like this:

```
structures-frontend-next/
├── .env                    # Default values (can be empty)
├── .env.local             # Local overrides (your actual values)
└── src/
    └── pages/
        └── login/
            └── OidcConfiguration.ts  # Uses import.meta.env
```

## 9. Vite Configuration

The `vite.config.ts` file doesn't need special configuration for environment variables. Vite automatically loads `.env` files.

## 10. Debug Mode

Enable debug mode in your `.env.local`:
```bash
VITE_DEBUG=true
```

This will log environment variables to the console when the application loads.

## Next Steps

1. Comment out empty values in your `.env` file
2. Restart the development server
3. Check the browser console for the debug output
4. Verify that your Okta and Keycloak configurations are now populated

If you're still having issues, check the browser's network tab to see if the application is making requests to the correct OIDC endpoints. 