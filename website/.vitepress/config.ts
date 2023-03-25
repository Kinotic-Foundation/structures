import { defineConfig } from 'vitepress'

// https://vitepress.vuejs.org/reference/site-config
export default defineConfig({
  title: "Structures",
  description: "Structures is an open-source framework for data storage and retrieval, supporting schema evolution, data management, and providing a user-friendly GUI and OpenAPI interface.",
  base: '/structures/',
  themeConfig: {
    // https://vitepress.vuejs.org/reference/default-theme-config
    nav: nav(),

    sidebar: {
      '/guide/': sidebarGuide(),
      '/reference/': sidebarReference()
    },

    socialLinks: [
      { icon: 'github', link: ' https://github.com/Kinotic-Foundation/continuum-framework' }
    ],
    footer: {
      message: 'Released under the Apache License.',
      copyright: 'Copyright © 2018-present Kinotic Foundation'
    }
  }
})

function nav() {
  return [
    { text: 'Guide', link: '/guide/overview', activeMatch: '/guide/' },
    {
      text: 'Reference',
      link: '/reference/structures-config',
      activeMatch: '/reference/'
    }
  ]
}

function sidebarGuide() {
  return [
    {
      text: 'Introduction',
      items: [
        { text: 'What is Structures?', link: '/guide/overview' },
        { text: 'Getting Started', link: '/guide/getting-started' }
      ]
    },
    {
      text: 'Details',
      items: [
        { text: 'Creating a new Structure', link: '/guide/new-structure' },
      ]
    }
  ]
}

function sidebarReference() {
  return [
    {
      text: 'Reference',
      items: [
        { text: 'Structures Config', link: '/reference/structures-config' }
      ]
    },
    {
      text: 'API',
      items: [
        { text: 'Javadoc', link: '/reference/javadoc' }
      ]
    }
  ]
}
