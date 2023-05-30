<template>
  <router-view />
</template>

<script lang="ts">
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import { inject } from 'inversify-props'
  import {IEventBus} from '@kinotic/continuum'
  import Keycloak from "keycloak-js"
  import {IUserState} from "@/frontends/continuum";


  @Component({
    components: { },
    props: { keycloak: Keycloak }
  })
  export default class Main extends Vue {

    @inject()
    public eventBus!: IEventBus

    @inject()
    private userState!: IUserState

    public async created() {
      if(process.env.VUE_APP_KEYCLOAK_SUPPORT === "true") {
        await this.userState.authenticateKeycloak(this.userState.getUri(), this.$props.keycloak)
        setInterval(async () => {
          try {
            let refreshed: boolean = await this.$props.keycloak.updateToken(70)
            if (refreshed) {
              console.log('Token refreshed' + refreshed);
            } else {
              let delta: number = 0
              if(this.$props.keycloak.tokenParsed?.exp){
                delta += this.$props.keycloak.tokenParsed?.exp
              }
              if(this.$props.keycloak.timeSkew){
                delta += this.$props.keycloak.timeSkew
              }
              console.warn('Token not refreshed, valid for '
                  + Math.round(delta - new Date().getTime() / 1000) + ' seconds');
            }
          }catch(error: any){
            console.error('Failed to refresh token')
          }
        }, 6000)
      }
    }

    public beforeDestroy() {
      this.eventBus.disconnect()
    }

  }

</script>
