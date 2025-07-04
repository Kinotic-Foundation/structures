<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'

import ProjectList from '@/components/ProjectList.vue'
import StructuresList from '@/components/StructuresList.vue'
import GraphQLModal from '@/components/modals/GraphQLModal.vue'
import StructureItemModal from '@/components/modals/StructureItemModal.vue'
import { STRUCTURES_STATE } from '@/states/IStructuresState'
import Tabs from 'primevue/tabs'
import TabList from 'primevue/tablist'
import Tab from 'primevue/tab'
import TabPanels from 'primevue/tabpanels'
import TabPanel from 'primevue/tabpanel'

@Component({
    components: {
        ProjectList,
        StructuresList,
        GraphQLModal,
        StructureItemModal,
        Tabs,
        TabList,
        Tab,
        TabPanels,
        TabPanel
    }
})
export default class ApplicationDetails extends Vue {
    activeTab: number = 0

    tabs = [
        { title: 'Projects', value: 0 },
        { title: 'Structures', value: 1 }
    ]

    showGraphQLModal: boolean = false

    get applicationId(): string | string[] {
        return this.$route.params.applicationId
    }

    get isModalOpen(): boolean {
        return STRUCTURES_STATE.isModalOpen.value
    }

    get selectedStructure() {
        return STRUCTURES_STATE.selectedStructure.value
    }

    openGraphQL(): void {
        this.showGraphQLModal = true
    }

    closeGraphQL(): void {
        this.showGraphQLModal = false
    }

    closeModal(): void {
        STRUCTURES_STATE.closeModal()
    }
}
</script>

<template>
    <div class="p-10">
        <div class="flex justify-between items-center mb-6">
            <div>
                <h1 class="font-semibold text-2xl text-[#101010] mb-3">{{ applicationId }}</h1>
            </div>
            <div class="flex gap-3">
                <div
                    @click="openGraphQL"
                    class="border border-[#E6E7EB] rounded-xl flex items-center gap-2 py-3 px-8 cursor-pointer"
                >
                    <img src="@/assets/graphql.svg" class="w-6 h-6" />
                    <span class="text-sm font-semibold">GraphQL</span>
                </div>
                <div class="border border-[#E6E7EB] rounded-xl flex items-center gap-2 py-3 px-8">
                    <img src="@/assets/scalar.svg" class="w-6 h-6" />
                    <span class="text-sm font-semibold">OpenAPI</span>
                </div>
            </div>
        </div>

        <Tabs :activeIndex="activeTab" class="w-full">
            <TabList>
                <Tab
                    v-for="tab in tabs"
                    :key="tab.title"
                    :value="tab.value"
                >
                    {{ tab.title }}
                </Tab>
            </TabList>

            <TabPanels>
                <TabPanel
                    v-for="tab in tabs"
                    :key="tab.title"
                    :value="tab.value"
                >
                    <ProjectList
                        v-if="tab.value === 0"
                        :applicationId="applicationId"
                    />
                    <StructuresList
                        v-else-if="tab.value === 1"
                        :applicationId="applicationId"
                    />
                </TabPanel>
            </TabPanels>
        </Tabs>

        <GraphQLModal :visible="showGraphQLModal" @close="closeGraphQL" />
        <StructureItemModal
            v-if="isModalOpen"
            :item="selectedStructure"
            @close="closeModal"
        />
    </div>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
    cursor: pointer;
}
</style>
