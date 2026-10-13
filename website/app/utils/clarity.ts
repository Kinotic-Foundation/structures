import Clarity from '@microsoft/clarity'
import type { ConsentChoice } from '~/utils/consent'

let clarityInitialized = false

export function initClarity(projectId: string): void {
  if (clarityInitialized || !projectId || typeof window === 'undefined') return
  clarityInitialized = true
  Clarity.init(projectId)
}

/** Grants or revokes Clarity's analytics storage and its link to Microsoft Advertising. */
export function setClarityConsent(choice: ConsentChoice): void {
  if (!clarityInitialized) return
  Clarity.consentV2({
    ad_Storage: choice.marketing ? 'granted' : 'denied',
    analytics_Storage: choice.analytics ? 'granted' : 'denied',
  })
}
