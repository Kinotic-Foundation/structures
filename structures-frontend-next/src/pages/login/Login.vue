<template>
  <div class="flex w-full justify-center items-center h-screen max-w-[1440px] mx-auto">
    <!-- OIDC Callback Loading Overlay -->
    <div v-if="oidcCallbackLoading" class="fixed inset-0 bg-white bg-opacity-90 flex items-center justify-center z-50">
      <div class="text-center">
        <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mx-auto mb-4"></div>
        <h2 class="text-xl font-semibold text-gray-800 mb-2">Validating Login...</h2>
        <p class="text-gray-600">Please wait while we complete your authentication.</p>
      </div>
    </div>

    <div class="hidden md:block w-1/2 h-full bg-[url(@/assets/login-page-image.png)] bg-no-repeat bg-cover bg-bottom-left">
    </div>
    <div class="w-1/2 h-full flex flex-col justify-around items-center bg-center bg-cover">
      <div class="w-[320px] flex flex-col items-center">

        <img src="@/assets/login-page-logo.svg" class="w-[218px] h-[45px] mb-[53px]" />

        <!-- Show login form only when not in OIDC callback -->
        <div v-if="shouldShowLoginForm">
          <!-- Basic Username/Password Form - Only show if enabled -->
          <div v-if="configLoaded && basicAuthEnabled">
            <IconField class="!mb-4 !flex !items-center !relative !w-full">
              <InputText
                v-model="login"
                class="w-full max-w-[540px] h-[56px] !pl-4"
                placeholder="Username or Email"
                @focus="hideAlert"
              />
              <InputIcon class="!mt-0 -translate-y-1/2">
                <img src="@/assets/input-hide.svg" />
              </InputIcon>
            </IconField>

            <IconField class="!mb-8 !flex !items-center !relative !w-full">
              <Password
                v-model="password"
                input-class="w-full h-[56px]"
                class="!w-full max-w-[540px]"
                placeholder="Password"
                toggleMask
                :feedback="false"
                @focus="hideAlert"
                @keyup.enter="handleLogin"
              />
              <InputIcon class="!mt-0 -translate-y-1/2">
                <img src="@/assets/input-hide.svg" />
              </InputIcon>
            </IconField>

            <Button
              label="Login"
              class="rounded-[10px] max-h-[56px] !py-[18px] !w-full !text-base"
              :loading="loading"
              @click="handleLogin"
            />

            <!-- Only show OIDC section if any providers are enabled -->
            <div v-if="configLoaded && enabledProviders.size > 0" class="flex justify-center items-center w-full mt-10 mb-8">
              <p class="border border-gray-300 w-full"></p>
              <span class="text-gray-300 mx-3">OR</span>
              <p class="border border-gray-300 w-full"></p>
            </div>
          </div>

          <!-- Show OIDC section without "OR" separator if basic auth is disabled -->
          <div v-else-if="configLoaded && enabledProviders.size > 0" class="w-full">
            <!-- OIDC providers will be shown below -->
          </div>

          <!-- Okta Login Button -->
          <div v-if="configLoaded && enabledProviders.has('okta')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleOktaLogin">
            <img src="@/assets/okta-icon.svg" class="mr-6" />
            <span v-if="!loading">Continue with Okta</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- Google Login Button -->
          <div v-if="configLoaded && enabledProviders.has('google')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleGoogleLogin">
            <img src="@/assets/google-icon.svg" class="mr-6" />
            <span v-if="!loading">Continue with Google</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- Microsoft Login Button (Enterprise) -->
          <div v-if="configLoaded && enabledProviders.has('microsoft')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleMicrosoftLogin">
            <img src="@/assets/microsoft_online-icon.svg" class="mr-6" />
            <span v-if="!loading">Continue with Microsoft (Work)</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- Microsoft Social Login Button (Personal) -->
          <div v-if="configLoaded && enabledProviders.has('microsoftSocial')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleMicrosoftSocialLogin">
            <img src="@/assets/microsoft_online-icon.svg" class="mr-6" />
            <span v-if="!loading">Continue with Microsoft (Personal)</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- Keycloak Login Button -->
          <div v-if="configLoaded && enabledProviders.has('keycloak')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleKeycloakLogin">
            <img src="@/assets/keycloak-icon.svg" class="mr-6" />
            <span v-if="!loading">Continue with Keycloak</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- GitHub Login Button -->
          <div v-if="configLoaded && enabledProviders.has('github')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleGithubLogin">
            <img src="@/assets/github-icon.svg" class="mr-6" />
            <span v-if="!loading">Continue with GitHub</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- Apple Login Button -->
          <div v-if="configLoaded && enabledProviders.has('apple')" 
               class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
               :class="{ 'opacity-50 cursor-not-allowed': loading }"
               @click="handleAppleLogin">
            <img src="@/assets/apple-logo.svg" class="mr-6" />
            <span v-if="!loading">Continue with Apple</span>
            <span v-else class="flex items-center">
              <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
              Connecting...
            </span>
          </div>

          <!-- Only show forgot password if basic auth is enabled -->
          <div v-if="configLoaded && basicAuthEnabled" class="text-[#212121] cursor-pointer">
            Forgot Password
          </div>
        </div>
      </div>
      <div class="flex gap-2">
          <a href="#" class="text-[#0568FD] border-b-1">
            Terms of use
          </a>
          <span clas>
            |
          </span>
          <span class="text-[#0568FD] border-b-1">
            Privacy policy
          </span>
        </div>
    </div>

    <Toast />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-facing-decorator"
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'
import { createUserManager, type OidcProvider, getProviderConfig } from './OidcConfiguration'
import { configService } from '@/util/config'

