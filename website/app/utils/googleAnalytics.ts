const MEASUREMENT_ID = 'G-MMMX3G571K'

declare global {
  interface Window {
    dataLayer: unknown[]
  }
}

let googleAnalyticsInitialized = false

// gtag.js only processes Arguments objects from the data layer, so this pushes
// `arguments` rather than the rest array.
function gtag(..._args: unknown[]): void {
  // eslint-disable-next-line prefer-rest-params
  window.dataLayer.push(arguments)
}

/**
 * Loads the Google tag with every storage type denied, so it sends cookieless
 * pings until {@link setGoogleAnalyticsConsent} grants analytics storage.
 */
export function initGoogleAnalytics(): void {
  if (googleAnalyticsInitialized || typeof window === 'undefined') return
  googleAnalyticsInitialized = true

  window.dataLayer = window.dataLayer || []
  gtag('consent', 'default', {
    ad_storage: 'denied',
    ad_user_data: 'denied',
    ad_personalization: 'denied',
    analytics_storage: 'denied',
  })
  gtag('js', new Date())
  gtag('config', MEASUREMENT_ID)

  const script = document.createElement('script')
  script.async = true
  script.src = `https://www.googletagmanager.com/gtag/js?id=${MEASUREMENT_ID}`
  document.head.appendChild(script)
}

/** Grants or revokes analytics cookies. Advertising storage stays denied. */
export function setGoogleAnalyticsConsent(granted: boolean): void {
  if (!googleAnalyticsInitialized) return
  gtag('consent', 'update', { analytics_storage: granted ? 'granted' : 'denied' })
}
