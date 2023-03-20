<template>
  <v-app id="frontend-app">
    <!-- value="true" is needed in conjunction with the mini variant. This says the drawer should be open... -->
    <!-- If we need the drawer to be closed on smaller screens this will need more work -->
    <v-navigation-drawer
        value="true"
        :mini-variant="frontendState.drawerOpen"
        clipped
        stateless
        absolute
        overflow
        app>

      <v-list class="navigation-drawer__list">
        <v-list-item v-for="item in navigationItems"
                     :key="item.path"
                     :to="item.path" v-show="typeof(item.meta.noShow) === 'undefined'">

          <v-list-item-action>
            <v-icon>{{ item.meta.icon }}</v-icon>
          </v-list-item-action>

          <v-list-item-content>
            <v-list-item-title>{{item.meta.title}}</v-list-item-title>
          </v-list-item-content>

        </v-list-item>
      </v-list>

    </v-navigation-drawer>
    <v-app-bar id="frontend-app__bar"
               clipped-left
               app>

      <v-app-bar-nav-icon @click.stop="frontendState.drawerOpen = !frontendState.drawerOpen"></v-app-bar-nav-icon>

      <v-toolbar-title>{{toolbarTitle}}</v-toolbar-title>

      <v-spacer></v-spacer>

      <frontend-launcher v-if="frontendState.frontends.length > 1"
                         :frontends="frontendState.frontends"
                         @frontend-selected="onFrontendSelected"/>

      <v-btn icon @click="toggleDarkMode">
        <v-icon>{{icons.toggleTheme}}</v-icon>
      </v-btn>

    </v-app-bar >

    <v-main>
      <!--Child routes will be rendered here.-->
      <router-view />
    </v-main>

    <notifications group="alert"
                   position="bottom center"
                   width="100%"
                   :duration="30000">
      <template slot="body" slot-scope="props">
        <v-alert type="error"
                 prominent
                 @click="props.close">
          <v-row align="center">
            <v-col class="grow">{{props.item.text}}</v-col>
            <v-col class="shrink">
              <v-btn @click="props.close"
                     color="black"
                     text>
                Close
              </v-btn>
            </v-col>
          </v-row>
        </v-alert>
      </template>
    </notifications>

    <notifications group="info"
                   position="bottom center"
                   width="100%"
                   :duration="5000">
      <template slot="body" slot-scope="props">
        <v-alert type="info"
                 @click="props.close">
          <v-row align="center">
            <v-col class="grow">{{props.item.text}}</v-col>
            <v-col class="shrink">
              <v-btn @click="props.close"
                     color="black"
                     text>
                Close
              </v-btn>
            </v-col>
          </v-row>
        </v-alert>
      </template>
    </notifications>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { inject } from 'inversify-props'
import { IFrontendState } from '../states'
import { RouteConfig, RouteRecord } from 'vue-router'
import { mdiThemeLightDark } from '@mdi/js'
import FrontendLauncher from '../components/FrontendLauncher.vue'

/**
 * Layout used to provide a place to put all CRUD views within the {@link FrontendLayout}
 * Really just a wrapper to support child routes inside the {@link FrontendLayout}
 */
@Component({
  components: { FrontendLauncher }
})
export default class FrontendLayout extends Vue {

  @inject()
  private frontendState!: IFrontendState

  private icons = {
    toggleTheme: mdiThemeLightDark
  }

  constructor() {
    super()
  }

  toggleDarkMode() {
    this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
    localStorage.setItem("dark_theme", this.$vuetify.theme.dark.toString());
  }

  public mounted(){
    const theme = localStorage.getItem("dark_theme");
    if (theme) {
      if (theme === "true") {
        this.$vuetify.theme.dark = true;
      } else {
        this.$vuetify.theme.dark = false;
      }
    }else if ( // Sets default theme based on user preferences.
        window.matchMedia &&
        window.matchMedia("(prefers-color-scheme: dark)").matches
    ) {
      this.$vuetify.theme.dark = true;
      localStorage.setItem(
          "dark_theme",
          this.$vuetify.theme.dark.toString()
      );
    }
  }

  public onFrontendSelected(routeConfig: RouteConfig):void {
    let toGo: RouteConfig | string
    if (typeof (routeConfig.children) !== 'undefined' && routeConfig.children.length > 0) {
      toGo = routeConfig.children[0]
    } else {
      toGo = routeConfig.path
    }
    this.$router.push(toGo)
  }

  public get toolbarTitle() {
    let ret = ''
    const startIndex: number = this.$route.matched.length - 1
    for (let i: number = startIndex; i >= 0; i--) {
      if(i < startIndex){
        ret = ' ' + ret
      }
      ret = this.resolveTitle(this.$route.matched[i]) + ret
    }

    return ret
  }

  public get navigationItems() :RouteConfig[] | undefined{
    return this.frontendState.selectedFrontend.children
  }



  private resolveTitle(value: RouteRecord): string {
    let ret: string = ''
    if (value.meta !== undefined
        && value.meta.title !== undefined) {
      ret = value.meta.title
    }
    return ret
  }

}
</script>

<style>
#frontend-app__bar .v-toolbar__content{
  padding: 0 18px;
}
</style>
<style scoped>
.navigation-drawer__list{
  padding: 0;
}
</style>