import { type IUserState } from "@/states/IUserState"
import { CONTINUUM_UI } from "@/IContinuumUI"
import { StructuresStates } from "@/states/index"

@Component({
  components: {
    InputText,
    Password,
    Button,
    Toast,
  }
})
export default class Login extends Vue {
  login: string = ''
  password: string = ''
  loading: boolean = false
  oidcCallbackLoading: boolean = false
  valid: boolean = true
  configLoaded: boolean = false
  basicAuthEnabled: boolean = false
  enabledProviders: Set<string> = new Set()

  toast = useToast()

  private userState: IUserState = StructuresStates.getUserState()

  private loginRules = [(v: string) => !!v || 'Login required']
  private passwordRules = [(v: string) => !!v || 'Password required']

  async mounted() {
    await this.loadConfiguration();
    
    // Check if we're returning from an OIDC login with an error
    if (this.$route.query.error) {
      this.loading = false;
      console.error('OIDC login error detected:', this.$route.query);
      
      const error = this.$route.query.error as string;
      const errorDescription = this.$route.query.error_description as string;
      
      // Decode URL-encoded error description
      const decodedErrorDescription = errorDescription ? decodeURIComponent(errorDescription) : '';
      
      console.error('OIDC Error:', error);
      console.error('OIDC Error Description:', decodedErrorDescription);
      
      // Display user-friendly error message
      let userMessage = 'OIDC login failed';
      
      if (error === 'invalid_request') {
        if (decodedErrorDescription.includes('AADSTS50194')) {
          userMessage = 'Microsoft Azure configuration error: Application is not configured as multi-tenant. Please contact your administrator.';
        } else if (decodedErrorDescription.includes('redirect_uri_mismatch')) {
          userMessage = 'OIDC configuration error: Redirect URI mismatch. Please contact your administrator.';
        } else {
          userMessage = `OIDC configuration error: ${decodedErrorDescription}`;
        }
      } else if (error === 'access_denied') {
        userMessage = 'Access denied by OIDC provider. Please try again or contact your administrator.';
      } else if (error === 'unauthorized_client') {
        userMessage = 'OIDC client not authorized. Please contact your administrator.';
      } else if (error === 'unsupported_response_type') {
        userMessage = 'OIDC response type not supported. Please contact your administrator.';
      } else if (error === 'server_error') {
        userMessage = 'OIDC server error. Please try again later.';
      } else if (error === 'temporarily_unavailable') {
        userMessage = 'OIDC service temporarily unavailable. Please try again later.';
      } else if (decodedErrorDescription) {
        userMessage = `OIDC login failed: ${decodedErrorDescription}`;
      }
      
      this.displayAlert(userMessage);
      return;
    }
    
    // Check if we're returning from an OIDC login
    if (this.$route.query.code && this.$route.query.state) {
      this.oidcCallbackLoading = true; // Show loading overlay
      try {
        console.log('OIDC callback detected, processing...');
        console.log('Query parameters:', this.$route.query);
        
        // First, we need to determine which provider was used
        // We'll try to find the provider by checking localStorage for stored state
        let provider: OidcProvider = 'okta'; // default fallback
        let referer: string | null = null;
        
        // Check localStorage for stored state to determine provider
        const stateKey = `oidc.${this.$route.query.state}`;
        const storedState = localStorage.getItem(stateKey);
        
        if (storedState) {
          try {
            // Parse the stored state to get our custom data
            const sessionState = JSON.parse(storedState);
            const stateObj = JSON.parse(atob(sessionState.data));
            console.log(`Found stored state : ${JSON.stringify(stateObj)}`);
            provider = stateObj.provider || 'okta';
            referer = stateObj.referer || null;
            console.log('Parsed stored state:', { provider, referer });
          } catch (parseError) {
            console.warn(`Failed to parse stored state for ${storedState}:`, parseError);
          }
        }
        
        console.log(`Using provider: ${provider}`);
        const userManager = await createUserManager(provider);
        
        // Complete the OIDC login
        console.log('Starting signinRedirectCallback...');
        const user = await userManager.signinRedirectCallback();
        console.log('OIDC callback completed successfully');
        console.log('User state from callback:', user.state);
        
        // The library automatically validates the state parameter
        // If validation fails, it throws an error before reaching this point
        
        // Store the user info in your state management
        await this.userState.handleOidcLogin(user);
        
        // Redirect to the original destination or default
        const redirectPath = referer || '/applications';
        console.log(`Navigating to: ${redirectPath}`);
        await CONTINUUM_UI.navigate(redirectPath);
      } catch (error: unknown) {
        console.error('OIDC callback error:', error);
        if (error instanceof Error) {
          this.displayAlert(`OIDC callback failed: ${error.message}`);
        } else {
          this.displayAlert('OIDC callback failed');
        }
      } finally {
        this.oidcCallbackLoading = false; // Hide loading overlay
        this.loading = false;
      }
    }
  }

