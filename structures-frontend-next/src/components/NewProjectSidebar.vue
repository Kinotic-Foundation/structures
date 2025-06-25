<template>
    <transition name="slide">
        <div v-if="visible" class="fixed inset-y-0 right-0 w-[400px] h-screen bg-white shadow-xl z-50 overflow-y-auto">
            <div class="flex justify-between items-center border-b border-[#E6E7EB] p-4">
                <div class="flex items-center gap-3">
                    <img src="@/assets/action-plus-icon.svg" />
                    <h2 class="text-lg font-semibold text-[#101010]">New project</h2>
                </div>
                <button @click="handleClose" class="w-[11px] h-[11px] cursor-pointer">
                    <img src="@/assets/close-icon.svg" />
                </button>
            </div>
            <form @submit.prevent="handleSubmit" class="flex flex-col justify-between h-[calc(100vh-100px)] p-4">
                <div class="flex flex-col gap-5">
                    <div>
                        <label class="block text-sm font-semibold text-[#101010] mb-2">Name</label>
                        <input v-model="form.name" type="text" class="w-full p-2 border border-[#D2D3D9] rounded-lg"
                            placeholder="Project name" required />
                    </div>
                    <div>
                        <label class="block text-sm font-semibold text-[#101010] mb-2">Description</label>
                        <textarea v-model="form.description" class="w-full p-2 border border-[#D2D3D9] rounded-lg"
                            rows="3" placeholder="Optional description" />
                    </div>

                    <div>
                        <label class="block text-sm font-semibold text-[#101010] mb-2">Source of truth</label>

                        <div class="p-1 bg-[#F4F5F9] rounded-xl w-full flex gap-2">
                            <button type="button" @click="form.source = 'GUI'"
                                class="w-1/2 text-sm font-bold py-[10px] rounded-lg transition" :class="form.source === 'GUI'
                                    ? 'bg-white text-[#101010]'
                                    : 'bg-transparent text-[#5F6165]'">
                                GUI
                            </button>
                            <button type="button" @click="form.source = 'Code'"
                                class="w-1/2 text-sm font-bold py-[10px] rounded-lg transition" :class="form.source === 'Code'
                                    ? 'bg-white text-[#101010]'
                                    : 'bg-transparent text-[#5F6165]'">
                                Code
                            </button>
                        </div>
                    </div>

                    <div v-if="form.source === 'Code'">
                        <label class="block text-sm font-semibold text-[#101010] mb-2">Language</label>
                        <select v-model="form.language"
                            class="w-full p-2 border border-[#D2D3D9] rounded-lg text-sm text-[#101010]">
                            <option value="" disabled>Select language</option>
                            <option value="typescript">TypeScript</option>
                            <option value="javascript">JavaScript</option>
                            <option value="python">Python</option>
                        </select>
                    </div>
                </div>

                <div class="flex justify-end gap-2 mt-6">
                    <button type="button" @click="handleClose"
                        class="px-[10px] py-[7px] rounded-lg text-sm bg-[#F0F1F5] text-[#4F5159]">
                        Cancel
                    </button>
                    <button type="submit" class="px-[10px] py-[7px] bg-[#3651ED]/70 text-white rounded-lg text-sm">
                        Create project
                    </button>
                </div>
            </form>
        </div>
    </transition>
</template>

<script lang="ts">
import { Component, Vue, Prop, Emit } from 'vue-facing-decorator'

interface ProjectForm {
    name: string
    description: string
    source: 'GUI' | 'Code'
    language: string
}

@Component
export default class NewProjectSidebar extends Vue {
    @Prop({ required: true }) readonly visible!: boolean

    form: ProjectForm = {
        name: '',
        description: '',
        source: 'GUI',
        language: ''
    }

    @Emit('close')
    handleClose(): void {
        this.resetForm()
    }

    @Emit('submit')
    handleSubmit(): ProjectForm {
        const data = { ...this.form }
        this.resetForm()
        return data
    }

    private resetForm(): void {
        this.form = {
            name: '',
            description: '',
            source: 'GUI',
            language: ''
        }
    }
}
</script>

<style scoped>
.slide-enter-active,
.slide-leave-active {
    transition: transform 0.3s ease;
}

.slide-enter-from {
    transform: translateX(100%);
}

.slide-enter-to {
    transform: translateX(0%);
}

.slide-leave-from {
    transform: translateX(0%);
}

.slide-leave-to {
    transform: translateX(100%);
}
</style>
