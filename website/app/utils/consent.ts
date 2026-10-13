// Visitors' existing choices live under this key, so it stays fixed.
const STORAGE_KEY = 'kinotic-clarity-consent'

/** A visitor's choice for each optional cookie category. Necessary storage is always on. */
export interface ConsentChoice {
  analytics: boolean
  marketing: boolean
}

/** The choice in effect before a visitor decides: every optional category off. */
export const NO_CONSENT: ConsentChoice = { analytics: false, marketing: false }

/** Returns the visitor's saved choice, or null when they have not chosen yet. */
export function readConsent(): ConsentChoice | null {
  let stored: string | null = null
  try {
    stored = localStorage.getItem(STORAGE_KEY)
  } catch {
    // Storage is unavailable in some private modes; treat it as no choice.
  }

  let choice: ConsentChoice | null = null
  // A single accept or decline covers every category.
  if (stored === 'accepted') {
    choice = { analytics: true, marketing: true }
  } else if (stored === 'declined') {
    choice = { ...NO_CONSENT }
  } else if (stored) {
    try {
      const parsed = JSON.parse(stored)
      if (typeof parsed?.analytics === 'boolean' && typeof parsed?.marketing === 'boolean') {
        choice = { analytics: parsed.analytics, marketing: parsed.marketing }
      }
    } catch {
      // An unreadable value counts as no choice, so the banner asks again.
    }
  }
  return choice
}

/** Saves the visitor's choice for later visits. */
export function persistConsent(choice: ConsentChoice): void {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(choice))
  } catch {
    // Storage is unavailable in some private modes; the choice lasts for this page load.
  }
}