  private async loadConfiguration() {
    try {
      // Load basic auth configuration
      this.basicAuthEnabled = await configService.isBasicAuthEnabled();
      
      // Load OIDC provider configurations
      const providers: OidcProvider[] = ['okta', 'keycloak', 'google', 'microsoft', 'microsoftSocial', 'github', 'custom', 'apple'];
      for (const provider of providers) {
        try {
          const config = await getProviderConfig(provider);
          if (config.enabled) {
            this.enabledProviders.add(provider);
          }
        } catch (error) {
          console.warn(`Failed to load configuration for provider ${provider}:`, error);
        }
      }
      
      this.configLoaded = true;
    } catch (error) {
      console.error('Failed to load configuration:', error);
      // Set defaults if configuration loading fails
      this.basicAuthEnabled = true;
      this.configLoaded = true;
    }
  }

  // Check if basic username/password authentication is enabled
  async isBasicAuthEnabled(): Promise<boolean> {
    return await configService.isBasicAuthEnabled();
  }

  get loginValid(): boolean {
    return this.loginRules.every(rule => rule(this.login) === true)
  }

  get passwordValid(): boolean {
    return this.passwordRules.every(rule => rule(this.password) === true)
  }

  get referer(): string | null {
    const r = this.$route.query.referer;
    return typeof r === 'string' ? r : null;
  }

  // Check if a specific provider is enabled
  async isProviderEnabled(provider: OidcProvider): Promise<boolean> {
    try {
      const config = await getProviderConfig(provider);
      return config.enabled;
    } catch (error) {
      console.warn(`Provider ${provider} not found in configuration`);
      return false;
    }
  }

  // Check if any OIDC providers are enabled
  async hasEnabledOidcProviders(): Promise<boolean> {
    const providers: OidcProvider[] = ['okta', 'keycloak', 'google', 'microsoft', 'microsoftSocial', 'github', 'custom'];
    const enabledProviders = await Promise.all(
      providers.map(provider => this.isProviderEnabled(provider))
    );
    return enabledProviders.some(enabled => enabled);
  }

  // Check if we're in an OIDC callback (have code and state parameters)
  get isOidcCallback(): boolean {
    return !!(this.$route.query.code && this.$route.query.state);
  }

  // Check if we should show the login form (not during OIDC callback)
  get shouldShowLoginForm(): boolean {
    return !this.isOidcCallback;
  }

