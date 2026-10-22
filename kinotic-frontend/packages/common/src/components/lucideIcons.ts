import type { Component } from 'vue'

// One lazily loaded chunk per Lucide icon; a name fetches only its own icon's code. The query makes
// these modules copies of their own, so the icons the apps import by name stay in the apps' chunks
// rather than each becoming a chunk the startup graph preloads, and the glob leaves out the index
// module, which imports every icon.
const ICON_MODULES = import.meta.glob<Component>(
    ['./*.mjs', '!./index.mjs'],
    { import: 'default', query: { lucide: 'icon' }, base: '../../node_modules/@lucide/vue/dist/esm/icons/' })

/**
 * Loads the Lucide icon of the given name, in Lucide's kebab-case form such as `folder-git-2`.
 *
 * @param name the icon's name
 * @return the icon's component, or null when Lucide has no icon of that name
 */
export async function loadLucideIcon(name: string): Promise<Component | null> {
  const load = ICON_MODULES[`./${name}.mjs`]
  return load ? load() : null
}
