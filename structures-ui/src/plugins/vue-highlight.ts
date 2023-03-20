import Vue from 'vue'
import VueHighlightJS from 'vue-highlight.js'

// Highlight.js languages (Only required languages)
// @ts-ignore
import json from 'highlight.js/lib/languages/json'

/*
 * Import Highlight.js theme
 * Find more: https://highlightjs.org/static/demo/
 */
import 'highlight.js/styles/darcula.css'

/*
* Use Vue Highlight.js
*/
Vue.use(VueHighlightJS, {
    // Register only languages that you want
    languages: {
        json
    }
})
