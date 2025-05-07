<script setup lang="ts">
import PersonList from './components/PersonList.vue'
</script>

<template>
  <header>
    <h1>Structures Example</h1>
  </header>

  <main>
    <PersonList />
  </main>
</template>

<style scoped>
header {
  background: #2c3e50;
  color: white;
  padding: 20px;
  text-align: center;
}

main {
  padding: 20px;
}
</style>
