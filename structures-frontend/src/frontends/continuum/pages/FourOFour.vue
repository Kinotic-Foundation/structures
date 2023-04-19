<template>
    <Glitch style="font-size: 10em" message="404" />
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator'
    import Glitch from '@/frontends/continuum/components/Glitch.vue'

    @Component({
    components: { Glitch }
})
export default class FourOFour extends Vue {

}
</script>

