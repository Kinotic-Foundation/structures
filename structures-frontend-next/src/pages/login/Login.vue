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
          <!-- Step 1: Username/Email Input -->
          <div v-if="!emailEntered && configLoaded" class="w-full">
            <h2 class="text-xl font-semibold text-gray-800 mb-6 text-center">Enter your email address</h2>
            
            <IconField class="!mb-6 !flex !items-center !relative !w-full">
              <InputText
                ref="emailInput"
                v-model="login"
                class="w-full max-w-[700px] h-[56px] !pl-4"
                placeholder="Username or Email"
                @focus="hideAlert"
                @keyup.enter="handleEmailSubmit"
              />
              <InputIcon class="!mt-0 -translate-y-1/2">
                <img src="@/assets/input-hide.svg" />
              </InputIcon>
            </IconField>

            <Button
              label="Continue"
              class="rounded-[10px] max-h-[56px] !py-[18px] !w-full !text-base"
              :loading="loading"
              @click="handleEmailSubmit"
            />
          </div>

          <!-- Step 2: Provider Selection or Password Input -->
          <div v-if="emailEntered && configLoaded" class="w-full">
            <!-- Show provider selection if domain matches -->
            <div v-if="matchedProvider && !showPassword" class="w-full">
              <h2 class="text-xl font-semibold text-gray-800 mb-6 text-center">
                Continue with {{ providerDisplayName || matchedProvider }}
              </h2>
              
              <div class="flex border border-[#B8BCBD] w-full p-4 mb-6 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
                   :class="{ 'opacity-50 cursor-not-allowed': loading }"
                   @click="handleOidcLogin(matchedProvider)">
                <img :src="getProviderIcon(matchedProvider)" class="mr-6 w-6 h-6" />
                <span v-if="!loading">Continue with {{ getProviderDisplayName(matchedProvider) }}</span>
                <span v-else class="flex items-center">
                  <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
                  Connecting...
                </span>
              </div>

              <div class="text-center">
                <button 
                  @click="showPassword = true" 
                  class="text-[#0568FD] hover:underline cursor-pointer">
                  Or sign in with password
                </button>
              </div>
            </div>

            <!-- Show password input if no provider match or user chooses password -->
            <div v-if="(!matchedProvider || showPassword) && basicAuthEnabled" class="w-full">
              <h2 class="text-xl font-semibold text-gray-800 mb-6 text-center">
                {{ matchedProvider ? 'Sign in with password' : 'Enter your password' }}
              </h2>
              
              <div class="mb-4">
                <div class="text-sm text-gray-600 mb-2">Email: {{ login }}</div>
                <button 
                  @click="resetToEmail" 
                  class="text-[#0568FD] hover:underline cursor-pointer text-sm">
                  Change email
                </button>
              </div>

              <IconField class="!mb-6 !flex !items-center !relative !w-full">
                <Password
                  ref="passwordInput"
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
                label="Sign In"
                class="rounded-[10px] max-h-[56px] !py-[18px] !w-full !text-base"
                :loading="loading"
                @click="handleLogin"
              />

            </div>

            <!-- Show error if no provider match and basic auth disabled -->
            <div v-if="!matchedProvider && !basicAuthEnabled" class="w-full text-center">
              <div class="text-red-600 mb-4">
                No authentication method found for this email domain.
              </div>
              <button 
                @click="resetToEmail" 
                class="text-[#0568FD] hover:underline cursor-pointer">
                Try a different email
              </button>
            </div>
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
import { Component, Vue } from 'vue-facing-decorator';
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'
import { createUserManager } from './OidcConfiguration';
import { configService } from '@/util/config';

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
  emailEntered: boolean = false
  showPassword: boolean = false
  matchedProvider: string | null = null
  providerDisplayName: string = ''

  toast = useToast()

  private userState: IUserState = StructuresStates.getUserState()

  private loginRules = [(v: string) => !!v || 'Login required']
  private passwordRules = [(v: string) => !!v || 'Password required']

  async mounted() {
    await this.loadConfiguration();

        // Focus the email input initially
    this.$nextTick(() => {
      this.focusEmailInput();
    });
    
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
        
        // First, we need to determine which provider was used
        // We'll try to find the provider by checking localStorage for stored state
        let provider: string = 'keycloak'; // default fallback
        let referer: string | null = null;
        
        // Check localStorage for stored state to determine provider
        const stateKey = `oidc.${this.$route.query.state}`;
        const storedState = localStorage.getItem(stateKey);
        
        if (storedState) {
          try {
            // Parse the stored state to get our custom data
            const sessionState = JSON.parse(storedState);
            const stateObj = JSON.parse(atob(sessionState.data));
            provider = stateObj.provider || 'keycloak';
            referer = stateObj.referer || null;
          } catch (parseError) {
            console.warn(`Failed to parse stored state for ${storedState}:`, parseError);
          }
        }
        
        const userManager = await createUserManager(provider);
        
        // Complete the OIDC login
        const user = await userManager.signinRedirectCallback();
        
        // The library automatically validates the state parameter
        // If validation fails, it throws an error before reaching this point
        
        // Store the user info in your state management
        await this.userState.handleOidcLogin(user);
        
        // Redirect to the original destination or default
        const redirectPath = referer || '/applications';
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

  private focusEmailInput() {
    if (this.$refs.emailInput) {
      (this.$refs.emailInput as any).$el?.focus?.() || 
      (this.$refs.emailInput as any).focus?.();
    }
  }

  private focusPasswordInput() {
    if (this.$refs.passwordInput) {
      // For PrimeVue Password component, we need to focus the inner input
      const passwordElement = this.$refs.passwordInput as any;
      const innerInput = passwordElement.$el?.querySelector('input[type="password"]') ||
                        passwordElement.$el?.querySelector('input');
      if (innerInput) {
        innerInput.focus();
      }
    }
  }

  private async loadConfiguration() {
    try {
      
      // Load basic auth configuration
      this.basicAuthEnabled = await configService.isBasicAuthEnabled();
      
      // Load OIDC provider configurations dynamically
      const enabledProviders = await configService.getEnabledOidcProviders();
      
      for (const provider of enabledProviders) {
        this.enabledProviders.add(provider.provider);
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
  // async isBasicAuthEnabled(): Promise<boolean> {
  //   return await configService.isBasicAuthEnabled();
  // }

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
  async isProviderEnabled(providerName: string): Promise<boolean> {
    try {
      const provider = await configService.getOidcProviderByName(providerName);
      return provider?.enabled ?? false;
    } catch (error) {
      console.warn(`Provider ${providerName} not found in configuration`);
      return false;
    }
  }

  // Check if any OIDC providers are enabled
  async hasEnabledOidcProviders(): Promise<boolean> {
    const enabledProviders = await configService.getEnabledOidcProviders();
    return enabledProviders.length > 0;
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
      await this.userState.authenticate(this.login, this.password);

      if (this.referer) {
        await CONTINUUM_UI.navigate(this.referer);
      } else {
        // Use the redirected path if available, otherwise go to applications
        const redirectPath = this.$route.redirectedFrom?.fullPath;
        if (redirectPath && redirectPath !== "/") {
          await CONTINUUM_UI.navigate(redirectPath);
        } else {
          await CONTINUUM_UI.navigate('/applications');
        }
      }
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

  private async handleEmailSubmit() {
    if (!this.loginValid) {
      this.displayAlert('Please enter a valid email address');
      return;
    }

    this.loading = true;
    try {
      // Check if OIDC should be used based on the new workflow
      const shouldUseOidc = await configService.isOidcEnabled();
      
      if (shouldUseOidc) {
        // Try to find a provider that matches the email domain
        const matchedProvider = await this.findProviderByDomain(this.login);
        
        if (matchedProvider) {
          console.log('Domain matches an OIDC provider, redirecting immediately');
          // Domain matches an OIDC provider, redirect immediately
          this.loading = false;
          await this.handleOidcLogin(matchedProvider);
          return;
        } else {
          // No provider matches the domain, show password input for basic auth fallback
          console.log('No OIDC provider matches email domain, falling back to basic auth');
          this.emailEntered = true;
          this.showPassword = true;
          this.loading = false;
          // Focus password input when it becomes visible
          this.$nextTick(() => {
            this.focusPasswordInput();
          });
          return;
        }
      } else {
        // OIDC is not enabled or no providers configured, use basic auth
        console.log('OIDC not enabled or no providers configured, using basic auth');
        this.emailEntered = true;
        this.showPassword = true;
        this.loading = false;
        // Focus password input when it becomes visible
        this.$nextTick(() => {
            this.focusPasswordInput();
          });
        return;
      }
    } catch (error) {
      console.error('Error determining authentication method:', error);
      // Fallback to basic auth on error
      this.emailEntered = true;
      this.showPassword = true;
      this.loading = false;
      // Focus password input when it becomes visible
      this.$nextTick(() => {
        this.focusPasswordInput();
      });
    }
  }

  private async findProviderByDomain(domain: string): Promise<string | null> {    
    // Check if any provider has this domain in their domains array
    try {
      const provider = await configService.findProviderByEmailDomain(domain);
      return provider?.provider || null;
    } catch (error) {
      console.warn(`Failed to find OIDC config`, error);
    }
    
    return null;
  }

  private async getProviderDisplayName(provider: string): Promise<string> {
    try {
      const providerConfig = await configService.getOidcProviderByName(provider);
      return providerConfig?.displayName || provider;
    } catch (error) {
      console.warn(`Failed to get display name for provider ${provider}:`, error);
      return provider;
    }
  }

  private getProviderIcon(provider: string): string {
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

  private resetToEmail() {
    this.emailEntered = false;
    this.showPassword = false;
    this.matchedProvider = null;
    this.providerDisplayName = '';
    this.password = '';
        
    // Focus email input when resetting
    this.$nextTick(() => {
      this.focusEmailInput();
    });
  }

  private async handleOidcLogin(provider: string) {
    // Prevent multiple clicks
    if (this.loading) {
      return;
    }

    this.loading = true;
    try {
      
      // Validate provider configuration
      const providerConfig = await configService.getOidcProviderByName(provider);
      if (!providerConfig?.enabled) {
        throw new Error(`Provider ${provider} is not enabled`);
      }
      
      if (!providerConfig.clientId) {
        throw new Error(`Client ID not configured for ${provider}`);
      }
      
      if (!providerConfig.authority) {
        throw new Error(`Authority URL not configured for ${provider}`);
      }
      
      console.log('Provider config validated:', {
        provider: provider,
        enabled: providerConfig.enabled,
        clientId: providerConfig.clientId,
        authority: providerConfig.authority,
        redirectUri: providerConfig.redirectUri
      });
      
      // Create UserManager for the provider
      const userManager = await createUserManager(provider);
      
      console.log('UserManager settings:', {
        authority: userManager.settings.authority,
        client_id: userManager.settings.client_id,
        redirect_uri: userManager.settings.redirect_uri,
        response_type: userManager.settings.response_type,
        scope: userManager.settings.scope
      });
      
      // Create state object with provider and referer information
      const stateObj = {
        referer: this.referer,
        provider: provider
      };
      
      // Encode state object as base64
      const state = btoa(JSON.stringify(stateObj));
      // Start OIDC login with login_hint to pre-fill email
      const signinOptions: any = { state };
      
      // Add login_hint if we have an email to pre-fill
      if (this.login) {
        signinOptions.login_hint = this.login;
        
        // Some providers also support domain_hint for better UX
        const emailDomain = this.login.split('@')[1];
        if (emailDomain) {
          signinOptions.domain_hint = emailDomain;
        }
      }
      
      await userManager.signinRedirect(signinOptions);
      
    } catch (error) {
      console.error('OIDC login failed:', error);
      console.error('Error details:', {
        name: error instanceof Error ? error.name : 'Unknown',
        message: error instanceof Error ? error.message : String(error),
        stack: error instanceof Error ? error.stack : undefined
      });
      this.displayAlert(`OIDC login failed: ${error instanceof Error ? error.message : 'Unknown error'}`);
      this.loading = false;
    }
  }

}
</script>