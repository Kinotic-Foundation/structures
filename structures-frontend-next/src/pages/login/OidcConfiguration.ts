import { type UserManagerSettings, WebStorageStateStore, UserManager } from 'oidc-client-ts';

export type OidcProvider = 'okta' | 'keycloak' | 'google' | 'github' | 'microsoft' | 'custom';

// Configuration specific to public clients
export interface PublicClientConfig {
  isPublicClient: boolean;
  responseType: string;
  responseMode?: string;
}

export interface OidcProviderConfig extends Partial<UserManagerSettings> {
  client_id: string;
  client_secret?: string;
  authority: string;
  redirect_uri: string;
  post_logout_redirect_uri?: string;
  silent_redirect_uri?: string;
  loadUserInfo?: boolean;
  metadata?: {
    authorization_endpoint?: string;
    token_endpoint?: string;
    userinfo_endpoint?: string;
    end_session_endpoint?: string;
    jwks_uri?: string;
  };
  // Public client specific settings
  publicClient?: PublicClientConfig;
  customParams?: Record<string, string>;
}

// so we should have an organization object that 
// has exactly one provider for the org - with the original default admin account
// and then an Application - which might have a single provider or multiple
// we could also think about a user which can use a social provider individually
// we will need to figure out the best workflow but i believe the same config can 
// be used for all of these use cases

export interface OidcConfiguration {
  defaultProvider: OidcProvider;
  providers: Record<OidcProvider, OidcProviderConfig>;
  defaultSettings: Partial<UserManagerSettings>;
}

// Default settings that work across providers
const DEFAULT_SETTINGS: Partial<UserManagerSettings> = {
  // For PKCE, we use 'code' response type and 'query' response mode
  response_type: 'code',
  response_mode: 'query',
  scope: 'openid profile email offline_access',
  loadUserInfo: true,
  monitorSession: true,
  userStore: new WebStorageStateStore({ store: window.localStorage }),
  stateStore: new WebStorageStateStore({ store: window.localStorage }),
  // Remove default metadata to allow automatic discovery
};

// Base configuration that can be extended for specific providers
export const baseOidcConfig: OidcConfiguration = {
  defaultProvider: 'okta',
  defaultSettings: DEFAULT_SETTINGS,
  providers: {
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
      },
      stateStore: new WebStorageStateStore({ store: window.localStorage }),
      userStore: new WebStorageStateStore({ store: window.localStorage }),
      monitorSession: true
    },
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
      // Remove explicit metadata to let oidc-client-ts discover endpoints automatically
      // This prevents authority mismatch issues
    },
    google: {
      client_id: '',
      client_secret: '',
      authority: 'https://accounts.google.com',
      redirect_uri: '',
      post_logout_redirect_uri: '',
      silent_redirect_uri: '',
      loadUserInfo: true,
      metadata: {
        authorization_endpoint: 'https://accounts.google.com/o/oauth2/v2/auth',
        token_endpoint: 'https://oauth2.googleapis.com/token',
        userinfo_endpoint: 'https://openidconnect.googleapis.com/v1/userinfo',
        jwks_uri: 'https://www.googleapis.com/oauth2/v3/certs',
      },
    },
    github: {
      client_id: '',
      client_secret: '',
      authority: 'https://github.com',
      redirect_uri: '',
      post_logout_redirect_uri: '',
      silent_redirect_uri: '',
      loadUserInfo: true,
      metadata: {
        authorization_endpoint: 'https://github.com/login/oauth/authorize',
        token_endpoint: 'https://github.com/login/oauth/access_token',
        userinfo_endpoint: 'https://api.github.com/user',
      },
    },
    microsoft: {
      client_id: '',
      client_secret: '',
      authority: 'https://login.microsoftonline.com/common/v2.0',
      redirect_uri: '',
      post_logout_redirect_uri: '',
      silent_redirect_uri: '',
      loadUserInfo: true,
      metadata: {
        authorization_endpoint: 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize',
        token_endpoint: 'https://login.microsoftonline.com/common/oauth2/v2.0/token',
        userinfo_endpoint: 'https://graph.microsoft.com/oidc/userinfo',
        end_session_endpoint: 'https://login.microsoftonline.com/common/oauth2/v2.0/logout',
        jwks_uri: 'https://login.microsoftonline.com/common/discovery/v2.0/keys',
      },
    },
    custom: {
      client_id: '',
      client_secret: '',
      authority: '',
      redirect_uri: '',
      post_logout_redirect_uri: '',
      silent_redirect_uri: '',
      loadUserInfo: true,
      metadata: {
        authorization_endpoint: '',
        token_endpoint: '',
        userinfo_endpoint: '',
        end_session_endpoint: '',
        jwks_uri: '',
      },
    }
  }
};

// Helper function to get provider-specific configuration
export const getProviderConfig = (provider: OidcProvider): OidcProviderConfig => {
  return baseOidcConfig.providers[provider];
};

// Helper function to create UserManager settings for a specific provider
export const createUserManagerSettings = (provider: OidcProvider): UserManagerSettings => {
  const config = getProviderConfig(provider);
  const defaultSettings = baseOidcConfig.defaultSettings;
  
  // Merge settings with public client configuration if applicable
  const settings = {
    ...defaultSettings,
    ...config,
    // If it's a public client, ensure proper PKCE settings
    ...(config.publicClient?.isPublicClient ? {
      response_type: config.publicClient.responseType,
      response_mode: config.publicClient.responseMode,
      // Remove client_secret if it exists
      client_secret: undefined
    } : {}),
    // Only include metadata if it's explicitly provided in the config
    // This allows automatic discovery for providers without explicit metadata
    ...(config.metadata ? {
      metadata: {
        ...defaultSettings.metadata,
        ...config.metadata,
      }
    } : {}),
  } as UserManagerSettings;

  return settings;
};

// Helper function to create a UserManager instance for a specific provider
export const createUserManager = (provider: OidcProvider) => {
  const settings = createUserManagerSettings(provider);
  
  // Clear any cached metadata for this provider to force fresh discovery
  const cacheKey = `oidc.user.${settings.authority}.${settings.client_id}`;
  localStorage.removeItem(cacheKey);
  
  console.log('Creating UserManager with settings:', {
    authority: settings.authority,
    client_id: settings.client_id,
    redirect_uri: settings.redirect_uri,
    response_type: settings.response_type,
    response_mode: settings.response_mode
  });
  
  return new UserManager(settings);
};

export default baseOidcConfig; 