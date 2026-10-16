/** A header link. `external` renders a plain anchor, for targets outside the Vue router such as in-page sections. */
export interface HeaderLink {
  label: string
  to: string
  external?: boolean
}

declare module '#app' {
  interface PageMeta {
    /** Replaces the marketing header's links and call to action on this page. */
    headerNav?: {
      links: HeaderLink[]
      cta: HeaderLink
    }
  }
}
