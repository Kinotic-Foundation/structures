<script setup lang="ts">
/**
 * A video poster with a play button. Clicking it emits `play`; the parent decides where
 * the video opens, so nothing loads until the visitor asks for it.
 */
defineProps<{
  poster: string
  label: string
}>()

defineEmits<{ play: [] }>()
</script>

<template>
  <button type="button" class="poster" :aria-label="label" @click="$emit('play')">
    <img :src="poster" alt="" class="poster__image" loading="lazy">
    <span class="poster__shade" aria-hidden="true" />
    <span class="poster__play" aria-hidden="true">
      <svg viewBox="0 0 44 44" fill="none">
        <path d="M35.5 18.6c2.9 1.7 2.9 5.1 0 6.8L15.9 36.7c-2.9 1.7-6.5-.4-6.5-3.4V10.7c0-3 3.6-5.1 6.5-3.4L35.5 18.6Z" fill="#fff" />
      </svg>
    </span>
  </button>
</template>

<style scoped>
.poster {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 0;
  border: 0;
  background: #0E0E10;
  overflow: hidden;
  cursor: pointer;
}

.poster__image,
.poster__shade {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
}

.poster__image {
  object-fit: cover;
}

.poster__shade {
  background: rgba(0, 0, 0, 0.25);
}

.poster__play {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: clamp(56px, 13%, 120px);
  aspect-ratio: 1;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(4px);
  transition: background 0.2s ease, transform 0.2s ease;
}

.poster__play svg {
  width: 38%;
  height: 38%;
}

.poster:hover .poster__play,
.poster:focus-visible .poster__play {
  background: rgba(255, 255, 255, 0.32);
  transform: scale(1.05);
}

.poster:focus-visible {
  outline: 2px solid var(--color-k-mint);
  outline-offset: -4px;
}
</style>
