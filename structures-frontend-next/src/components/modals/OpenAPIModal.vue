<template>
  <transition name="fade">
    <div
      v-if="visible"
      class="fixed inset-0 bg-white z-50 flex flex-col"
    >
      <div class="flex justify-between items-center p-4 border-b border-gray-200">
        <div class="flex justify-center items-center gap-3">
            <img src="@/assets/scalar.svg" />
            <h2 class="text-lg font-semibold">OpenAPI Reference</h2>
        </div>
        <button @click="$emit('close')" class="text-gray-500 hover:text-black text-xl">×</button>
      </div>
      <div class="flex-1 overflow-hidden">
        <iframe
          ref="iframeRef"
          src="/scalar-ui.html"
          width="100%"
          height="100%"
          frameborder="0"
        ></iframe>
      </div>
    </div>
  </transition>
</template>

<script lang="ts">
import { Component, Vue, Prop, Emit, Ref } from 'vue-facing-decorator'
import Cookies from 'js-cookie'
import { APPLICATION_STATE } from '@/states/IApplicationState'

@Component({})
export default class OpenAPIModal extends Vue {
  @Prop({ required: true }) readonly visible!: boolean
  @Emit('close') close(): void {}
  @Ref('iframeRef') iframeRef!: HTMLIFrameElement

  onMounted() {
    this.setupIframe()
  }

  private setupIframe() {
    const namespace = APPLICATION_STATE.currentApplication?.id || 'default'
    const token = Cookies.get('token')

    if (!token) {
      console.warn('No token found in cookie')
      return
    }

    this.iframeRef?.addEventListener('load', () => {
      this.iframeRef.contentWindow?.postMessage({ namespace, token }, '*')
    })
  }
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
