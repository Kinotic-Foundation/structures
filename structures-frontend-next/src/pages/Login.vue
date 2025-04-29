<template>
    <div class="login">

    </div>
  </template>
  
  <script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'

import Card from 'primevue/card'
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'
import FloatLabel from 'primevue/floatlabel'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'

// import CONTINUUM_UI from '@/frontends/continuum'
// import { type IUserState } from '@/states/IUserState'
// import { StructuresStates } from '@/frontends/states'

@Component
export default class Login extends Vue {
  static components = {
    Card,
    InputText,
    Password,
    Button,
    FloatLabel,
    Toast
  }

  login: string = ''
  password: string = ''
  loading: boolean = false
  referer: string | null = null

//   userState: IUserState = StructuresStates.getUserState()
  toast = useToast()

  get loginValid(): boolean {
    return !!this.login
  }

  get passwordValid(): boolean {
    return !!this.password
  }

  async handleLogin() {
    if (!this.loginValid || !this.passwordValid) {
      this.toast.add({
        severity: 'error',
        summary: 'Validation Error',
        detail: 'Login and password are required',
        life: 3000
      })
      return
    }

    this.loading = true
    try {
    //   await this.userState.authenticate(this.login, this.password)
    //   await CONTINUUM_UI.navigate(this.referer || '/')
    } catch (error: any) {
      this.toast.add({
        severity: 'error',
        summary: 'Login Failed',
        detail: error.message || 'Unknown error',
        life: 3000
      })
    }
    this.loading = false
  }

  hideAlert() {
    this.toast.removeAllGroups()
  }
}

  </script>
  
  <style scoped>
  .login {
    background: url("../assets/loginBackground_w_3840.jpg") no-repeat;
    background-size: cover;
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .login-container {
    width: 100%;
    max-width: 400px;
    padding: 2rem;
  }
  
  .login-card {
    padding: 2rem;
  }
  
  .logo-container {
    display: flex;
    justify-content: center;
    margin-bottom: 1.5rem;
  }
  
  .logo {
    max-width: 150px;
  }
  </style>
  