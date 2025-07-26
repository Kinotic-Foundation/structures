import { type UserManagerSettings, WebStorageStateStore, UserManager } from 'oidc-client-ts';

export type OidcProvider = 'okta' | 'keycloak' | 'google' | 'github' | 'microsoft' | 'microsoftSocial' | 'custom';

export interface OidcProviderConfig extends Partial<UserManagerSettings> {
  enabled: boolean;
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
  publicClient?: PublicClientConfig;
  customParams?: Record<string, string>;
}

export interface PublicClientConfig {
  isPublicClient: boolean;
  responseType: string;
  responseMode?: string;
}

export interface OidcConfiguration {
  defaultProvider: OidcProvider;
  providers: Record<OidcProvider, OidcProviderConfig>;
  defaultSettings: Partial<UserManagerSettings>;
}

const env = import.meta.env;

// Debug: Log environment variables for troubleshooting
if (env.VITE_DEBUG === 'true') {
  console.log('OIDC Environment Variables:', {
    VITE_OIDC_OKTA_ENABLED: env.VITE_OIDC_OKTA_ENABLED,
    VITE_OKTA_CLIENT_ID: env.VITE_OKTA_CLIENT_ID,
    VITE_OKTA_AUTHORITY: env.VITE_OKTA_AUTHORITY,
    VITE_OIDC_KEYCLOAK_ENABLED: env.VITE_OIDC_KEYCLOAK_ENABLED,
    VITE_KEYCLOAK_CLIENT_ID: env.VITE_KEYCLOAK_CLIENT_ID,
    VITE_KEYCLOAK_AUTHORITY: env.VITE_KEYCLOAK_AUTHORITY,
  });
}

const DEFAULT_SETTINGS: Partial<UserManagerSettings> = {
  response_type: 'code',
  response_mode: 'query',
  scope: 'openid profile email offline_access',
  loadUserInfo: true,
  monitorSession: true,
  userStore: new WebStorageStateStore({ store: window.localStorage }),
  stateStore: new WebStorageStateStore({ store: window.localStorage }),
};

