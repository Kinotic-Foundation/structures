<template>
    <section class="hero">
      <div class="hero-section-info">
        <p class="hero-title">
          <span class="hero-title-top">
            Data Evolution
          </span>
          <span class="hero-title-bottom">
            Made Simple
          </span>
        </p>
        <span class="hero-subtitle">
          Empower your teams to adapt fast—Structures supports safe schema changes, 
          full CRUD operations, and effortless integration through a sleek GUI and OpenAPI.
        </span>
        <div class="hero-section-buttons">
          <button class="start-project-button">Start your project</button>
          <button class="request-demo-button">Request for demo</button>
        </div>
      </div>
      <div>
        <img src="../../public/images/hero-section-image.png" />
      </div>
    </section>
  </template>
  
  <style scoped>

.hero {
  width: 100%;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  background-image:
    /* linear-gradient(180deg, rgba(0, 0, 0, 1) 0%, rgba(30, 41, 101, 0.20) 100%), */
    url("../../public/images/background-home.png");

  display: flex;
  justify-content: center;
  align-items: center;
  gap: 120px;
  /* margin-top: -64px; */
}

  .hero-section-info {
    max-width: 660px;
  }
  .hero-title {
    display: flex;
    flex-direction: column;
    margin-bottom: 40px;
  }
  .hero-subtitle {
    font-size: 19px;
  }
  .hero-title-top{
    font-size: 81px;
    color: white;
    margin-bottom: 50px;
  }
  .hero-title-bottom {
    font-size: 66px;
    color:#C8FB39
  }
  .hero-section-buttons {
    margin-top: 40px;
    display: flex;
    gap: 30px;
  }
  .start-project-button {
    padding: 8px 12px;
    border: none;
    border-radius: 10px;
    background-color: #3651ED;
    color: white;
    font-weight: 500;
  }
  .request-demo-button {
    padding: 8px 12px;
    border: none;
    border-radius: 10px;
    background-color: white;
    color: #64748B;
    font-weight: 500;

  }
  </style>
  