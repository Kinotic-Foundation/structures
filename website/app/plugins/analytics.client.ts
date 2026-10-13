import { createApp, h } from 'vue'
import CookieConsent from '~/components/CookieConsent.vue'
import { initClarity, setClarityConsentV2 } from '~/utils/clarity'
import { initGoogleAnalytics, setGoogleAnalyticsConsent } from '~/utils/googleAnalytics'

const STORAGE_KEY = 'kinotic-clarity-consent'

type StoredConsent = 'accepted' | 'declined'

function readStored(): StoredConsent | null {
  try {
    const v = localStorage.getItem(STORAGE_KEY)
    return v === 'accepted' || v === 'declined' ? v : null
  } catch {
    return null
  }
}

function persist(value: StoredConsent): void {
  try {
    localStorage.setItem(STORAGE_KEY, value)
  } catch {
    // ignore (private mode, etc.)
  }
}

function mountBanner(onDecision: (accepted: boolean) => void): void {
  const container = document.createElement('div')
  container.setAttribute('data-cookie-consent', '')
  document.body.appendChild(container)

  const app = createApp({
    setup() {
      const handle = (accepted: boolean) => {
        onDecision(accepted)
        app.unmount()
        container.remove()
      }
      return () => h(CookieConsent, { onDecision: handle })
    },
  })
  app.mount(container)
}

function applyConsent(accepted: boolean): void {
  const value = accepted ? 'granted' : 'denied'
  setClarityConsentV2(value, value)
  setGoogleAnalyticsConsent(accepted)
}

export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig()
  const projectId = (config.public.clarityProjectId as string | undefined) || ''

  // Both providers start in denied/cookieless mode (Clarity through Consent Mode
  // in its project settings) and only upgrade once applyConsent grants storage.
  if (projectId) initClarity(projectId)
  initGoogleAnalytics()

  const stored = readStored()
  if (stored) {
    applyConsent(stored === 'accepted')
    return
  }

  // No prior choice: stay cookieless and ask the visitor.
  applyConsent(false)
  mountBanner((accepted) => {
    persist(accepted ? 'accepted' : 'declined')
    applyConsent(accepted)
  })
})
