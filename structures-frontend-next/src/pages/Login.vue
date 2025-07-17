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

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer">
          <img src="@/assets/google-icon.svg" class="mr-6" />
          <span>Continue with Google</span>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer">
          <img src="@/assets/microsoft_online-icon.svg" class="mr-6" />
          <span>Continue with Microsoft</span>
        </div>

        <div class="flex border border-[#B8BCBD] w-full p-4 mb-4 rounded-[5px] text-black/50 cursor-pointer">
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
      await this.userState.authenticate(this.login, this.password);

      if (this.referer) {
        await CONTINUUM_UI.navigate(this.referer);
      } else {
        this.$route.redirectedFrom?.fullPath !== "/" && this.$route.redirectedFrom?.fullPath ? this.$router.push(this.$route.redirectedFrom?.fullPath) : this.$router.push(`/applications`)
        // await CONTINUUM_UI.navigate('/applications');

      }

} catch (error: unknown) {
  if (error instanceof Error) {
    this.displayAlert(error.message)
  } else if (typeof error === 'string') {
    this.displayAlert(error)
  } else {
    this.displayAlert('Unknown login error')
  }
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
}
</script>