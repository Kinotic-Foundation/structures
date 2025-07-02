<script lang="ts">
import CrudTable from '@/components/CrudTable.vue'
import GraphQLModal from '@/components/modals/GraphQLModal.vue'
import NewProjectSidebar from '@/components/NewProjectSidebar.vue'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { mdiApi, mdiGraphql } from '@mdi/js'
import { Component, Prop, Vue } from 'vue-facing-decorator'

@Component({
    components: { CrudTable, NewProjectSidebar, GraphQLModal }
})
export default class ApplicationDetails extends Vue {
    @Prop({ required: true }) applicationId!: string
    
    activeTab = 'projects'

    showGraphQLModal = false

    icons = { graph: mdiGraphql, api: mdiApi }

    get applicationName(): string {
        return APPLICATION_STATE.currentApplication?.id || 'Application'
    }

    get projectsCount() {
        return APPLICATION_STATE.projectsCount
    }

    get structuresCount() {
        return APPLICATION_STATE.structuresCount
    }

    async switchTab(tab: string): Promise<void> {
        this.activeTab = tab
    }

    openGraphQL(): void {
        this.showGraphQLModal = true
    }

    closeGraphQL(): void {
        this.showGraphQLModal = false
    }

}
</script>

<template>
    <div class="p-10">
        <div class="flex justify-between items-center mb-6">
            <div>
                <h1 class="font-semibold text-2xl text-[#101010] mb-3">{{ applicationName }}</h1>
                <p class="text-[#101010] text-sm">{{ projectsCount }} projects, {{ structuresCount }} structures</p>
            </div>
            <div class="flex gap-3">
                <div @click="openGraphQL" class="border border-[#E6E7EB] rounded-xl flex items-center gap-2 py-3 px-8 cursor-pointer">
                    <img src="@/assets/graphql.svg" class="w-6 h-6" />
                    <span class="text-sm font-semibold">GraphQL</span>
                </div>
                <div class="border border-[#E6E7EB] rounded-xl flex items-center gap-2 py-3 px-8">
                    <img src="@/assets/scalar.svg" class="w-6 h-6" />
                    <span class="text-sm font-semibold">OpenAPI</span>
                </div>
            </div>
        </div>

        <div class="flex gap-6 border-b border-[#E6E7EB] mb-6">
            <button @click="switchTab('projects')"
                :class="['text-sm font-semibold pb-3 border-b-2', activeTab === 'projects' ? 'border-[#3D5ACC] text-[#101010]' : 'border-transparent text-[#999CA0]']">
                Projects
            </button>
            <button @click="switchTab('structures')"
                :class="['text-sm font-semibold pb-3 border-b-2', activeTab === 'structures' ? 'border-[#3D5ACC] text-[#101010]' : 'border-transparent text-[#999CA0]']">
                Structures
            </button>
        </div>

        <ProjectList v-if="activeTab === 'projects'" :applicationId="applicationId" />

        <StructuresList v-else-if="activeTab === 'structures'" :applicationId="applicationId" />

        <GraphQLModal :visible="showGraphQLModal" @close="closeGraphQL" />

    </div>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
    cursor: pointer;
}
</style>