export const baseOidcConfig: OidcConfiguration = {
  defaultProvider: 'keycloak',
  defaultSettings: DEFAULT_SETTINGS,
  providers: {
    okta: {
      enabled: env.VITE_OIDC_OKTA_ENABLED === 'true',
      client_id: env.VITE_OKTA_CLIENT_ID || '',
      client_secret: '',
      authority: env.VITE_OKTA_AUTHORITY || '',
      redirect_uri: env.VITE_OKTA_REDIRECT_URI || 'http://localhost:5173/login',
      post_logout_redirect_uri: env.VITE_OKTA_POST_LOGOUT_REDIRECT_URI || 'http://localhost:5173',
      silent_redirect_uri: env.VITE_OKTA_SILENT_REDIRECT_URI || 'http://localhost:5173/login/silent-renew',
      loadUserInfo: true,
      publicClient: {
        isPublicClient: true,
        responseType: 'code',
        responseMode: 'query'
      },
      // metadata: {
      //   authorization_endpoint: '',
      //   token_endpoint: '',
      //   userinfo_endpoint: '',
      //   end_session_endpoint: '',
      //   jwks_uri: ''
      // },
      // stateStore: new WebStorageStateStore({ store: window.localStorage }),
      // userStore: new WebStorageStateStore({ store: window.localStorage }),
      monitorSession: true
    },
    keycloak: {
      enabled: env.VITE_OIDC_KEYCLOAK_ENABLED === 'true',
      client_id: env.VITE_KEYCLOAK_CLIENT_ID || '',
      client_secret: '',
      authority: env.VITE_KEYCLOAK_AUTHORITY || '',
      redirect_uri: env.VITE_KEYCLOAK_REDIRECT_URI || 'http://localhost:5173/login',
      post_logout_redirect_uri: env.VITE_KEYCLOAK_POST_LOGOUT_REDIRECT_URI || 'http://localhost:5173',
      silent_redirect_uri: env.VITE_KEYCLOAK_SILENT_REDIRECT_URI || 'http://localhost:5173/login/silent-renew',
      loadUserInfo: true,
      publicClient: {
        isPublicClient: true,
        responseType: 'code',
        responseMode: 'query'
      },
      // No explicit metadata - uses automatic discovery
    },
    google: {
      enabled: env.VITE_OIDC_GOOGLE_ENABLED === 'true',
      client_id: env.VITE_GOOGLE_CLIENT_ID || '',
      client_secret: '',
      authority: env.VITE_GOOGLE_AUTHORITY || 'https://accounts.google.com',
      redirect_uri: env.VITE_GOOGLE_REDIRECT_URI || '',
      post_logout_redirect_uri: env.VITE_GOOGLE_POST_LOGOUT_REDIRECT_URI || '',
      silent_redirect_uri: env.VITE_GOOGLE_SILENT_REDIRECT_URI || '',
      loadUserInfo: true,
      // metadata: {
      //   authorization_endpoint: 'https://accounts.google.com/o/oauth2/v2/auth',
      //   token_endpoint: 'https://oauth2.googleapis.com/token',
      //   userinfo_endpoint: 'https://openidconnect.googleapis.com/v1/userinfo',
      //   jwks_uri: 'https://www.googleapis.com/oauth2/v3/certs',
      // },
    },
    github: {
      enabled: env.VITE_OIDC_GITHUB_ENABLED === 'true',
      client_id: env.VITE_GITHUB_CLIENT_ID || '',
      client_secret: '',
      authority: env.VITE_GITHUB_AUTHORITY || 'https://github.com',
      redirect_uri: env.VITE_GITHUB_REDIRECT_URI || '',
      post_logout_redirect_uri: env.VITE_GITHUB_POST_LOGOUT_REDIRECT_URI || '',
      silent_redirect_uri: env.VITE_GITHUB_SILENT_REDIRECT_URI || '',
      loadUserInfo: true,
      // metadata: {
      //   authorization_endpoint: 'https://github.com/login/oauth/authorize',
      //   token_endpoint: 'https://github.com/login/oauth/access_token',
      //   userinfo_endpoint: 'https://api.github.com/user',
      // },
    },
    microsoft: {
      enabled: env.VITE_OIDC_MICROSOFT_ENABLED === 'true',
      client_id: env.VITE_MICROSOFT_CLIENT_ID || '',
      client_secret: '',
      authority: env.VITE_MICROSOFT_AUTHORITY || 'https://login.microsoftonline.com/common/v2.0',
      redirect_uri: env.VITE_MICROSOFT_REDIRECT_URI || '',
      post_logout_redirect_uri: env.VITE_MICROSOFT_POST_LOGOUT_REDIRECT_URI || '',
      silent_redirect_uri: env.VITE_MICROSOFT_SILENT_REDIRECT_URI || '',
      loadUserInfo: true,
      // Add custom scope if specified (for custom audience in v2.0)
      ...(env.VITE_MICROSOFT_RESOURCE && {
        scope: `openid profile email ${env.VITE_MICROSOFT_RESOURCE}`
      }),
      // metadata: {
      //   // Use dynamic endpoints based on authority
      //   authorization_endpoint: env.VITE_MICROSOFT_AUTHORITY?.replace('/v2.0', '/oauth2/v2.0/authorize') || 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize',
      //   token_endpoint: env.VITE_MICROSOFT_AUTHORITY?.replace('/v2.0', '/oauth2/v2.0/token') || 'https://login.microsoftonline.com/common/oauth2/v2.0/token',
      //   userinfo_endpoint: 'https://graph.microsoft.com/oidc/userinfo',
      //   end_session_endpoint: env.VITE_MICROSOFT_AUTHORITY?.replace('/v2.0', '/oauth2/v2.0/logout') || 'https://login.microsoftonline.com/common/oauth2/v2.0/logout',
      //   jwks_uri: env.VITE_MICROSOFT_AUTHORITY?.replace('/v2.0', '/discovery/v2.0/keys') || 'https://login.microsoftonline.com/common/discovery/v2.0/keys',
      // },
    },
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
      // metadata: {
      //   authorization_endpoint: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize',
      //   token_endpoint: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/token',
      //   userinfo_endpoint: 'https://graph.microsoft.com/oidc/userinfo',
      //   end_session_endpoint: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/logout',
      //   jwks_uri: 'https://login.microsoftonline.com/consumers/discovery/v2.0/keys',
      // },
    },
    custom: {
      enabled: env.VITE_OIDC_CUSTOM_ENABLED === 'true',
      client_id: env.VITE_CUSTOM_CLIENT_ID || '',
      client_secret: '',
      authority: env.VITE_CUSTOM_AUTHORITY || '',
      redirect_uri: env.VITE_CUSTOM_REDIRECT_URI || '',
      post_logout_redirect_uri: env.VITE_CUSTOM_POST_LOGOUT_REDIRECT_URI || '',
      silent_redirect_uri: env.VITE_CUSTOM_SILENT_REDIRECT_URI || '',
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

export const getProviderConfig = (provider: OidcProvider): OidcProviderConfig => {
  return baseOidcConfig.providers[provider];
};

export const createUserManagerSettings = (provider: OidcProvider): UserManagerSettings => {
  const config = getProviderConfig(provider);
  const defaultSettings = baseOidcConfig.defaultSettings;
  
  const settings = {
    ...defaultSettings,
    ...config,
    ...(config.publicClient?.isPublicClient ? {
      response_type: config.publicClient.responseType,
      response_mode: config.publicClient.responseMode,
      client_secret: undefined
    } : {}),
    ...(config.metadata ? {
      metadata: {
        ...defaultSettings.metadata,
        ...config.metadata,
      }
    } : {}),
  } as UserManagerSettings;

  return settings;
};

export const createUserManager = (provider: OidcProvider) => {
  const settings = createUserManagerSettings(provider);
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