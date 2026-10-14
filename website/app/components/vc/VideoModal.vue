<script setup lang="ts">
/**
 * A full-screen overlay playing one YouTube video. It is open while `videoId` is set and
 * asks to be closed from its close button, a click outside the player, or Escape.
 */
const props = defineProps<{
  videoId: string | null
}>()

const emit = defineEmits<{ close: [] }>()

const closeButton = ref<HTMLButtonElement>()

const src = computed(() => props.videoId
  ? `https://www.youtube-nocookie.com/embed/${props.videoId}?autoplay=1&rel=0&playsinline=1&modestbranding=1&iv_load_policy=3&color=white`
  : '')

const onKeydown = (event: KeyboardEvent) => {
  if (event.key === 'Escape' && props.videoId) emit('close')
}

// The page behind the overlay stays put while it is open, and focus lands on the close
// button so keyboard users can leave the way they came.
watch(() => props.videoId, async (videoId) => {
  document.body.style.overflow = videoId ? 'hidden' : ''
  if (videoId) {
    await nextTick()
    closeButton.value?.focus()
  }
})

onMounted(() => window.addEventListener('keydown', onKeydown))

onBeforeUnmount(() => {
  window.removeEventListener('keydown', onKeydown)
  document.body.style.overflow = ''
})
</script>

<template>
  <div v-if="videoId" class="modal" role="dialog" aria-modal="true" aria-label="Kinotic video" @click="emit('close')">
    <div class="modal__player" @click.stop>
      <iframe
        :src="src"
        title="Kinotic video"
        referrerpolicy="strict-origin-when-cross-origin"
        allow="autoplay; encrypted-media; fullscreen; picture-in-picture"
        allowfullscreen
      />
    </div>
    <button ref="closeButton" type="button" class="modal__close" aria-label="Close video" @click="emit('close')">
      <svg width="16" height="16" viewBox="0 0 16 16" fill="none" aria-hidden="true">
        <path d="M2.5 2.5l11 11M13.5 2.5l-11 11" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" />
      </svg>
    </button>
  </div>
</template>

<style scoped>
.modal {
  position: fixed;
  inset: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 28px;
  background: rgba(3, 3, 4, 0.88);
  backdrop-filter: blur(10px);
}

.modal__player {
  position: relative;
  width: min(1080px, 100%);
  aspect-ratio: 16 / 9;
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 12px;
  background: #000;
  box-shadow: 0 40px 120px rgba(0, 0, 0, 0.7);
  overflow: hidden;
}

.modal__player iframe {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  border: 0;
}

.modal__close {
  position: absolute;
  top: 22px;
  right: 26px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 44px;
  height: 44px;
  border: 1px solid rgba(255, 255, 255, 0.16);
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.08);
  color: var(--color-k-text);
  cursor: pointer;
  transition: background 0.2s ease;
}

.modal__close:hover,
.modal__close:focus-visible {
  background: rgba(255, 255, 255, 0.18);
}

.modal__close:focus-visible {
  outline: 2px solid var(--color-k-mint);
  outline-offset: 2px;
}

@media (max-width: 768px) {
  .modal {
    padding: 16px;
  }

  .modal__close {
    top: 12px;
    right: 12px;
  }
}
</style>
