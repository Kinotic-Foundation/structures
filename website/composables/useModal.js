import { reactive } from 'vue'

export const useModal = () => {
  const modalState = reactive({
    isOpen: false,
    title: '',
    content: ''
  })

  const openModal = (options = {}) => {
    modalState.isOpen = true
    modalState.title = options.title || 'Modal Title'
    modalState.content = options.content || 'Modal Content'
  }

  const closeModal = () => {
    modalState.isOpen = false
    modalState.title = ''
    modalState.content = ''
  }

  return {
    modalState,
    openModal,
    closeModal
  }
}