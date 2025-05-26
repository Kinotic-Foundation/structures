// src/states/IStructuresState.ts
import { ref, computed } from 'vue'

class StructuresState {
  private _modalVisible = ref(false)
  private _selectedStructure = ref<any | null>(null)

  readonly selectedStructure = computed(() => this._selectedStructure.value)
  readonly isModalOpen = computed(() => this._modalVisible.value && !!this._selectedStructure.value)

  openModal(item: any): void {
    this._selectedStructure.value = item
    this._modalVisible.value = true
  }

  closeModal(): void {
    this._selectedStructure.value = null
    this._modalVisible.value = false
  }
}

export const STRUCTURES_STATE = new StructuresState()
