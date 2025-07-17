<script lang="ts">
import { Component, Vue, Watch } from 'vue-facing-decorator'
import ProjectList from '@/components/ProjectList.vue'
import StructuresList from '@/components/StructuresList.vue'
import GraphQLModal from '@/components/modals/GraphQLModal.vue'
import StructureItemModal from '@/components/modals/StructureItemModal.vue'
import Tabs from 'primevue/tabs'
import TabList from 'primevue/tablist'
import Tab from 'primevue/tab'
import TabPanels from 'primevue/tabpanels'
import TabPanel from 'primevue/tabpanel'
import { APPLICATION_STATE } from '@/states/IApplicationState'

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

    get projectsCount(): number {
        return APPLICATION_STATE.projectsCount ?? 0
    }

    get structuresCount(): number {
        return APPLICATION_STATE.structuresCount ?? 0
    }

    openGraphQL(): void {
        this.showGraphQLModal = true
    }

    closeGraphQL(): void {
        this.showGraphQLModal = false
    }

    @Watch('activeTab')
    onActiveTabChange(newTab: number) {
        console.log('Active tab changed:', newTab)
    }
}
</script>

<template>
    <div class="p-10">
        <div class="flex justify-between items-center mb-6">
            <div>
                <h1 class="font-semibold text-2xl text-surface-950 mb-3">{{ applicationId }}</h1>
                <span>{{ projectsCount }} projects, {{ structuresCount }} structures</span>
            </div>
            <div class="flex gap-3">
                <div @click="openGraphQL"
                    class="border border-surface-200 rounded-xl flex items-center gap-2 py-3 px-8 cursor-pointer">
                    <img src="@/assets/graphql.svg" class="w-6 h-6" />
                    <span class="text-sm font-semibold">GraphQL</span>
                </div>
                <div class="border border-surface-200 rounded-xl flex items-center gap-2 py-3 px-8 cursor-pointer">
                    <img src="@/assets/scalar.svg" class="w-6 h-6" />
                    <span class="text-sm font-semibold">OpenAPI</span>
                </div>
            </div>
        </div>
        <Tabs value="0">
            <TabList>
                <Tab value="0">Projects</Tab>
                <Tab value="1">Structures</Tab>
            </TabList>
            <TabPanels>
                <TabPanel value="0">
                    <ProjectList :applicationId="applicationId" />
                </TabPanel>
                <TabPanel value="1">
                    <StructuresList :applicationId="applicationId" />
                </TabPanel>
            </TabPanels>
        </Tabs>
        <GraphQLModal :visible="showGraphQLModal" @close="closeGraphQL" />
    </div>
</template>
