import { ref, computed } from 'vue'
export interface Structure {
  id: string
  name: string
  description?: string
}

class StructuresState {
  private _modalVisible = ref(false)
  private _selectedStructure = ref<Structure | null>(null)

  readonly selectedStructure = computed(() => this._selectedStructure.value)
  readonly isModalOpen = computed(() => this._modalVisible.value && !!this._selectedStructure.value)

  openModal(item: Structure): void {
    this._selectedStructure.value = item
    this._modalVisible.value = true
  }

  closeModal(): void {
    this._selectedStructure.value = null
    this._modalVisible.value = false
  }
}

export const STRUCTURES_STATE = new StructuresState()
