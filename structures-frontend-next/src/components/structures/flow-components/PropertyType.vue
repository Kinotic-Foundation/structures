<template>
  <div class="flex gap-1 justify-end">
    <span
        v-for="(type, index) in parsedTypes"
        :key="index"
        class="text-[10px] px-2 py-1 rounded-full font-bold whitespace-nowrap cursor-pointer"
        :class="type.className"
        @dblclick.stop="onEdit"
    >
      {{ type.label }}
    </span>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Emit } from 'vue-facing-decorator'

@Component
export default class PropertyType extends Vue {
  @Prop({ required: true }) readonly type!: string

  @Emit('edit')
  onEdit(event: MouseEvent) {
    return event
  }

  get parsedTypes() {
    const match = this.type?.match(/^array<(.+)>$/)
    const baseType = match ? match[1] : this.type

    const isSpecialType = ['object', 'enum', 'union'].includes(baseType)

    const getClass = () =>
        isSpecialType ? 'bg-lime-100 text-lime-700' : 'bg-surface-100 text-surface-600'

    if (match) {
      return [
        { label: 'Array', className: getClass() },
        { label: this.capitalize(baseType), className: getClass() }
      ]
    }

    return [{ label: this.capitalize(this.type), className: getClass() }]
  }

  capitalize(str: string) {
    return str ? str.charAt(0).toUpperCase() + str.slice(1) : ''
  }
}
</script>
