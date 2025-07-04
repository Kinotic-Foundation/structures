<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import NewProjectSidebar from '@/components/NewProjectSidebar.vue'
import type { IDataSource, Identifiable, IterablePage, Pageable } from '@kinotic/continuum-client'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { Project, Structures } from '@kinotic/structures-api'
import type { CrudHeader } from '@/types/CrudHeader'

@Component({
    components: { CrudTable, NewProjectSidebar }
})
export default class ProjectList extends Vue {
    @Prop({ required: true }) applicationId!: string

    showProjectSidebar = false

    projectTableHeaders: CrudHeader[] = [
        { field: 'name', header: 'Project name', sortable: true },
        { field: 'sourceOfTruth', header: 'Source of truth', sortable: true },
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
                APPLICATION_STATE.projectsCount = result.totalElements ?? 0; // ✅ update count
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

    async onProjectSubmit(createdProject: Project): Promise<void> {
        try {

            const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
            if (tableRef) {
                tableRef.items.unshift(createdProject)
                tableRef.totalItems += 1
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
        //this.$router.push(`/application/${encodeURIComponent(item.id)}`)
        this.showProjectSidebar = false
    }
}
</script>

<template>
    <div>
        <CrudTable
            v-if="dataSource"
            rowHoverColor=""
            :data-source="dataSource"
            :headers="projectTableHeaders"
            :singleExpand="false"
            @add-item="onAddProject"
            @edit-item="onEditItem"
            ref="crudTable"
            @onRowClick="toProjectPage"
            createNewButtonText="New project"
            :isShowAddNew="true"
            emptyStateText="No projects yet"
        >
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

        <NewProjectSidebar 
            :visible="showProjectSidebar" 
            @close="onProjectSidebarClose" 
            @submit="onProjectSubmit" 
        />
    </div>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
    cursor: pointer;
}
</style>
