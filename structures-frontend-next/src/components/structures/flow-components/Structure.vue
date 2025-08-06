<template>
  <div class="rounded-lg shadow bg-white text-xs w-72">
    <!-- Header -->
    <div class="flex items-center gap-2 rounded-t-lg font-bold px-3 py-2 bg-surface-950 text-white">
      <i class="pi pi-table" style="color: #C8FB39"/>
      <span>{{ props.data.label }}</span>
    </div>

    <div v-if="properties.length" class="flex flex-col">
      <div
          v-for="(property, index) in properties"
          :key="index"
          class="flex items-center justify-between px-3 py-2 border-b border-surface-100"
      >
        <!-- Property name (editable later) -->
        <div class="w-2/3 font-medium text-sm truncate">
          <template v-if="editingNameIndex === index">
            <InputText
                v-model="property.name"
                class="!w-full !h-5 !p-0 !m-0 !shadow-none focus:!ring-0"
                :class="{
                  '!border-0': !(editingNameError && editingNameIndex === index),
                  '!border-red-500 !border': editingNameError && editingNameIndex === index
                }"
                autofocus
                @blur="finishEditingName"
                @keyup.enter="finishEditingName"
            />
          </template>
          <template v-else>
            <span class="cursor-pointer" @dblclick="startEditingName(index)">
              {{ property.name }}
            </span>
          </template>
        </div>

        <!-- Type bubbles -->
        <PropertyType
            class="grow"
            :type="property.type"
            @edit="(e: MouseEvent) => editType(index, e)"
        />
      </div>
    </div>

    <!-- Add Property Button (Triggers Popover) -->
    <div
        ref="addButton"
        class="flex justify-center items-center gap-2 font-bold px-3 py-2 text-primary cursor-pointer"
        @click="togglePopover"
    >
      <i class="pi pi-plus"/>
      <span>Add property</span>
    </div>

    <!-- Popover -->
    <Popover ref="popover">
      <div class="w-full flex flex-col gap-4">
        <div class="w-full flex gap-3">
          <!-- Name Input -->
          <div class="w-1/2 flex flex-col gap-1">
            <label for="name" class="text-sm">Property Name</label>
            <InputText
                id="name"
                v-model="newProperty.name"
                :class="{'p-invalid': !!errors.name}"
                aria-describedby="name"
            />
            <small v-if="errors.name" class="!text-text-xs">{{ errors.name }}</small>
          </div>

          <!-- Type Dropdown -->
          <div class="w-1/2 flex flex-col gap-1">
            <label for="type" class="text-sm">Type</label>
            <CascadeSelect
                id="type"
                v-model="newProperty.type"
                :options="typeOptions"
                optionGroupLabel="label"
                optionLabel="label"
                optionValue="code"
                :optionGroupChildren="['children']"
                placeholder="Select type"
                :class="{'p-invalid': !!errors.type}"
                class="w-full"
            />
            <small v-if="errors.type" class="!text-text-xs">{{ errors.type }}</small>
          </div>
        </div>

        <!-- Add Property Button -->
        <Button class="self-start" label="Add property" @click="addProperty"/>
      </div>
    </Popover>
    <Popover ref="typeEditPopover" class="min-w-60">
      <div class="w-full flex flex-col gap-4">
        <div class="w-full flex flex-col gap-1">
          <label for="edit-type" class="text-sm">Type</label>
          <CascadeSelect
              id="edit-type"
              v-model="updatedType"
              :options="typeOptions"
              optionGroupLabel="label"
              optionLabel="label"
              optionValue="code"
              :optionGroupChildren="['children']"
              placeholder="Select type"
              class="w-full"
          />
        </div>

        <Button
            class="self-start"
            label="Update type"
            @click="updatePropertyType"
        />
      </div>
    </Popover>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Popover from 'primevue/popover'
import CascadeSelect from 'primevue/cascadeselect';
import PropertyType from "@/components/structures/flow-components/PropertyType.vue";

const props = defineProps<{ data: { label: string; fields: string[]; color: any } }>()

const popover = ref()
const addButton = ref()

const properties = ref<{ name: string; type: string }[]>([])

const typeEditPopover = ref()
const typeEditTarget = ref<HTMLElement | null>(null)
const editingTypeIndex = ref<number | null>(null)
const updatedType = ref<string | null>(null)

const newProperty = ref({
  name: '',
  type: ''
})

const primitiveTypes = [
  'boolean', 'byte', 'char', 'date', 'double', 'enum',
  'float', 'integer', 'long', 'map', 'object',
  'short', 'string', 'union'
]

const typeOptions = [
  {
    label: 'Array',
    code: 'array',
    children: primitiveTypes
        .filter(t => t !== 'array')
        .map(t => ({
          label: `Array[${capitalize(t)}]`,
          code: `array<${t}>`
        }))
  },
  ...primitiveTypes.map(t => ({
    label: capitalize(t),
    code: t
  }))
]

function capitalize(str: string) {
  return str ? str.charAt(0).toUpperCase() + str.slice(1) : ''
}

const editType = (index: number, event?: MouseEvent) => {
  editingTypeIndex.value = index
  updatedType.value = properties.value[index].type
  typeEditTarget.value = event?.target as HTMLElement
  if (typeEditTarget.value) {
    typeEditPopover.value?.toggle(event, typeEditTarget.value)
  }
}

const updatePropertyType = () => {
  if (editingTypeIndex.value !== null && updatedType.value) {
    properties.value[editingTypeIndex.value].type = updatedType.value
  }
  typeEditPopover.value?.hide()
  editingTypeIndex.value = null
}

const editingNameIndex = ref<number | null>(null);
const editingNameError = ref<boolean>(false);

const startEditingName = (index: number) => {
  editingNameIndex.value = index
}

const finishEditingName = () => {
  if (editingNameIndex.value === null) return

  const name = properties.value[editingNameIndex.value].name.trim()

  if (!name) {
    editingNameError.value = true
    return
  }

  properties.value[editingNameIndex.value].name = name
  editingNameIndex.value = null
  editingNameError.value = false
}

const togglePopover = (event: MouseEvent) => {
  popover.value.toggle(event, addButton.value)
}

const errors = ref({
  name: '',
  type: ''
})

const addProperty = () => {
  errors.value = {name: '', type: ''}

  if (!newProperty.value.name.trim()) {
    errors.value.name = 'Name is required'
  }

  if (!newProperty.value.type) {
    errors.value.type = 'Type is required'
  }

  const hasErrors = Object.values(errors.value).some(Boolean)
  if (hasErrors) return

  properties.value.push({
    name: newProperty.value.name.trim(),
    type: newProperty.value.type || ''
  })
  popover.value.hide()
  newProperty.value = {name: '', type: ''}
}
</script>
