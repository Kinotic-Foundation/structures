<template>
  <AuthPageShell :art="loginArt">
    <div class="login-form">
      <h2 class="signup-title">Create your organization</h2>

      <p class="mb-4 max-w-88 text-center leading-normal text-muted-color text-[0.9375rem]">
        Kinotic projects are backed by GitHub repositories, so your organization starts
        with your GitHub identity.
      </p>

      <div class="w-full max-w-80 flex flex-col gap-3 mt-2 mb-6">
        <SocialAuthButton
          provider="github"
          :action="apiUrl('/api/auth/org/signup/social/start/github')"
          intent="sign-up"
        />
      </div>

      <div class="text-center mt-4 text-sm">
        Already signed up? <router-link to="/login" class="login-link">Sign in</router-link>
      </div>
    </div>
  </AuthPageShell>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { apiUrl } from '@kinotic-ai/frontend-common'
import { AuthPageShell } from '@kinotic-ai/frontend-common'
import SocialAuthButton from '@/components/SocialAuthButton.vue'
import loginArt from '@/assets/login-background.svg'

const router = useRouter()

// A server that offers signing up with an email and a password, as a demonstration server does, signs up there
onMounted(async () => {
  try {
    const res = await fetch(apiUrl('/api/auth/org/signup/methods'), { credentials: 'same-origin' })
    if (res.ok && (await res.json()).password === true) {
      await router.replace('/signup/email')
    }
  } catch {
    // the GitHub sign-up stays on offer
  }
})
</script>
