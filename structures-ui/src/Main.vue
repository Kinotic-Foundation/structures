<template>
  <router-view />
</template>

<script lang="ts">
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import { inject } from 'inversify-props'
  import { IEventBus } from '@kinotic-foundation/continuum-js'


  @Component({
    components: { }
  })
  export default class Main extends Vue {

    @inject()
    public eventBus!: IEventBus

    public async mounted() {
        // await this.eventBus.connect('ws://localhost:58503/v1', 'guest', 'guest')
    }

    public beforeDestroy() {
      this.eventBus.disconnect()
    }

  }

</script>
