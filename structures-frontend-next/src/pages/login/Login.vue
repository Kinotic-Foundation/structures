<template>
  <div class="flex w-full justify-center items-center h-screen max-w-[1440px] mx-auto">
      <div class="hidden md:block w-1/2 h-full bg-[url(@/assets/login-page-image.png)] bg-no-repeat bg-cover bg-bottom-left">
    </div>
    <div class="w-1/2 h-full flex flex-col justify-around items-center bg-center bg-cover">
      <div class="w-[320px] flex flex-col items-center">

        <img src="@/assets/login-page-logo.svg" class="w-[218px] h-[45px] mb-[53px]" />

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

        <div class="flex justify-center items-center w-full mt-10 mb-8">
          <p class="border border-gray-300 w-full"></p>
          <span class="text-gray-300 mx-3">OR</span>
          <p class="border border-gray-300 w-full"></p>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer"
             @click="handleOktaLogin">
          <img src="@/assets/okta-icon.svg" class="mr-6" />
          <span>Continue with Okta</span>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer"
             @click="handleGoogleLogin">
          <img src="@/assets/google-icon.svg" class="mr-6" />
          <span>Continue with Google</span>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer"
             @click="handleMicrosoftLogin">
          <img src="@/assets/microsoft_online-icon.svg" class="mr-6" />
          <span>Continue with Microsoft</span>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer"
             @click="handleKeycloakLogin">
          <img src="@/assets/keycloak-icon.svg" class="mr-6" />
          <span>Continue with Keycloak</span>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer"
             @click="handleAppleLogin">
          <img src="@/assets/apple-logo.svg" class="mr-6" />
          <span>Continue with Apple</span>
        </div>
        <div class="text-[#212121] cursor-pointer">
          Forgot Password
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
import { createUserManager, type OidcProvider } from './OidcConfiguration'

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
  valid: boolean = true

  toast = useToast()

  private userState: IUserState = StructuresStates.getUserState()

  private loginRules = [(v: string) => !!v || 'Login required']
  private passwordRules = [(v: string) => !!v || 'Password required']

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
    this.loading = true;
    try {
      console.log(`Starting OIDC login for provider: ${provider}`);
      const userManager = createUserManager(provider);
      
      console.log('UserManager created, starting signinRedirect...');
      
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
      if (error instanceof Error) {
        this.displayAlert(`OIDC login failed: ${error.message}`);
      } else {
        this.displayAlert('OIDC login failed');
      }
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

  private async handleKeycloakLogin() {
    console.log('Starting Keycloak login...');
    try {
      await this.handleOidcLogin('keycloak');
    } catch (error) {
      console.error('Keycloak login error:', error);
      throw error;
    }
  }

  private async handleAppleLogin() {
    // Note: Apple login might need special handling as it's not a standard OIDC provider
    this.displayAlert('Apple login not implemented yet');
  }

  async mounted() {
    // Check if we're returning from an OIDC login
    if (this.$route.query.code && this.$route.query.state) {
      this.loading = true;
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
        const userManager = createUserManager(provider);
        
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
        this.loading = false;
      }
    }
  }
}
</script>