import { type UserManagerSettings, WebStorageStateStore, UserManager } from 'oidc-client-ts';
import { configService } from '@/util/config';

export type OidcProvider = 'okta' | 'keycloak' | 'google' | 'github' | 'microsoft' | 'microsoftSocial' | 'custom' | 'apple';

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

const DEFAULT_SETTINGS: Partial<UserManagerSettings> = {
  response_type: 'code',
  response_mode: 'query',
  scope: 'openid profile email offline_access',
  loadUserInfo: true,
  monitorSession: true,
  userStore: new WebStorageStateStore({ store: window.localStorage }),
  stateStore: new WebStorageStateStore({ store: window.localStorage }),
};

// Create a function to get the OIDC configuration dynamically
export async function getOidcConfiguration(): Promise<OidcConfiguration> {
  const oidcConfig = await configService.getOidcConfig();
  const isDebug = await configService.isDebugEnabled();

  // Debug: Log configuration for troubleshooting
  if (isDebug) {
    console.log('OIDC Configuration:', oidcConfig);
  }

  return {
    defaultProvider: 'keycloak',
    defaultSettings: DEFAULT_SETTINGS,
    providers: {
      okta: {
        enabled: oidcConfig.okta.enabled,
        client_id: oidcConfig.okta.client_id,
        client_secret: '',
        authority: oidcConfig.okta.authority,
        redirect_uri: oidcConfig.okta.redirect_uri,
        post_logout_redirect_uri: oidcConfig.okta.post_logout_redirect_uri,
        silent_redirect_uri: oidcConfig.okta.silent_redirect_uri,
        loadUserInfo: true,
        publicClient: {
          isPublicClient: true,
          responseType: 'code',
          responseMode: 'query'
        },
        monitorSession: true
      },
      keycloak: {
        enabled: oidcConfig.keycloak.enabled,
        client_id: oidcConfig.keycloak.client_id,
        client_secret: '',
        authority: oidcConfig.keycloak.authority,
        redirect_uri: oidcConfig.keycloak.redirect_uri,
        post_logout_redirect_uri: oidcConfig.keycloak.post_logout_redirect_uri,
        silent_redirect_uri: oidcConfig.keycloak.silent_redirect_uri,
        loadUserInfo: true,
        publicClient: {
          isPublicClient: true,
          responseType: 'code',
          responseMode: 'query'
        },
      },
      google: {
        enabled: oidcConfig.google.enabled,
        client_id: oidcConfig.google.client_id,
        client_secret: '',
        authority: oidcConfig.google.authority,
        redirect_uri: oidcConfig.google.redirect_uri,
        post_logout_redirect_uri: oidcConfig.google.post_logout_redirect_uri,
        silent_redirect_uri: oidcConfig.google.silent_redirect_uri,
        loadUserInfo: true,
      },
      github: {
        enabled: oidcConfig.github.enabled,
        client_id: oidcConfig.github.client_id,
        client_secret: '',
        authority: oidcConfig.github.authority,
        redirect_uri: oidcConfig.github.redirect_uri,
        post_logout_redirect_uri: oidcConfig.github.post_logout_redirect_uri,
        silent_redirect_uri: oidcConfig.github.silent_redirect_uri,
        loadUserInfo: true,
      },
      microsoft: {
        enabled: oidcConfig.microsoft.enabled,
        client_id: oidcConfig.microsoft.client_id,
        client_secret: '',
        authority: oidcConfig.microsoft.authority,
        redirect_uri: oidcConfig.microsoft.redirect_uri,
        post_logout_redirect_uri: oidcConfig.microsoft.post_logout_redirect_uri,
        silent_redirect_uri: oidcConfig.microsoft.silent_redirect_uri,
        loadUserInfo: true,
        // Add custom scope if specified (for custom audience in v2.0)
        ...(oidcConfig.microsoft.resource && {
          scope: `openid profile email ${oidcConfig.microsoft.resource}`
        }),
      },
      microsoftSocial: {
        enabled: oidcConfig.microsoftSocial.enabled,
        client_id: oidcConfig.microsoftSocial.client_id,
        client_secret: '',
        authority: oidcConfig.microsoftSocial.authority,
        redirect_uri: oidcConfig.microsoftSocial.redirect_uri,
        post_logout_redirect_uri: oidcConfig.microsoftSocial.post_logout_redirect_uri,
        silent_redirect_uri: oidcConfig.microsoftSocial.silent_redirect_uri,
        loadUserInfo: true,
        scope: 'openid profile email',
        response_type: 'code', // Use Authorization Code flow with PKCE
        response_mode: 'query',
      },
             custom: {
         enabled: oidcConfig.custom.enabled,
         client_id: oidcConfig.custom.client_id,
         client_secret: '',
         authority: oidcConfig.custom.authority,
         redirect_uri: oidcConfig.custom.redirect_uri,
         post_logout_redirect_uri: oidcConfig.custom.post_logout_redirect_uri,
         silent_redirect_uri: oidcConfig.custom.silent_redirect_uri,
         loadUserInfo: true,
         metadata: oidcConfig.custom.metadata,
       },
       apple: {
         enabled: oidcConfig.apple.enabled,
         client_id: oidcConfig.apple.client_id,
         client_secret: '',
         authority: oidcConfig.apple.authority,
         redirect_uri: oidcConfig.apple.redirect_uri,
         post_logout_redirect_uri: oidcConfig.apple.post_logout_redirect_uri,
         silent_redirect_uri: oidcConfig.apple.silent_redirect_uri,
         loadUserInfo: true,
       }
    }
  };
}

export async function getProviderConfig(provider: OidcProvider): Promise<OidcProviderConfig> {
  const config = await getOidcConfiguration();
  return config.providers[provider];
}

export async function createUserManagerSettings(provider: OidcProvider): Promise<UserManagerSettings> {
  const config = await getProviderConfig(provider);
  const oidcConfig = await getOidcConfiguration();
  const defaultSettings = oidcConfig.defaultSettings;
  
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
}

export async function createUserManager(provider: OidcProvider) {
  const settings = await createUserManagerSettings(provider);
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
}

// For backward compatibility, export a default configuration
export async function getDefaultOidcConfig(): Promise<OidcConfiguration> {
  return await getOidcConfiguration();
} 