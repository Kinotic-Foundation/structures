<template>
  <div class="flex w-full justify-center items-center h-screen mx-auto">
    <div v-if="isInitialized && state?.oidcCallbackLoading" class="fixed inset-0 bg-white bg-opacity-90 flex items-center justify-center z-50">
      <div class="text-center">
        <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mx-auto mb-4"></div>
        <h2 class="text-xl font-semibold text-gray-800 mb-2">Validating Login...</h2>
        <p class="text-gray-600">Please wait while we complete your authentication.</p>
      </div>
    </div>
         <div class="relative w-1/2 h-full bg-gradient-to-br from-[#0A0A0B] from-0% via-[#0A0A0B] via-70% to-[#293A9E] to-100% hidden md:block">
       <img src="@/assets/login-page-symbol-new.svg" class="absolute right-0 bottom-0 h-screen"/>
       <img src="@/assets/login-page-logo-new.svg" class="absolute left-[75px] bottom-[56px] max-w-[300px] h-[63px] w-auto xl:max-w-[300px] xl:h-[63px] lg:max-w-[250px] lg:h-[52px] md:max-w-[200px] md:h-[42px] sm:max-w-[150px] sm:h-[32px]"/>
     </div>
    <div class="w-1/2 h-full flex flex-col justify-center items-center bg-center bg-cover relative">
      <div v-if="showSuccessMessage" class="w-full h-full flex flex-col justify-center items-center text-center">
        <div class="mb-6">
          <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
            </svg>
          </div>
          <h2 class="text-4xl font-semibold text-surface-900 mb-4">Authentication successful</h2>
          <p class="text-surface-900 text-base font-normal">You can close this tab and return to your command line</p>
        </div>
      </div>

      <div v-if="!showSuccessMessage" class="w-[320px] flex flex-col items-center">
        <img src="@/assets/login-page-logo.svg" class="w-[218px] h-[45px] mb-[53px]" />

        <div v-if="isInitialized && shouldShowLoginForm">
          <div v-if="state?.showRetryOption" class="w-full mb-6 p-4 border border-red-200 bg-red-50 rounded-lg">
            <div class="text-center">
              <div class="flex items-center justify-center mb-3">
                <svg class="w-5 h-5 text-red-600 mr-2" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                <p class="text-red-700 font-medium">OIDC login encountered an error</p>
              </div>
              <p class="text-red-600 mb-4 text-sm">You can try again or use an alternative login method:</p>
              
              <div class="mb-4">
                <button 
                  @click="toggleErrorDetails"
                  class="text-red-600 hover:text-red-800 text-sm underline flex items-center mx-auto"
                >
                  <span>{{ state?.showErrorDetails ? 'Hide' : 'Show' }} error details</span>
                  <svg 
                    class="w-4 h-4 ml-1 transition-transform" 
                    :class="{ 'rotate-180': state?.showErrorDetails }"
                    fill="currentColor" 
                    viewBox="0 0 20 20"
                  >
                    <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd"></path>
                  </svg>
                </button>
                
                <div v-if="state?.showErrorDetails && currentOidcError" class="mt-3 p-3 bg-red-100 border border-red-200 rounded text-left">
                  <div class="text-xs text-red-700 font-mono">
                    <div><strong>Error:</strong> {{ currentOidcError.error }}</div>
                    <div v-if="currentOidcError.description" class="mt-1">
                      <strong>Description:</strong> {{ currentOidcError.description }}
                    </div>
                  </div>
                </div>
              </div>
              
              <div class="flex flex-wrap gap-2 justify-center">
                <Button
                  label="Try OIDC Again"
                  class="rounded-[10px] max-h-[40px] !py-2 !px-4 !text-sm bg-blue-600 hover:bg-blue-700"
                  @click="retryOidcLogin"
                />
                <Button
                  label="Use Password Instead"
                  class="rounded-[10px] max-h-[40px] !py-2 !px-4 !text-sm bg-gray-600 hover:bg-gray-700"
                  @click="usePasswordInstead"
                />
                <Button
                  label="Back to Email"
                  class="rounded-[10px] max-h-[40px] !py-2 !px-4 !text-sm bg-gray-500 hover:bg-gray-600"
                  @click="goBackToEmail"
                />
                <Button
                  label="Clear Error"
                  class="rounded-[10px] max-h-[40px] !py-2 !px-4 !text-sm bg-red-600 hover:bg-blue-700"
                  @click="clearErrorAndReset"
                />
              </div>
            </div>
          </div>
          <div v-if="!state?.emailEntered" class="w-full">
            <IconField class="!mb-6 !flex !items-center !relative !w-full">
              <InputText
                ref="emailInput"
                v-model="login"
                class="w-[320px] max-w-[700px] h-[56px] !pl-4"
                placeholder="Username or Email"
                @focus="hideAlert"
                @keyup.enter="handleEmailSubmit"
              />
            </IconField>

            <Button
              label="Continue"
              class="rounded-[10px] max-h-[56px] !py-[18px] !w-full !text-base"
              :loading="state?.loading || false"
              @click="handleEmailSubmit"
            />
          </div>
          <div v-if="state?.emailEntered" class="w-full">
            <div v-if="state?.matchedProvider && !state?.showPassword" class="w-full">
              <h2 class="text-xl font-semibold text-gray-800 mb-6 text-center">
                Continue with {{ state?.providerDisplayName || state?.matchedProvider }}
              </h2>
              
              <div class="flex border border-[#B8BCBD] w-full p-4 mb-6 rounded-[5px] text-black/50 cursor-pointer hover:bg-gray-50 transition-colors"
                   :class="{ 'opacity-50 cursor-not-allowed': state?.loading }"
                   @click="handleOidcLogin(state?.matchedProvider)">
                <img :src="getProviderIcon(state?.matchedProvider)" class="mr-6 w-6 h-6" />
                <span v-if="!state?.loading">Continue with {{ state?.providerDisplayName || state?.matchedProvider }}</span>
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
            <div v-if="state?.showPassword" class="w-full">
              <IconField class="!mb-6 !flex !items-center !relative !w-full">
                <InputText
                  :value="login"
                  disabled
                  class="w-[320px] max-w-[700px] h-[56px] !pl-4 !bg-gray-100 !text-gray-600"
                  placeholder="Username or Email"
                />
              </IconField>

              <IconField class="!mb-6 !flex !items-center !relative !w-full">
                <Password
                  ref="passwordInput"
                  v-model="password"
                  input-class="w-full h-[56px]"
                  class="!w-full max-w-[700px]"
                  placeholder="Password"
                  toggleMask
                  :feedback="false"
                  @focus="hideAlert"
                  @keyup.enter="handleLogin"
                />
              </IconField>
              <Button
                label="Sign In"
                class="rounded-[10px] max-h-[56px] !py-[18px] !w-full !text-base !mb-5"
                :loading="state?.loading || false"
                @click="handleLogin"
              />
              <Button
                label="< Back"
                text
                class="!p-3 !w-full !text-base"
                style="color: #3651ED; background: transparent;"
                @click="resetToEmail"
              />

            </div>

            <div v-if="!state?.matchedProvider && !state?.showPassword && state?.emailEntered" class="w-full text-center">
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

        <div v-if="!isInitialized" class="w-full text-center">
          <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p class="text-gray-600">Initializing...</p>
        </div>
      </div>
      <div class="flex gap-2 absolute bottom-8 left-0 right-0 justify-center"> 
          <a href="#" class="text-[#0568FD] border-b-1">
            Terms of use
          </a>
          <span class="text-gray-400">
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
import { AuthenticationService } from '@/util/AuthenticationService';

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
  private auth = new AuthenticationService();
  
  get login() { return this.auth.login; }
  set login(value: string) { this.auth.login = value; }
  
  get password() { return this.auth.password; }
  set password(value: string) { this.auth.password = value; }

  get state() { return this.auth.state; }
  get shouldShowLoginForm() { return this.auth.shouldShowLoginForm; }
  get isLoginValid() { return this.auth.isLoginValid; }
  get isPasswordValid() { return this.auth.isPasswordValid; }
  get currentOidcError() { return this.auth.currentOidcError; }

  get showPassword() { return this.state.showPassword; }
  set showPassword(value: boolean) { 
    this.auth.updateState({ showPassword: value }); 
  }

  get isInitialized() { 
    return true;
  }

  private _isConfigLoaded: boolean = false;
  private _isBasicAuthEnabled: boolean = true;
  
  private showSuccessMessage: boolean = false;
  private countdown: number = 2;
  private countdownInterval: NodeJS.Timeout | null = null;

  get isConfigLoaded() { return this._isConfigLoaded; }
  get isBasicAuthEnabled() { return this._isBasicAuthEnabled; }

  get debugInfo() {
    return {
      emailEntered: this.state?.emailEntered,
      showPassword: this.state?.showPassword,
      matchedProvider: this.state?.matchedProvider,
      isBasicAuthEnabled: this._isBasicAuthEnabled,
      isConfigLoaded: this._isConfigLoaded
    };
  }

  toast = useToast()
  private userState: IUserState = StructuresStates.getUserState()

  async mounted() {
    this.$nextTick(() => {
      this.focusEmailInput();
    });
    
    this.loadBasicConfig();
    
    if (this.$route.query.error) {
      this.handleOidcError();
      return;
    }
    
    if (this.$route.query.code && this.$route.query.state) {
      this.handleOidcCallback();
    }
  }

  private async loadBasicConfig() {
    console.log('Loading basic config...');
    try {
      this._isBasicAuthEnabled = await this.auth.checkBasicAuthEnabled();
      this._isConfigLoaded = true;
      console.log('Basic config loaded successfully:', { 
        isBasicAuthEnabled: this._isBasicAuthEnabled, 
        isConfigLoaded: this._isConfigLoaded 
      });
    } catch (error) {
      console.error('Failed to load basic config:', error);
      this._isBasicAuthEnabled = true;
      this._isConfigLoaded = false;
      console.log('Using default config:', { 
        isBasicAuthEnabled: this._isBasicAuthEnabled, 
        isConfigLoaded: this._isConfigLoaded 
      });
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
      const passwordElement = this.$refs.passwordInput as any;
      const innerInput = passwordElement.$el?.querySelector('input[type="password"]') ||
                        passwordElement.$el?.querySelector('input');
      if (innerInput) {
        innerInput.focus();
      }
    }
  }

  get referer(): string | null {
    const r = this.$route.query.referer;
    return typeof r === 'string' ? r : null;
  }

  private async handleOidcError() {
    this.auth.setLoading(false);
    this.auth.setOidcCallbackLoading(false);
    
    const error = this.$route.query.error as string;
    const errorDescription = this.$route.query.error_description as string;
    
    const { userMessage, isRetryable, error: oidcError } = await this.auth.parseOidcError(error, errorDescription);
    
    this.displayAlert(userMessage);
    
    if (isRetryable) {
      this.auth.showRetryOption(oidcError);
      this.auth.updateState({ 
        emailEntered: true, 
        showPassword: false 
      });
    } else {
      this.auth.resetToEmail();
    }
  }

  private async handleOidcCallback() {
    this.auth.setOidcCallbackLoading(true);
    
    try {
      const state = this.$route.query.state as string;
      const stateInfo = await this.auth.parseOidcState(state);
      
      if (!stateInfo) {
        throw new Error('Invalid OIDC state');
      }
      
      const { referer, provider } = stateInfo;
      const userManager = await createUserManager(provider);
      
      const user = await userManager.signinRedirectCallback();
      
      await this.userState.handleOidcLogin(user);
      
      if (referer) {
        this.$route.query.referer = referer;
      }
      
      this.showSuccessMessage = true;
      this.startCountdown();
    } catch (error: unknown) {
      console.error('OIDC callback error:', error);
      if (error instanceof Error) {
        this.displayAlert(`OIDC callback failed: ${error.message}`);
      } else {
        this.displayAlert('OIDC callback failed');
      }
      
      this.auth.resetToEmail();
    } finally {
      this.auth.setOidcCallbackLoading(false);
      this.auth.setLoading(false);
    }
  }

  async handleLogin() {
    if (!this.isLoginValid || !this.isPasswordValid) {
      this.displayAlert('Login and Password are required');
      return;
    }

    this.auth.setLoading(true);
    try {
      await this.userState.authenticate(this.login, this.password);

      this.showSuccessMessage = true;
      this.startCountdown();
    } catch (error: unknown) {
      console.error('Authentication error:', error);
      if (error instanceof Error) {
        this.displayAlert(error.message)
      } else if (typeof error === 'string') {
        this.displayAlert(error)
      } else {
        this.displayAlert('Unknown login error')
      }
      
      this.auth.resetToEmail();
    } finally {
      this.auth.setLoading(false);
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
    if (!this.isLoginValid) {
      this.displayAlert('Please enter a valid email address');
      return;
    }

    this.auth.setLoading(true);
    try {
      console.log('Proceeding to password step for email:', this.login);
      this.auth.updateState({
        emailEntered: true,
        showPassword: true,
        matchedProvider: null,
        providerDisplayName: '',
        showRetryOption: false,
        showErrorDetails: false
      });
      
      this.$nextTick(() => {
        this.focusPasswordInput();
      });
    } catch (error) {
      console.error('Error in email submit:', error);
      this.displayAlert('Error processing email. Please try again.');
    } finally {
      this.auth.setLoading(false);
    }
  }

  private getProviderIcon(provider: string): string {
    return this.auth.getProviderIcon(provider);
  }

  private resetToEmail() {
    this.auth.resetToEmail();
    this.$nextTick(() => {
      this.focusEmailInput();
    });
  }

  private retryOidcLogin() {
    this.auth.clearRetryOption();
    this.clearUrlErrorParams();
    
    if (this.state.matchedProvider && this.login) {
      this.handleOidcLogin(this.state.matchedProvider);
    } else {
      this.auth.resetToEmail();
    }
  }

  private usePasswordInstead() {
    this.auth.clearRetryOption();
    this.auth.updateState({
      emailEntered: true,
      showPassword: true,
      matchedProvider: null,
      providerDisplayName: '',
      showRetryOption: false,
      showErrorDetails: false
    });
    this.clearUrlErrorParams();
    
    this.$nextTick(() => {
      this.focusPasswordInput();
    });
  }

  private goBackToEmail() {
    this.auth.resetToEmail();
    this.clearUrlErrorParams();
    
    this.$nextTick(() => {
      this.focusEmailInput();
    });
  }

  private clearErrorAndReset() {
    this.auth.resetAfterError();
    this.clearUrlErrorParams();
    
    this.$nextTick(() => {
      this.focusEmailInput();
    });
  }

  private toggleErrorDetails() {
    this.auth.toggleErrorDetails();
  }

  private clearUrlErrorParams() {
    if (this.$route.query.error || this.$route.query.error_description) {
      const newQuery = { ...this.$route.query };
      delete newQuery.error;
      delete newQuery.error_description;
      this.$router.replace({ query: newQuery });
    }
  }

  private async handleOidcLogin(provider: string) {
    console.log('handleOidcLogin called with provider:', provider);
    console.log('Current loading state:', this.state.loading);
  

    console.log('Starting OIDC flow (loading already set)...');
    
    try {
      console.log('Creating user manager for provider:', provider);
      const userManager = await createUserManager(provider);
      console.log('User manager created successfully');
      
      console.log('Creating OIDC state...');
      const state = await this.auth.createOidcState(this.referer, provider);
      console.log('OIDC state created:', state);
      
      const signinOptions: any = { state };
      
      if (this.login) {
        signinOptions.login_hint = this.login;
        console.log('Added login_hint:', this.login);
        
        const emailDomain = this.login.split('@')[1];
        if (emailDomain) {
          signinOptions.domain_hint = emailDomain;
          console.log('Added domain_hint:', emailDomain);
        }
      }
      
      console.log('Calling signinRedirect with options:', signinOptions);
      await userManager.signinRedirect(signinOptions);
      console.log('signinRedirect completed successfully');
      
    } catch (error) {
      console.error('OIDC login failed:', error);
      this.displayAlert(`OIDC login failed: ${error instanceof Error ? error.message : 'Unknown error'}`);
      
      this.auth.resetToEmail();
    }
  }

  private startCountdown() {
    this.countdown = 2;
    
    this.countdownInterval = setInterval(() => {
      this.countdown--;
      
      if (this.countdown <= 0) {
        this.clearCountdown();
        this.redirectAfterSuccess();
      }
    }, 1000);
  }

  private clearCountdown() {
    if (this.countdownInterval) {
      clearInterval(this.countdownInterval);
      this.countdownInterval = null;
    }
  }

  private async redirectAfterSuccess() {
    try {
      const refererFromQuery = this.$route.query.referer as string;
      if (refererFromQuery) {
        await CONTINUUM_UI.navigate(refererFromQuery);
        return;
      }
      
      if (this.referer) {
        await CONTINUUM_UI.navigate(this.referer);
        return;
      }
      
      const redirectPath = this.$route.redirectedFrom?.fullPath;
      if (redirectPath && redirectPath !== "/") {
        await CONTINUUM_UI.navigate(redirectPath);
        return;
      }
      
      await CONTINUUM_UI.navigate('/applications');
    } catch (error) {
      console.error('Redirect error:', error);
      await CONTINUUM_UI.navigate('/applications');
    }
  }

  beforeUnmount() {
    this.clearCountdown();
  }
}
</script>