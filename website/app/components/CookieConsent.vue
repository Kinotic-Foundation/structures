<script setup lang="ts">
import type { ConsentChoice } from '~/utils/consent'

const props = defineProps<{
  /** The visitor's saved choice, or null on a first visit. A saved choice opens the category toggles. */
  current: ConsentChoice | null
}>()

const emit = defineEmits<{
  save: [choice: ConsentChoice]
}>()

const customizing = ref(props.current !== null)
const analytics = ref(props.current?.analytics ?? false)
const marketing = ref(props.current?.marketing ?? false)

const categories = [
  {
    key: 'analytics',
    label: 'Analytics',
    description: 'Google Analytics and Microsoft Clarity cookies that measure visits and record anonymized sessions.',
    model: analytics,
  },
  {
    key: 'marketing',
    label: 'Marketing',
    description: 'Advertising cookies, and use of visit data by Google and Microsoft for ad measurement and personalization.',
    model: marketing,
  },
]

const save = (choice: ConsentChoice) => emit('save', choice)
</script>

<template>
  <div
    class="fixed inset-x-4 bottom-4 z-[100] mx-auto max-w-3xl rounded-2xl border border-white/10 bg-[#1A1A1C] p-5 shadow-2xl sm:p-6"
    role="dialog"
    aria-live="polite"
    aria-label="Cookie consent"
  >
    <p class="font-[InterRegular] text-[14px] leading-[150%] text-[#AFAFB4]">
      We use Microsoft Clarity and Google Analytics to understand how visitors
      use our site, and Google and Microsoft may use this data for advertising.
      By default we collect only anonymous, cookieless data. Accept all, reject
      all, or choose by category.
      <a href="/privacy" class="text-white underline hover:text-[#FF2D7A]">
        Learn more
      </a>.
    </p>

    <ul v-if="customizing" class="mt-4 divide-y divide-white/10 border-y border-white/10">
      <li class="flex items-center justify-between gap-6 py-3">
        <div>
          <div class="font-[InterMedium] text-[14px] text-white">Necessary</div>
          <p class="mt-0.5 font-[InterRegular] text-[13px] leading-[150%] text-[#8A8A92]">
            Keeps the site working and remembers this choice.
          </p>
        </div>
        <span class="shrink-0 font-[InterMedium] text-[12px] text-[#8A8A92]">Always on</span>
      </li>
      <li v-for="category in categories" :key="category.key" class="flex items-center justify-between gap-6 py-3">
        <div>
          <div :id="`consent-${category.key}`" class="font-[InterMedium] text-[14px] text-white">{{ category.label }}</div>
          <p class="mt-0.5 font-[InterRegular] text-[13px] leading-[150%] text-[#8A8A92]">
            {{ category.description }}
          </p>
        </div>
        <button
          type="button"
          role="switch"
          class="consent__switch"
          :aria-checked="category.model.value"
          :aria-labelledby="`consent-${category.key}`"
          @click="category.model.value = !category.model.value"
        >
          <span class="consent__thumb" />
        </button>
      </li>
    </ul>

    <div class="mt-4 flex flex-wrap items-center justify-end gap-3">
      <button
        v-if="!customizing"
        type="button"
        class="consent__button consent__button--outline"
        @click="customizing = true"
      >
        Customize
      </button>
      <button
        type="button"
        class="consent__button consent__button--solid"
        @click="save({ analytics: false, marketing: false })"
      >
        Reject all
      </button>
      <button
        v-if="customizing"
        type="button"
        class="consent__button consent__button--solid"
        @click="save({ analytics, marketing })"
      >
        Save choices
      </button>
      <button
        type="button"
        class="consent__button consent__button--accent"
        @click="save({ analytics: true, marketing: true })"
      >
        Accept all
      </button>
    </div>
  </div>
</template>

<style scoped>
.consent__button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  padding: 0 20px;
  border-radius: 9999px;
  font-family: InterMedium, sans-serif;
  font-size: 13px;
  color: #fff;
  transition: background 0.15s ease, transform 0.15s ease;
}

.consent__button--outline {
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.consent__button--outline:hover {
  background: rgba(255, 255, 255, 0.05);
}

.consent__button--solid {
  background: rgba(255, 255, 255, 0.12);
}

.consent__button--solid:hover {
  background: rgba(255, 255, 255, 0.18);
}

.consent__button--accent {
  background: linear-gradient(135deg, #FF2D7A 0%, #FF2A55 100%);
  box-shadow: 0 15px 30px rgba(255, 45, 122, 0.28);
}

.consent__button--accent:hover {
  transform: scale(1.02);
}

.consent__button:focus-visible,
.consent__switch:focus-visible {
  outline: 2px solid #FF2D7A;
  outline-offset: 3px;
}

.consent__switch {
  position: relative;
  flex: none;
  width: 44px;
  height: 24px;
  border-radius: 9999px;
  background: rgba(255, 255, 255, 0.16);
  transition: background 0.15s ease;
}

.consent__switch[aria-checked='true'] {
  background: #FF2D7A;
}

.consent__thumb {
  position: absolute;
  top: 3px;
  left: 3px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: #fff;
  transition: transform 0.15s ease;
}

.consent__switch[aria-checked='true'] .consent__thumb {
  transform: translateX(20px);
}
</style>
