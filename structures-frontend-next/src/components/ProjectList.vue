<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import NewProjectSidebar from '@/components/NewProjectSidebar.vue'
import ProjectStructuresTable from '@/components/ProjectStructuresTable.vue'
import type { IDataSource, Identifiable, IterablePage, Pageable } from '@kinotic/continuum-client'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { Project, Structures } from '@kinotic/structures-api'
import type { CrudHeader } from '@/types/CrudHeader'

@Component({
    components: { CrudTable, NewProjectSidebar, ProjectStructuresTable }
})
export default class ProjectList extends Vue {
    @Prop({ required: true }) applicationId!: string

    showProjectSidebar = false
    selectedProjectId: string | null = null

    projectTableHeaders: CrudHeader[] = [
        { field: 'name', header: 'Project Name', sortable: true },
        { field: 'sourceOfTruth', header: 'Source of Truth', sortable: true },
        { field: 'description', header: 'Description', sortable: false },
        { field: 'updated', header: 'Updated', sortable: false }
    ]

    @Watch('applicationId', { immediate: true })
    onApplicationIdChange(): void {
        this.refreshTable()
    }

    get dataSource(): IDataSource<Project> {
        return {
            findAll: async (pageable: Pageable): Promise<IterablePage<Project>> => {
                const result = await Structures.getProjectService().findAllForApplication(this.applicationId, pageable);
                APPLICATION_STATE.projectsCount = result.totalElements ?? 0
                return result;
            },
            search: async (searchText: string, pageable: Pageable): Promise<IterablePage<Project>> => {
                const search = `applicationId:${this.applicationId} && ${searchText}`
                return Structures.getProjectService().search(search, pageable)
            }
        }
    }

    get projectsCount() {
        return APPLICATION_STATE.projectsCount
    }

    refreshTable(): void {
        const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
        tableRef?.find()
    }

    onAddProject(): void {
        this.showProjectSidebar = true
    }

    onProjectSidebarClose(): void {
        this.showProjectSidebar = false
    }

    async onProjectSubmit(): Promise<void> {
        try {
            const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable>
            if (tableRef) {
                tableRef.find()
            }
        } catch (error) {
            console.error('[ProjectList] Refresh after project creation failed:', error)
            this.$toast.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Failed to refresh project list.',
                life: 3000
            })
        } finally {
            this.showProjectSidebar = false
        }
    }


    onEditItem(item: Identifiable<string>): void {
        this.$router.push(`${this.$route.path}/edit/${item.id}`)
    }
    toProjectPage(item: Identifiable<string>): void {
        if (!item.id) {
            console.error('[ProjectList] Cannot navigate: item.id is null')
            return
        }
        const appId = this.applicationId
        const projectId = item.id
        this.$router.push(`/application/${encodeURIComponent(appId)}/project/${encodeURIComponent(projectId)}/structures`)
    }
    clearSelectedProject() {
        this.selectedProjectId = null
    }
}
</script>
<template>
    <div>
        <CrudTable v-if="dataSource && !selectedProjectId" rowHoverColor="" :data-source="dataSource"
            :headers="projectTableHeaders" :singleExpand="false" @add-item="onAddProject" @edit-item="onEditItem"
            ref="crudTable" @onRowClick="toProjectPage" createNewButtonText="New Project" :isShowAddNew="true"
            emptyStateText="No projects yet">
            <template #item.id="{ item }">
                <span>{{ item.id }}</span>
            </template>
            <template #additional-actions="{ item }">
                <Button text class="!text-[#334155] !bg-white" title="GraphQL">
                    <RouterLink :to="{ path: '/graphql', query: { namespace: item.id } }">
                        <img src="@/assets/graphql.svg" />
                    </RouterLink>
                </Button>
                <Button text class="!text-[#334155] !bg-white" title="OpenAPI">
                    <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
                        <img src="@/assets/scalar.svg" />
                    </RouterLink>
                </Button>
            </template>
        </CrudTable>

        <div v-if="selectedProjectId" class="mt-6">
            <div class="flex items-center justify-between mb-4">
                <h2 class="text-xl font-semibold text-[#101010]">Structures for Project: {{ selectedProjectId }}</h2>
                <Button label="Back to Projects" icon="pi pi-arrow-left" @click="clearSelectedProject" />
            </div>
            <ProjectStructuresTable :projectId="selectedProjectId" />
        </div>

        <NewProjectSidebar :visible="showProjectSidebar" @close="onProjectSidebarClose" @submit="onProjectSubmit" />
    </div>
</template>
