import { AuthenticationManager, type AuthenticationState, type OidcError } from './AuthenticationManager';
import { configService } from './config';

export class AuthenticationService {
  private authManager: AuthenticationManager | null = null;
  private _state: AuthenticationState;
  private _login: string = '';
  private _password: string = '';
  private _currentOidcError: OidcError | null = null;

  constructor() {
    // Don't initialize AuthManager immediately - just create basic state
    this._state = {
      emailEntered: false,
      showPassword: false,
      matchedProvider: null,
      providerDisplayName: '',
      showRetryOption: false,
      showErrorDetails: false,
      loading: false,
      oidcCallbackLoading: false,
      password: ''
    };
  }

  // Lazy initialize the AuthManager only when needed
  private async getAuthManager(): Promise<AuthenticationManager> {
    if (!this.authManager) {
      this.authManager = new AuthenticationManager();
      // Wait for it to initialize
      await this.authManager.loadConfiguration();
    }
    return this.authManager;
  }

  // State getters
  get state(): AuthenticationState {
    return this._state;
  }

  get login(): string {
    return this._login;
  }

  set login(value: string) {
    this._login = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get currentOidcError(): OidcError | null {
    return this._currentOidcError;
  }

  // Simple computed properties that don't require AuthManager
  get shouldShowLoginForm(): boolean {
    return !this._state.oidcCallbackLoading;
  }

  get isLoginValid(): boolean {
    return !!this._login;
  }

  get isPasswordValid(): boolean {
    return !!this._password;
  }

  // These require AuthManager but are only called after email submission
  async isConfigLoaded(): Promise<boolean> {
    try {
      const authManager = await this.getAuthManager();
      return authManager.isConfigLoaded();
    } catch (error) {
      console.error('Failed to get config status:', error);
      return false;
    }
  }

  async isBasicAuthEnabled(): Promise<boolean> {
    try {
      const authManager = await this.getAuthManager();
      return authManager.isBasicAuthEnabled();
    } catch (error) {
      console.error('Failed to check basic auth:', error);
      return true; // Default to true
    }
  }

  async hasEnabledOidcProviders(): Promise<boolean> {
    try {
      const authManager = await this.getAuthManager();
      return authManager.hasEnabledOidcProviders();
    } catch (error) {
      console.error('Failed to check OIDC providers:', error);
      return false;
    }
  }

  // State management methods
  updateState(newState: Partial<AuthenticationState>): void {
    console.log('updateState called with:', newState);
    console.log('Previous state:', this._state);
    this._state = { ...this._state, ...newState };
    console.log('New state:', this._state);
  }

  resetToEmail(): void {
    if (this.authManager) {
      const newState = this.authManager.resetToEmail(this._state);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({
        emailEntered: false,
        showPassword: false,
        matchedProvider: null,
        providerDisplayName: '',
        showRetryOption: false,
        showErrorDetails: false
      });
    }
    this._password = '';
    this._currentOidcError = null;
  }

  resetToPassword(matchedProvider: string | null, providerDisplayName: string): void {
    console.log('resetToPassword called with:', { matchedProvider, providerDisplayName });
    
    if (this.authManager) {
      const newState = this.authManager.resetToPassword(this._state, matchedProvider, providerDisplayName);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({
        emailEntered: true,
        showPassword: true,
        matchedProvider,
        providerDisplayName,
        showRetryOption: false,
        showErrorDetails: false
      });
    }
    
    console.log('State after resetToPassword:', this._state);
  }

  showProviderSelection(matchedProvider: string, providerDisplayName: string): void {
    if (this.authManager) {
      const newState = this.authManager.showProviderSelection(this._state, matchedProvider, providerDisplayName);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({
        emailEntered: true,
        showPassword: false,
        matchedProvider,
        providerDisplayName,
        showRetryOption: false,
        showErrorDetails: false
      });
    }
  }

  showRetryOption(error: OidcError): void {
    if (this.authManager) {
      const newState = this.authManager.showRetryOption(this._state);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({
        showRetryOption: true,
        showErrorDetails: false,
        loading: false,
        oidcCallbackLoading: false
      });
    }
    this._currentOidcError = error;
  }

  clearRetryOption(): void {
    if (this.authManager) {
      const newState = this.authManager.clearRetryOption(this._state);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({
        showRetryOption: false,
        showErrorDetails: false
      });
    }
    this._currentOidcError = null;
  }

  toggleErrorDetails(): void {
    if (this.authManager) {
      const newState = this.authManager.toggleErrorDetails(this._state);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({
        showErrorDetails: !this._state.showErrorDetails
      });
    }
  }

  setLoading(loading: boolean): void {
    if (this.authManager) {
      const newState = this.authManager.setLoading(this._state, loading);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({ loading });
    }
  }

  setOidcCallbackLoading(loading: boolean): void {
    if (this.authManager) {
      const newState = this.authManager.setOidcCallbackLoading(this._state, loading);
      this.updateState(newState);
    } else {
      // Fallback without AuthManager
      this.updateState({ oidcCallbackLoading: loading });
    }
  }

  // Authentication logic methods - these initialize AuthManager if needed
  async determineAuthMethod(email: string) {
    const authManager = await this.getAuthManager();
    return await authManager.determineAuthMethod(email);
  }

  async findProviderByDomain(email: string) {
    const authManager = await this.getAuthManager();
    return await authManager.findProviderByDomain(email);
  }

  async getProviderDisplayName(provider: string) {
    const authManager = await this.getAuthManager();
    return await authManager.getProviderDisplayName(provider);
  }

  getProviderIcon(provider: string): string {
    if (this.authManager) {
      return this.authManager.getProviderIcon(provider);
    } else {
      // Fallback icon mapping without AuthManager
      const iconMap: Record<string, string> = {
        okta: '@/assets/okta-icon.svg',
        keycloak: '@/assets/keycloak-icon.svg',
        google: '@/assets/google-icon.svg',
        microsoft: '@/assets/microsoft_online-icon.svg',
        microsoftSocial: '@/assets/microsoft_online-icon.svg',
        github: '@/assets/github-icon.svg',
        apple: '@/assets/apple-logo.svg'
      };
      return iconMap[provider] || '@/assets/input-hide.svg';
    }
  }

  async parseOidcError(error: string, errorDescription?: string) {
    const authManager = await this.getAuthManager();
    return authManager.parseOidcError(error, errorDescription);
  }

  // OIDC state management
  clearOidcState(): void {
    if (this.authManager) {
      this.authManager.clearOidcState();
    } else {
      // Fallback without AuthManager
      const keysToRemove: string[] = [];
      for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        if (key && key.startsWith('oidc.')) {
          keysToRemove.push(key);
        }
      }
      keysToRemove.forEach(key => localStorage.removeItem(key));
    }
  }

