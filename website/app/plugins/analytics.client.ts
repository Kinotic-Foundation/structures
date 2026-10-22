import { createApp, h } from 'vue'
import CookieConsent from '~/components/CookieConsent.vue'
import { initClarity, setClarityConsent } from '~/utils/clarity'
import { NO_CONSENT, persistConsent, readConsent, type ConsentChoice } from '~/utils/consent'
import { initGoogleAnalytics, setGoogleAnalyticsConsent } from '~/utils/googleAnalytics'

function applyConsent(choice: ConsentChoice): void {
  setClarityConsent(choice)
  setGoogleAnalyticsConsent(choice)
}

function mountBanner(current: ConsentChoice | null, onSave: (choice: ConsentChoice) => void): void {
  const container = document.createElement('div')
  container.setAttribute('data-cookie-consent', '')
  document.body.appendChild(container)

  const app = createApp({
    setup() {
      const handle = (choice: ConsentChoice) => {
        onSave(choice)
        app.unmount()
        container.remove()
      }
      return () => h(CookieConsent, { current, onSave: handle })
    },
  })
  app.mount(container)
}

export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig()
  const projectId = (config.public.clarityProjectId as string | undefined) || ''

  // Both providers start in denied/cookieless mode (Clarity through Consent Mode
  // in its project settings) and only upgrade once applyConsent grants storage.
  if (projectId) initClarity(projectId)
  initGoogleAnalytics()

  let current = readConsent()
  applyConsent(current ?? NO_CONSENT)

  let bannerOpen = false
  const open = () => {
    if (bannerOpen) return
    bannerOpen = true
    mountBanner(current, (choice) => {
      current = choice
      persistConsent(choice)
      applyConsent(choice)
      bannerOpen = false
    })
  }

  if (!current) open()

  return {
    provide: {
      /** Shows the cookie banner, opened to the category toggles when the visitor has already chosen. */
      cookieConsent: { open },
    },
  }
})
