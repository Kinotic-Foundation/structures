<template>
    <section class="features">
      <div class="grid">
        <div class="feature" v-for="(f, i) in features" :key="i">
          <div class="feature-container">
            <img :src="getIconPath(f.icon)" />
            <h3 class="feature-container-title">{{ f.title }}</h3>
          </div>
          <p>{{ f.details }}</p>
        </div>
      </div>
    </section>
  </template>
  
  <script setup>
const getIconPath = (filename) => new URL(`../../public/icons/${filename}`, import.meta.url).href;

  const features = [
    {
      title: "Schema Flexibility",
      details:
        "Structures makes schema evolution easy—letting developers adjust data schemas over time without major changes to application code. This streamlines the management of complex data and helps teams adapt their data models as business needs evolve.",
        icon: "share.svg"
      },
    {
      title: "Data Management",
      details:
        "Structures equips developers with powerful tools to create, read, update, and delete data—making it easy to manage complex datasets and offering a flexible interface for data manipulation.",
        icon: "database.svg"
      },
    {
      title: "Intuitive GUI and OpenAPI",
      details:
        "Structures offers an intuitive GUI built with Continuum and an OpenAPI interface, making it easy for developers to manage data and integrate Structures into existing applications and workflows.",
        icon: "folder.svg"
      },
  ];
  </script>
  
  <style scoped>
  .features {
    padding: 2rem 1rem;
  }
  .section-title {
    text-align: center;
    font-size: 2rem;
    margin-bottom: 2rem;
  }
  .grid {
    display: flex;
    gap: 1.5rem;
    flex-wrap: wrap;
    justify-content: center;
    padding: 78px 88px;
  }
  .feature {
    flex: 1 1 250px;
    background:transparent;
    border: 1px solid rgba(256, 256, 256, 0.2);
    padding: 45px;
    border-radius: 12px;
  }
  .feature-container {
    display: flex;
    align-items: center;
    gap: 22px;
    margin-bottom: 30px;
  }
  </style>
  