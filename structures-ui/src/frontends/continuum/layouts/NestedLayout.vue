<template>

  <!--  :key is required here so we will force a component reload when the route changes even if the new route is using the same component  -->
  <router-view :key="$route.fullPath"></router-view>

</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

/**
 * Layout used to provide a place to put all Nested views within the {@link FrontendLayout}
 * This is a wrapper that allows many different subviews to be accessible while only showing a single active menu link in the left nav bar
 */
@Component({
    components: { }
})
export default class NestedLayout extends Vue {

    constructor() {
        super()
    }

}
</script>
