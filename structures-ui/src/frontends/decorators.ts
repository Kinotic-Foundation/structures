import { createDecorator } from 'vue-class-component'


export const NoCache = createDecorator((options, key) => {
    // component options should be passed to the callback
    // and update for the options object affect the component
    if (options.computed !== undefined) {
        (options.computed[key] as any).cache = false
    }
})


export const Persist = createDecorator((options, key) => {
    // component options should be passed to the callback
    // and update for the options object affect the component
    const i = 0
})
