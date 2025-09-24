import { ConnectionInfo } from '@kinotic/continuum-client'

interface OidcProvider {
  enabled: boolean;
  provider: string;
  displayName: string;
  clientId: string;
  authority: string;
  redirectUri: string;
  postLogoutRedirectUri: string;
  silentRedirectUri: string;
  audience?: string;
  domains?: string[];
  roles?: string[];
  rolesClaimPath?: string;
  additionalScopes?: string;
  metadata?: Record<string, string>;
}

interface AppConfig {
  // OIDC Configuration
  enabled: boolean;
  tenantIdFieldName: string;
  oidcProviders: OidcProvider[];
  // Debug
  debug: boolean;
  frontendConfigurationPath: string;
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
    // Load base configuration locally
    const baseConfig = await this.loadLocalConfig();
    if (!baseConfig) {
      console.warn('No base configuration file found, using default configuration');
      return this.getDefaultConfig();
    }

    // Load override configuration if available (this would be from the backend)
    const overrideConfig = await this.loadOverrideConfig();
    if (overrideConfig) {
      console.log('Applying configuration override from backend');
      return this.deepMerge(baseConfig, overrideConfig);
    }

    console.log('Loaded configuration from local app-config.json');
    return baseConfig;
  }

  private async loadLocalConfig(): Promise<AppConfig | null> {
    try {
      // Load the local config file from the public folder
      const resp = await fetch('/app-config.json');
      if (resp.ok) {
        return await resp.json();
      }
    } catch (error) {
      console.warn('Failed to load local app-config.json:', error);
    }
    return null;
  }

  private async loadOverrideConfig(): Promise<Partial<AppConfig> | null> {
    try {
      // This would fetch from the backend's oidc-security-service configuration
      // TODO: This is a hack to get the override config from the backend
      // TODO: We should use the Continuum client to get the config
      const connectionInfo = this.createConnectionInfo();
      const resp = await fetch(`${connectionInfo.useSSL ? 'https' : 'http'}://${connectionInfo.host}:${connectionInfo.port}/app-config.override.json`);
      if (resp.ok) {
        return await resp.json();
      }
    } catch (error) {
      // Silently ignore if override config is not available
    }
    return null;
  }
  
  private createConnectionInfo(): ConnectionInfo {
    const connectionInfo: ConnectionInfo = {
        host: '127.0.0.1',
        port: 9090
    }
    if (window.location.hostname !== '127.0.0.1'
        && window.location.hostname !== 'localhost') {
        if (window.location.protocol.startsWith('https')) {
            connectionInfo.useSSL = true
        }
        if (window.location.port !== '') {
            connectionInfo.port = 9090
        } else {
            connectionInfo.port = null
        }
        connectionInfo.host = window.location.hostname
    }
    return connectionInfo
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
      enabled: false,
      tenantIdFieldName: 'tenantId',
      debug: false,
      oidcProviders: [],
      frontendConfigurationPath: '/app-config.override.json'
    };
  }

  // Helper methods to get specific config values

  async getEnabledOidcProviders() {
    const config = await this.loadConfig();
    return config?.oidcProviders.filter(provider => provider.enabled) || [];
  }

  async getOidcProviderByName(providerName: string): Promise<OidcProvider | undefined> {
    const config = await this.loadConfig();
    const providers = config?.oidcProviders || [];
    return providers.find(provider => provider.provider === providerName);
  }

  async isOidcEnabled(): Promise<boolean> {
    const config = await this.loadConfig();
    return config?.enabled || false;
  }

  async isBasicAuthEnabled(): Promise<boolean> {
    const config = await this.loadConfig();
    return !config?.enabled || (config?.enabled && config?.oidcProviders.length === 0);
  }

  async isDebugEnabled(): Promise<boolean> {
    const config = await this.loadConfig();
    return config?.debug;
  }

  async getTenantIdFieldName(): Promise<string> {
    const config = await this.loadConfig();
    return config?.tenantIdFieldName || 'tenantId';
  }


  // New method to find provider by email domain
  async findProviderByEmailDomain(email: string): Promise<OidcProvider | null> {
    const domain = this.extractDomainFromEmail(email);
    if (!domain) return null;

    const config = await this.loadConfig();
    return config?.oidcProviders.find(provider => 
      provider.domains && provider.domains.includes(domain)
    ) || null;
  }

  private extractDomainFromEmail(email: string): string | null {
    const atIndex = email.indexOf('@');
    if (atIndex === -1) return null;
    return email.substring(atIndex + 1).toLowerCase();
  }
}

// Export singleton instance
export const configService = new ConfigService();

// Export types for use in other files
export type { AppConfig, OidcProvider };
