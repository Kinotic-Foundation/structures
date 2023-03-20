# continuum-propel
Continuum Frontend Application

## Table of Contents
- [Caveats](#caveats)

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn run serve
```

### Compiles and minifies for production
```
yarn run build
```

### Run your tests
```
yarn run test
```

### Lints and fixes files
```
yarn run lint
```

## Caveats
- If you want to ignore typescript errors when a module has no type definitions. See below
```
// @ts-ignore
import json from 'highlight.js/lib/languages/json'
```
- If you want to use a Typescript interface for the type of a vue Prop
- [See why here](https://frontendsociety.com/using-a-typescript-interfaces-and-types-as-a-prop-type-in-vuejs-508ab3f83480)
```typescript
  interface ComplexObjectInterface{}

  let props = {
    propExample: {
      type: Object as () => ComplexObjectInterface
    }
  }
```
- Include slots from parent into child of child component 
    - [See here](https://gist.github.com/loilo/73c55ed04917ecf5d682ec70a2a1b8e2)

- Seems there is a bug in the newest inversify-props that prevents our bindings from working
  - We are going to have to stay on 1.4.9 for now. https://github.com/CKGrafico/inversify-props/issues/25#issuecomment-770142835

## Dependency Injection

We use the libraries listed below to provide a complete DI setup.
 - [InversifyJS](http://inversify.io/)
 - [inversify-props](https://github.com/CKGrafico/inversify-props)
 

## Help
- [Stylus Docs](http://stylus-lang.com/)
- [Vue Docs](https://vuejs.org/v2/guide/)
- [vue-router](https://router.vuejs.org/)
- [vuex](https://vuex.vuejs.org/)
- [vue-loader](https://vue-loader.vuejs.org/)
- [Vue Typescript Components](https://github.com/vuejs/vue-class-component)
- [Vuex Typescript Stores](https://championswimmer.in/vuex-module-decorators/)
- [Tutorial Typescript Components](https://alligator.io/vuejs/typescript-class-components/)
- [Deep Dive Typescript](https://basarat.gitbooks.io/typescript/content/docs/future-javascript.html)
- [Typescript EcmaScript Version Setup](https://www.meziantou.net/2018/05/14/which-version-of-ecmascript-should-i-use-in-the-typescript-configuration)
- [StompJs](https://github.com/stomp-js/stompjs) // Newly rewritten with typescript support
- [RxJs](https://rxjs-dev.firebaseapp.com/guide/overview) // Newly rewritten with typescript support (chosen over kefir due to typescript support)
- [RxJs Learn](https://www.learnrxjs.io/)
- [Typescript Optional](https://github.com/bromne/typescript-optional)
- [vue-highlight.js](https://github.com/gluons/vue-highlight.js)
- [ow](https://github.com/sindresorhus/ow) Function argument Validation library
- [vue-notification](https://github.com/euvl/vue-notification) Toast Notification Component
- [Terser](https://github.com/terser/terser#minify-options) Terser Options

## Vue.js Reference
- [Vue API](https://vuejs.org/v2/api/)
- [vue.config.js](https://cli.vuejs.org/config/#vue-config-js)

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
