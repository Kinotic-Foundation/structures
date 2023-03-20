<template>
    <div>
        <highlight-code class="stream-test__code" lang="json">
            {{convertToJsonString(event)}}
        </highlight-code>
    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator'
    import { EventConstants, IEvent } from '@kinotic-foundation/continuum-js'

    @Component({
    components: { }
})
export default class EventItem extends Vue {

    @Prop()
    public event!: IEvent

    constructor() {
        super()
    }

    // Lifecycle hooks
    public mounted() {
    }

    public beforeDestroy() {
    }

    public convertToJsonString(event: IEvent): string {
        const dataAsString = event.getDataString()
        const headers: any = {}
        event.headers.forEach ((v: string, k: string) => { headers[k] = v })

        let data = ''
        if(dataAsString.length > 0){
            if(event.headers.get(EventConstants.CONTENT_TYPE_HEADER) === EventConstants.CONTENT_JSON){
                data = JSON.parse(dataAsString)
            }else{
                data = dataAsString
            }
        }

        const jsonObj = {
            headers,
            data: data
        }

        return JSON.stringify(jsonObj, null, 0)
    }

}
</script>

<style>
    .stream-test__code, .stream-test__code code {
        width: 100%;
    }
</style>
