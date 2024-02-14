<template>
    <Glitch style="font-size: 10em" message="Credentials provided do not have access to resource" />
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator'
    import Glitch from '@/frontends/continuum/components/Glitch.vue'

    @Component({
    components: { Glitch }
})
export default class AccessDenied extends Vue {

}
</script>