  async handleLogin() {
    if (!this.loginValid || !this.passwordValid) {
      this.displayAlert('Login and Password are required');
      return;
    }

    this.loading = true;
    try {
      console.log('Starting authentication...');
      await this.userState.authenticate(this.login, this.password);
      console.log('Authentication successful, navigating...');

      if (this.referer) {
        console.log('Navigating to referer:', this.referer);
        await CONTINUUM_UI.navigate(this.referer);
      } else {
        // Use the redirected path if available, otherwise go to applications
        const redirectPath = this.$route.redirectedFrom?.fullPath;
        if (redirectPath && redirectPath !== "/") {
          console.log('Navigating to redirected path:', redirectPath);
          await CONTINUUM_UI.navigate(redirectPath);
        } else {
          console.log('Navigating to applications');
          await CONTINUUM_UI.navigate('/applications');
        }
      }
      console.log('Navigation completed');
    } catch (error: unknown) {
      console.error('Authentication error:', error);
      if (error instanceof Error) {
        this.displayAlert(error.message)
      } else if (typeof error === 'string') {
        this.displayAlert(error)
      } else {
        this.displayAlert('Unknown login error')
      }
    } finally {
      this.loading = false;
    }
  }

  private hideAlert() {
    this.toast.removeAllGroups();
  }

  private displayAlert(text: string) {
    this.toast.add({
      severity: 'error',
      summary: 'Error',
      detail: text,
      life: 30000
    });
  }

  private async handleOidcLogin(provider: OidcProvider) {
    // Prevent multiple clicks
    if (this.loading) {
      console.log('OIDC login already in progress, ignoring click');
      return;
    }
    
    this.loading = true;
    try {
      console.log(`Starting OIDC login for provider: ${provider}`);
      
      // Validate provider configuration
      const config = await getProviderConfig(provider);
      if (!config.enabled) {
        throw new Error(`Provider ${provider} is not enabled`);
      }
      
      if (!config.client_id) {
        throw new Error(`Client ID not configured for ${provider}`);
      }
      
      if (!config.authority) {
        throw new Error(`Authority URL not configured for ${provider}`);
      }
      
      const userManager = await createUserManager(provider);
      
      console.log('UserManager created, starting signinRedirect...');
      console.log('Provider configuration:', {
        authority: config.authority,
        client_id: config.client_id,
        redirect_uri: config.redirect_uri
      });
      
      // Create state object that includes both referer and provider
      const stateObj = {
        referer: this.referer,
        provider: provider
      };
      
      // Base64 encode the state object to avoid URL encoding issues
      const stateJson = JSON.stringify(stateObj);
      const stateBase64 = btoa(stateJson);
      console.log('State object:', stateObj);
      console.log('Base64 encoded state:', stateBase64);
      
      // Start the OIDC login flow
      await userManager.signinRedirect({
        state: stateBase64
      });
      console.log('signinRedirect completed successfully');
      
      // Debug: Check what's stored in localStorage
      const storedState = localStorage.getItem(`oidc.state.${userManager.settings.client_id}`);
      console.log('Stored state in localStorage:', storedState);
    } catch (error: unknown) {
      console.error('OIDC login error:', error);
      this.loading = false;
      
      let errorMessage = 'OIDC login failed';
      
      if (error instanceof Error) {
        if (error.message.includes('NetworkError') || error.message.includes('fetch')) {
          errorMessage = `Network error: Unable to connect to ${provider}. Please check your internet connection and try again.`;
        } else if (error.message.includes('redirect_uri_mismatch')) {
          errorMessage = `Configuration error: Redirect URI mismatch for ${provider}. Please contact your administrator.`;
        } else if (error.message.includes('client_id') || error.message.includes('authority')) {
          errorMessage = `Configuration error: ${error.message}. Please contact your administrator.`;
        } else {
          errorMessage = `OIDC login failed: ${error.message}`;
        }
      } else if (typeof error === 'string') {
        errorMessage = `OIDC login failed: ${error}`;
      }
      
      this.displayAlert(errorMessage);
    }
  }

  private async handleOktaLogin() {
    await this.handleOidcLogin('okta');
  }

  private async handleGoogleLogin() {
    await this.handleOidcLogin('google');
  }

  private async handleMicrosoftLogin() {
    await this.handleOidcLogin('microsoft');
  }

  private async handleMicrosoftSocialLogin() {
    await this.handleOidcLogin('microsoftSocial');
  }

  private async handleKeycloakLogin() {
    console.log('Starting Keycloak login...');
    try {
      await this.handleOidcLogin('keycloak');
    } catch (error) {
      console.error('Keycloak login error:', error);
      throw error;
    }
  }

  private async handleGithubLogin() {
    await this.handleOidcLogin('github');
  }

  private async handleAppleLogin() {
    await this.handleOidcLogin('apple');
  }


}
</script>