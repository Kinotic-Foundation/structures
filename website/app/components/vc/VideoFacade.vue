<script setup lang="ts">
/**
 * A poster with a play button that swaps to the player in the default slot when
 * clicked, so nothing behind the player loads until the visitor asks for it.
 */
defineProps<{
  poster: string
  label: string
}>()

const playing = ref(false)
</script>

<template>
  <div class="facade">
    <slot v-if="playing" />
    <button v-else type="button" class="facade__poster" :aria-label="label" @click="playing = true">
      <img :src="poster" alt="" class="facade__image" loading="lazy">
      <span class="facade__play" aria-hidden="true">
        <svg viewBox="0 0 44 44" fill="none">
          <path d="M35.5 18.6c2.9 1.7 2.9 5.1 0 6.8L15.9 36.7c-2.9 1.7-6.5-.4-6.5-3.4V10.7c0-3 3.6-5.1 6.5-3.4L35.5 18.6Z" fill="#fff" />
        </svg>
      </span>
    </button>
  </div>
</template>

<style scoped>
.facade {
  position: relative;
  background: #0E0E10;
  overflow: hidden;
}

.facade :slotted(iframe),
.facade :slotted(video) {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  border: 0;
  background: #000;
}

.facade__poster {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  border: 0;
  background: none;
  cursor: pointer;
}

.facade__image {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.facade__play {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: clamp(64px, 11%, 110px);
  aspect-ratio: 1;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(4px);
  transition: background 0.2s ease, transform 0.2s ease;
}

.facade__play svg {
  width: 38%;
  height: 38%;
}

.facade__poster:hover .facade__play,
.facade__poster:focus-visible .facade__play {
  background: rgba(255, 255, 255, 0.32);
  transform: scale(1.05);
}

.facade__poster:focus-visible {
  outline: 2px solid var(--color-k-mint);
  outline-offset: -4px;
}
</style>