  async createOidcState(referer: string | null, provider: string): Promise<string> {
    const authManager = await this.getAuthManager();
    return authManager.createOidcState(referer, provider);
  }

  async parseOidcState(state: string) {
    const authManager = await this.getAuthManager();
    return authManager.parseOidcState(state);
  }

  // Create initial state
  createInitialState(): AuthenticationState {
    return {
      emailEntered: false,
      showPassword: false,
      matchedProvider: null,
      providerDisplayName: '',
      showRetryOption: false,
      showErrorDetails: false,
      loading: false,
      oidcCallbackLoading: false,
      password: ''
    };
  }

  // Utility methods
  clearForm(): void {
    this._login = '';
    this._password = '';
    this.resetToEmail();
  }

  resetAfterError(): void {
    this.clearRetryOption();
    this._currentOidcError = null;
    this.clearOidcState();
  }

  // Direct config service methods for immediate use
  async checkBasicAuthEnabled(): Promise<boolean> {
    try {
      return await configService.isBasicAuthEnabled();
    } catch (error) {
      console.error('Failed to check basic auth:', error);
      return true; // Default to enabled
    }
  }

  async checkOidcEnabled(): Promise<boolean> {
    try {
      return await configService.isOidcEnabled();
    } catch (error) {
      console.error('Failed to check OIDC:', error);
      return false; // Default to disabled
    }
  }
}
