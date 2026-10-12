<script setup lang="ts">
/**
 * The Kinotic OS Cloud waitlist signup: an email field that joins the Waitlister
 * list, replaced by a confirmation once the signup is accepted.
 */
defineProps<{
  submitLabel: string
}>()

const WAITLIST_URL = 'https://waitlister.me/s/eKQTYP_j3hGR'

// The homepage renders this form more than once, so element ids are per instance.
const id = useId()
const emailId = `${id}-email`
const noteId = `${id}-note`
const errorId = `${id}-error`

const email = ref('')
const loading = ref(false)
const error = ref('')
const successTitle = ref('')
const successMessage = ref('')

async function joinWaitlist() {
  if (loading.value) return

  loading.value = true
  error.value = ''
  const controller = new AbortController()
  const timeout = setTimeout(() => controller.abort(), 15000)

  try {
    const response = await fetch(WAITLIST_URL, {
      method: 'POST',
      headers: {
        // JSON selects an inline response; form-encoded requests return a redirect.
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify({ email: email.value.trim() }),
      signal: controller.signal,
    })

    if (!response.ok) {
      error.value = response.status === 429
        ? 'Too many attempts. Please wait a little and try again.'
        : 'Unable to join the waitlist. Please try again.'
      return
    }

    const result = await response.json()
    if (result.success !== true) throw new Error('Signup was not accepted')

    const redirectUrl = result.redirect_url ?? result.redirectUrl
    const pendingConfirmation = result.is_pending_confirmation
      || (typeof redirectUrl === 'string'
        && new URL(redirectUrl, WAITLIST_URL).pathname.startsWith('/confirm-pending/'))

    if (pendingConfirmation) {
      successTitle.value = 'Check your inbox.'
      successMessage.value = 'Confirm your email to join the Kinotic OS Cloud waitlist.'
    } else {
      successTitle.value = result.is_new_sign_up === false || result.message === 'Already signed up'
        ? 'You’re already on the cloud waitlist.'
        : 'You’re on the cloud waitlist.'
      successMessage.value = 'We’ll email you when Kinotic OS Cloud access opens.'
    }
    email.value = ''
  } catch {
    error.value = 'Unable to join the waitlist. Please try again.'
  } finally {
    clearTimeout(timeout)
    loading.value = false
  }
}
</script>

<template>
  <div class="waitlist">
    <div v-if="successTitle" class="waitlist__success" role="status" aria-live="polite">
      <h3>{{ successTitle }}</h3>
      <p>{{ successMessage }}</p>
    </div>
    <form v-else :action="WAITLIST_URL" method="post" :aria-busy="loading" @submit.prevent="joinWaitlist">
      <div class="waitlist__row">
        <input
          :id="emailId"
          v-model.trim="email"
          class="waitlist__email"
          name="email"
          type="email"
          aria-label="Email address"
          placeholder="email@company.com"
          autocomplete="email"
          autocapitalize="none"
          :disabled="loading"
          :aria-invalid="error ? true : undefined"
          :aria-describedby="error ? `${noteId} ${errorId}` : noteId"
          required
        >
        <button type="submit" class="k-btn k-btn--mint waitlist__submit" :disabled="loading">
          {{ loading ? 'Joining…' : submitLabel }}
        </button>
      </div>
      <p v-if="error" :id="errorId" class="waitlist__error" role="alert">{{ error }}</p>
      <p :id="noteId" class="waitlist__note">
        We’ll email you when cloud access opens.
        <NuxtLink to="/privacy">Privacy policy</NuxtLink>
      </p>
    </form>
  </div>
</template>

<style scoped>
.waitlist {
  min-width: 0;
  max-width: 480px;
}

.waitlist__row {
  display: flex;
  align-items: stretch;
}

.waitlist__email {
  flex: 1;
  width: 100%;
  min-width: 0;
  padding: 14px 18px;
  border: 1px solid #45454C;
  border-right: none;
  border-radius: 3px 0 0 3px;
  background: #0D1A16;
  color: var(--color-k-text);
  font-family: var(--font-k-mono);
  font-size: 14px;
}

.waitlist__email::placeholder {
  color: var(--color-k-muted);
}

.waitlist__email:focus-visible,
.waitlist__submit:focus-visible {
  outline: 2px solid var(--color-k-mint);
  outline-offset: 3px;
}

.waitlist__submit {
  justify-content: center;
  padding: 15px 26px;
  border: none;
  border-radius: 0 3px 3px 0;
  white-space: nowrap;
  cursor: pointer;
}

.waitlist__submit:disabled,
.waitlist__email:disabled {
  opacity: 0.65;
  cursor: wait;
}

.waitlist__note {
  margin: 12px 0 0;
  color: var(--color-k-muted);
  font-size: 12px;
  line-height: 1.6;
}

.waitlist__note a {
  color: var(--color-k-text);
  text-decoration: underline;
  text-underline-offset: 3px;
}

.waitlist__error {
  margin: 12px 0 0;
  color: #FF8BA8;
  font-size: 14px;
}

.waitlist__success {
  padding: 24px;
  border: 1px solid #285942;
  border-radius: 4px;
  background: #102019;
}

.waitlist__success h3 {
  margin: 0;
  color: var(--color-k-mint);
  font-family: var(--font-k-display);
  font-size: 24px;
  font-weight: 500;
  line-height: 1.25;
}

.waitlist__success p {
  margin: 12px 0 0;
  color: var(--color-k-text);
  font-size: 14px;
  line-height: 1.6;
}

@media (max-width: 600px) {
  .waitlist__row {
    flex-direction: column;
    gap: 10px;
  }

  .waitlist__email {
    border-right: 1px solid #45454C;
    border-radius: 3px;
  }

  .waitlist__submit {
    border-radius: 3px;
  }
}
</style>
