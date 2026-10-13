<!-- Floating isometric cubes over a red and mint glow; the parent sets the height. -->
<template>
  <div class="cubes" aria-hidden="true">
    <div class="cubes__glow cubes__glow--red" />
    <div class="cubes__glow cubes__glow--mint" />
    <svg class="cubes__svg" viewBox="0 0 560 470" fill="none" preserveAspectRatio="xMidYMid meet">
      <g class="cube cube--fast">
        <path d="M 96 92 L 141 71 L 186 92 L 141 113 Z" fill="#B0324C" />
        <path d="M 96 92 L 96 142 L 141 163 L 141 113 Z" fill="#7A1D33" />
        <path d="M 186 92 L 186 142 L 141 163 L 141 113 Z" fill="#96263F" />
      </g>
      <g class="cube cube--fast" style="animation-delay: -3s;">
        <path d="M 428 122 L 466 104 L 504 122 L 466 140 Z" fill="#4BF2B0" />
        <path d="M 428 122 L 428 164 L 466 182 L 466 140 Z" fill="#0E9E68" />
        <path d="M 504 122 L 504 164 L 466 182 L 466 140 Z" fill="#17B87C" />
      </g>
      <g class="cube">
        <path d="M 168 236 L 258 194 L 348 236 L 258 278 Z" fill="#C6455C" />
        <path d="M 168 236 L 168 336 L 258 378 L 258 278 Z" fill="#8E2038" />
        <path d="M 348 236 L 348 336 L 258 378 L 258 278 Z" fill="#A62B44" />
      </g>
      <g class="cube" style="animation-delay: -2s;">
        <path d="M 448 344 L 480 329 L 512 344 L 480 359 Z" fill="#8E3247" />
        <path d="M 448 344 L 448 378 L 480 393 L 480 359 Z" fill="#5E1A29" />
        <path d="M 512 344 L 512 378 L 480 393 L 480 359 Z" fill="#752338" />
      </g>
    </svg>
  </div>
</template>

<style scoped>
.cubes {
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cubes__glow {
  position: absolute;
  filter: blur(6px);
}

.cubes__glow--red {
  left: 12%;
  top: 18%;
  width: 520px;
  height: 420px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(236, 31, 82, 0.22), transparent 70%);
}

.cubes__glow--mint {
  right: -6%;
  bottom: 2%;
  width: 460px;
  height: 400px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(40, 254, 180, 0.12), transparent 70%);
}

.cubes__svg {
  position: relative;
  width: 100%;
  height: 100%;
}

.cube {
  transform-box: fill-box;
  transform-origin: center;
  animation: cubes-float 7s ease-in-out infinite;
}

.cube--fast {
  animation: cubes-tumble 9s ease-in-out infinite;
}

@keyframes cubes-float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-12px); }
}

@keyframes cubes-tumble {
  0%, 100% { transform: translateY(0) rotate(-2deg); }
  50% { transform: translateY(-22px) rotate(2deg); }
}
</style>
