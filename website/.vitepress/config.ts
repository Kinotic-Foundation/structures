import { defineConfig } from 'vitepress'

// https://vitepress.vuejs.org/reference/site-config
export default defineConfig({
  title: "Structures",
  description: "Structures is an open-source framework for data storage and retrieval, supporting schema evolution, data management, and providing a user-friendly GUI and OpenAPI interface.",
  base: '/structures/website/',
  themeConfig: {
    // https://vitepress.vuejs.org/reference/default-theme-config
    nav: nav(),

    sidebar: {
      '/guide/': sidebarGuide(),
      '/reference/': sidebarReference()
    },

    socialLinks: [
      { icon: 'github', link: ' https://github.com/Kinotic-Foundation/structures' }
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
    },
    {
      text: 'Test Status',
      link: 'https://kinotic-foundation.github.io/structures/allure' // Fully qualified URL
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
        { text: 'Create a new Structure', link: '/guide/new-structure' },
        { text: 'Apollo @policy Support',
          link: '/guide/graphos/overview',
          items: [
            { text: 'Policy Decorators', link: '/guide/graphos/policy-decorators' },
            { text: 'Policy Evaluation Flow', link: '/guide/graphos/policy-evaluation-flow' }
          ]
        }
      ]
    }
  ];
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
        { text: 'Javadoc', link: '/reference/javadoc' },
        { text: 'PolicyAuthorizationService', link: '/reference/graphos/policy-authorization-service' },
        { text: 'PolicyAuthorizer', link: '/reference/graphos/policy-authorizer' },
        { text: 'PolicyAuthorizationRequest', link: '/reference/graphos/policy-authorization-request' }
      ]
    }
  ];
}
