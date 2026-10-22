<template>
  <component :is="icon ?? fallback" />
</template>

<script lang="ts">
import type { Component } from 'vue'

// Icons already looked up, null for a name Lucide has no icon of, so a remounted icon renders at once
// instead of showing its fallback first
const loadedIcons = new Map<string, Component | null>()
</script>

<script setup lang="ts">
import { markRaw, shallowRef, watch } from 'vue'
import { createDebug } from '../util/debug'

const debug = createDebug('lucide-icon')

/**
 * Renders the Lucide icon of the given name, in Lucide's kebab-case form such as `folder-git-2`
 * (https://lucide.dev/icons), loading that icon's code on first use; the fallback renders until it
 * has loaded, and for a name that is not a Lucide icon. Attributes such as `size` and
 * `stroke-width` pass to the rendered icon.
 */
const props = defineProps<{
  name: string | null
  fallback: Component
}>()

const icon = shallowRef<Component | null>(null)

watch(() => props.name, async name => {
  icon.value = name ? loadedIcons.get(name) ?? null : null
  if (name && !loadedIcons.has(name)) {
    try {
      // the name-to-icon map is loaded on the first icon shown, keeping it out of the startup chunks
      const { loadLucideIcon } = await import('./lucideIcons')
      const loaded = await loadLucideIcon(name)
      loadedIcons.set(name, loaded ? markRaw(loaded) : null)
      // a name changed while its icon loaded keeps the newer name's icon
      if (props.name === name) {
        icon.value = loadedIcons.get(name) ?? null
      }
    } catch (error) {
      debug('Failed to load the Lucide icon %s: %O', name, error)
    }
  }
}, { immediate: true })
</script>
