<template>
  <div v-if="modalState.isOpen" class="modal-overlay">
    <div class="modal-content">
      <slot name="header">
        al;skdlsad;al
        <!-- <h2>{{ modalState.title }}</h2> -->
      </slot>
      <slot name="body">
        <!-- <p>{{ modalState.content }}</p> -->
      </slot>
      <slot name="footer">
        <button @click="closeModal">Close</button>
      </slot>
    </div>
  </div>
</template>

<script setup>
import { useModal } from '~/composables/useModal'

const { modalState, closeModal } = useModal()
console.log("ajsdkjsahdkjsahdj")
</script>

<style>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 300px;
  max-width: 500px;
}
</style>