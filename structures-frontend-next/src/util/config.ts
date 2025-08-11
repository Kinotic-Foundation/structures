interface AppConfig {
  // OIDC Configuration
  oidc: {
    okta: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
    };
    keycloak: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
    };
    google: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
    };
    github: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
    };
    microsoft: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
      resource?: string;
    };
    microsoftSocial: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
    };
    custom: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
      metadata: {
        authorization_endpoint: string;
        token_endpoint: string;
        userinfo_endpoint: string;
        end_session_endpoint: string;
        jwks_uri: string;
      };
    };
    apple: {
      enabled: boolean;
      client_id: string;
      authority: string;
      redirect_uri: string;
      post_logout_redirect_uri: string;
      silent_redirect_uri: string;
    };
  };
  // Basic Auth
  basicAuth: {
    enabled: boolean;
  };
  // Debug
  debug: boolean;
}

class ConfigService {
  private config: AppConfig | null = null;
  private configPromise: Promise<AppConfig> | null = null;

  async loadConfig(): Promise<AppConfig> {
    if (this.config) {
      return this.config;
    }

    if (this.configPromise) {
      return this.configPromise;
    }

    this.configPromise = this.loadConfigFromFile();
    this.config = await this.configPromise;
    return this.config;
  }

  private async loadConfigFromFile(): Promise<AppConfig> {
    // Helper to fetch JSON safely
    const fetchJson = async (path: string): Promise<any | null> => {
      try {
        const resp = await fetch(path);
        if (resp.ok) {
          return await resp.json();
        }
      } catch (_) {
        // ignore
      }
      return null;
    };

    // 1) Preferred location with local override
    const baseConfig1 = await fetchJson('/config/app-config.json');
    const localOverride1a = await fetchJson('/config/app-config.local.json');
    const localOverride1b = await fetchJson('/config/app-config.json.local');
    if (baseConfig1) {
      let merged = this.validateAndMergeConfig(baseConfig1);
      if (localOverride1a) {
        console.log('Applying local override from /config/app-config.local.json');
        merged = this.deepMerge(merged, localOverride1a);
      } else if (localOverride1b) {
        console.log('Applying local override from /config/app-config.json.local');
        merged = this.deepMerge(merged, localOverride1b);
      } else {
        console.log('Loaded configuration from /config/app-config.json');
      }
      return merged;
    }

    // 2) Fallback location with local override
    const baseConfig2 = await fetchJson('/app-config.json');
    const localOverride2a = await fetchJson('/app-config.local.json');
    const localOverride2b = await fetchJson('/app-config.json.local');
    if (baseConfig2) {
      let merged = this.validateAndMergeConfig(baseConfig2);
      if (localOverride2a) {
        console.log('Applying local override from /app-config.local.json');
        merged = this.deepMerge(merged, localOverride2a);
      } else if (localOverride2b) {
        console.log('Applying local override from /app-config.json.local');
        merged = this.deepMerge(merged, localOverride2b);
      } else {
        console.log('Loaded configuration from /app-config.json');
      }
      return merged;
    }

    // 3) Allow local override without a base file (merge onto defaults)
    if (localOverride1a || localOverride1b || localOverride2a || localOverride2b) {
      const defaults = this.getDefaultConfig();
      const overrideOnly = localOverride1a || localOverride1b || localOverride2a || localOverride2b || {};
      console.log('Using defaults with local override only');
      return this.deepMerge(defaults, overrideOnly);
    }

    // 4) Return default configuration if no config file is found
    console.warn('No configuration file found, using default configuration');
    return this.getDefaultConfig();
  }

  private validateAndMergeConfig(config: Partial<AppConfig>): AppConfig {
    const defaultConfig = this.getDefaultConfig();
    
    // Deep merge the loaded config with defaults
    return this.deepMerge(defaultConfig, config);
  }

  private deepMerge<T>(target: T, source: Partial<T>): T {
    const result = { ...target };
    
    for (const key in source) {
      if (source[key] !== undefined) {
        if (typeof source[key] === 'object' && source[key] !== null && 
            typeof result[key] === 'object' && result[key] !== null) {
          result[key] = this.deepMerge(result[key], source[key] as any);
        } else {
          result[key] = source[key] as any;
        }
      }
    }
    
    return result;
  }

  private getDefaultConfig(): AppConfig {
    return {
      oidc: {
        okta: {
          enabled: false,
          client_id: '',
          authority: '',
          redirect_uri: 'http://localhost:5173/login',
          post_logout_redirect_uri: 'http://localhost:5173',
          silent_redirect_uri: 'http://localhost:5173/login/silent-renew',
        },
        keycloak: {
          enabled: true,
          client_id: '',
          authority: '',
          redirect_uri: 'http://localhost:5173/login',
          post_logout_redirect_uri: 'http://localhost:5173',
          silent_redirect_uri: 'http://localhost:5173/login/silent-renew',
        },
        google: {
          enabled: false,
          client_id: '',
          authority: 'https://accounts.google.com',
          redirect_uri: '',
          post_logout_redirect_uri: '',
          silent_redirect_uri: '',
        },
        github: {
          enabled: false,
          client_id: '',
          authority: 'https://github.com',
          redirect_uri: '',
          post_logout_redirect_uri: '',
          silent_redirect_uri: '',
        },
        microsoft: {
          enabled: false,
          client_id: '',
          authority: 'https://login.microsoftonline.com/common/v2.0',
          redirect_uri: '',
          post_logout_redirect_uri: '',
          silent_redirect_uri: '',
        },
        microsoftSocial: {
          enabled: false,
          client_id: '',
          authority: 'https://login.microsoftonline.com/consumers/v2.0',
          redirect_uri: '',
          post_logout_redirect_uri: '',
          silent_redirect_uri: '',
        },
        custom: {
          enabled: false,
          client_id: '',
          authority: '',
          redirect_uri: '',
          post_logout_redirect_uri: '',
          silent_redirect_uri: '',
          metadata: {
            authorization_endpoint: '',
            token_endpoint: '',
            userinfo_endpoint: '',
            end_session_endpoint: '',
            jwks_uri: '',
          },
        },
        apple: {
          enabled: false,
          client_id: '',
          authority: '',
          redirect_uri: 'http://localhost:5173/login',
          post_logout_redirect_uri: 'http://localhost:5173',
          silent_redirect_uri: 'http://localhost:5173/login/silent-renew',
        },
      },
      basicAuth: {
        enabled: true,
      },
      debug: false,
    };
  }

  // Helper methods to get specific config values
  async getOidcConfig() {
    const config = await this.loadConfig();
    return config.oidc;
  }

  async isBasicAuthEnabled(): Promise<boolean> {
    const config = await this.loadConfig();
    return config.basicAuth.enabled;
  }

  async isDebugEnabled(): Promise<boolean> {
    const config = await this.loadConfig();
    return config.debug;
  }
}

// Export singleton instance
export const configService = new ConfigService();

// Export type for use in other files
export type { AppConfig };
