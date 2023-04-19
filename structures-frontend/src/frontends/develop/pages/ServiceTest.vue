<template>
    <div class="serviceTest">
        <v-btn color="primary" @click="doGetFreeMemory">Get Free Memory</v-btn>

        <v-btn color="primary" @click="doTestUser">Test User</v-btn>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import {IEventBus} from '@kinotic-foundation/continuum-js'
import {ITestService} from '@/frontends/develop/services'
import { inject } from 'inversify-props';

@Component({
    components: { },
    props: { }
})
export default class ServiceTest extends Vue {

    @inject()
    private testService!: ITestService

    constructor() {
        super()
    }

    // Lifecycle hook
    public mounted() {
    }

    public beforeDestroy() {
    }

    public doGetFreeMemory() {
        this.testService.getFreeMemory().then((value) => {
            console.log('Service Result', value)
        })
    }

    public doTestUser() {
        this.testService.testUser().then((value) => {
            console.log('Service Result', value)
        })
    }

}
</script>

<style scoped>

</style>
