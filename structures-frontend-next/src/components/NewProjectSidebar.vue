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
                    <button type="submit" :disabled="loading"
                        class="px-[10px] py-[7px] bg-[#3651ED]/70 text-white rounded-lg text-sm flex items-center gap-2">
                        <svg v-if="loading" class="animate-spin h-4 w-4 text-white" fill="none" viewBox="0 0 24 24">
                            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4">
                            </circle>
                            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z"></path>
                        </svg>
                        Create project
                    </button>
                </div>
            </form>
        </div>
    </transition>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-facing-decorator'
import { Structures, Project, ProjectType } from '@kinotic/structures-api'
import { APPLICATION_STATE } from '@/states/IApplicationState'

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

    loading = false

    async handleSubmit(): Promise<void> {
        this.loading = true
        try {
            const app = APPLICATION_STATE.currentApplication
            if (!app) {
                throw new Error('No current application selected')
            }

            const project = new Project(
                null,
                app.id,
                this.form.name,
                this.form.description
            )

            if (this.form.source === 'GUI') {
                project.sourceOfTruth = ProjectType.GUI
            } else if (this.form.source === 'Code') {
                switch (this.form.language) {
                    case 'typescript':
                        project.sourceOfTruth = ProjectType.TYPESCRIPT
                        break
                    case 'javascript':
                        project.sourceOfTruth = ProjectType.JAVASCRIPT
                        break
                    case 'python':
                        project.sourceOfTruth = ProjectType.PYTHON
                        break
                    default:
                        throw new Error('Please select a language for Code projects.')
                }
            }

            const createdProject = await Structures.getProjectService().create(project)

            this.$toast?.success?.('Project created successfully.')
            this.resetForm()
            this.$emit('submit', createdProject) // 🚀 Emit the created project back
        } catch (error) {
            console.error('[NewProjectSidebar] Failed to create project:', error)
            this.$toast?.error?.('Failed to create project.')
        } finally {
            this.loading = false
        }
    }

    handleClose(): void {
        this.resetForm()
        this.$emit('close')
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
