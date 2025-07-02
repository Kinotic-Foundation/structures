<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import type { Identifiable, IterablePage, Pageable } from '@kinotic/continuum-client'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { Structure, Structures } from '@kinotic/structures-api'
import { STRUCTURES_STATE } from '@/states/IStructuresState'
import type { CrudHeader } from '@/types/CrudHeader'

@Component({
    components: { CrudTable }
})
export default class StructuresList extends Vue {
    @Prop({ required: true }) applicationId!: string
    @Prop({ required: false, default: undefined }) projectId?: string

    structureTableHeaders: CrudHeader[] = [
        { field: 'name', header: 'Structure name', sortable: true },
        { field: 'projectId', header: 'Project', sortable: true },
        { field: 'description', header: 'Description', sortable: false },
        { field: 'created', header: 'Created', sortable: false },
        { field: 'updated', header: 'Updated', sortable: false },
        { field: 'published', header: 'Status', sortable: false }
    ]

    @Watch('applicationId', { immediate: true })
    onApplicationIdChange(): void {
        this.refreshTable()
    }

    @Watch('projectId', { immediate: true })
    onProjectIdChange(): void {
        this.refreshTable()
    }

    get dataSource() {
        return {
            findAll: async (pageable: Pageable): Promise<IterablePage<Structure>> => {
                if (this.projectId) {
                    return Structures.getStructureService().findAllForProject(this.projectId, pageable)
                }else{
                    return Structures.getStructureService().findAllForApplication(this.applicationId, pageable)
                }    
            },
            search: async (searchText: string, pageable: Pageable): Promise<IterablePage<Structure>> => {
                const search = `${this.projectId ? `projectId:${this.projectId}` : `applicationId:${this.applicationId}`} && ${searchText}`
                return Structures.getStructureService().search(search, pageable)
            }
        }
    }

    get structuresCount() {
        return APPLICATION_STATE.structuresCount
    }

    get isModalOpen() {
        return STRUCTURES_STATE.isModalOpen.value
    }

    get selectedStructure() {
        return STRUCTURES_STATE.selectedStructure.value
    }

    refreshTable(): void {
        const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
        tableRef?.find()
    }

    openModal(item: any) {
        STRUCTURES_STATE.openModal(item)
    }

    closeModal() {
        STRUCTURES_STATE.closeModal()
    }

    handleRowClick(item: any): void {
        this.openModal(item)
    }

    onAddItem(): void {
        // Handle adding new structure
        console.log('[StructuresList] Add new structure')
    }

    onEditItem(item: Identifiable<string>): void {
        this.$router.push(`${this.$route.path}/edit/${item.id}`)
    }
}
</script>

<template>
    <div>
        <CrudTable
            v-if="dataSource"
            rowHoverColor=""
            :data-source="dataSource"
            :headers="structureTableHeaders"
            :singleExpand="false"
            @add-item="onAddItem"
            @edit-item="onEditItem"
            ref="crudTable"
            @onRowClick="handleRowClick"
            createNewButtonText="New Structure"
        >
            <template #item.id="{ item }">
                <span>{{ item.id }}</span>
            </template>
            <!-- <template #additional-actions="{ item }">
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
            </template> -->
        </CrudTable>

        <StructureItemModal v-if="isModalOpen" :item="selectedStructure" @close="closeModal" />
    </div>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
    cursor: pointer;
}
</style> 