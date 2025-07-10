<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import StructureItemModal from '@/components/modals/StructureItemModal.vue'
import type { Identifiable, IterablePage, Pageable } from '@kinotic/continuum-client'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { Structure, Structures } from '@kinotic/structures-api'
import type { CrudHeader } from '@/types/CrudHeader'

@Component({
    components: { CrudTable, StructureItemModal }
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

    showModal = false
    selectedStructure: Structure | null = null

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
                let result;
                if (this.projectId) {
                    result = await Structures.getStructureService().findAllForProject(this.projectId, pageable);
                } else {
                    result = await Structures.getStructureService().findAllForApplication(this.applicationId, pageable);
                }
                APPLICATION_STATE.structuresCount = result.totalElements ?? 0;
                return result;
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

    refreshTable(): void {
        const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
        tableRef?.find()
    }

    openModal(item: Structure) {
        this.selectedStructure = item
        this.showModal = true
    }

    closeModal() {
        this.showModal = false
        this.selectedStructure = null
    }

    handleRowClick(item: Structure): void {
        this.openModal(item)
    }

    onAddItem(): void {
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
            emptyStateText="No structures yet"
        >
            <template #item.id="{ item }">
                <span>{{ item.id }}</span>
            </template>
        </CrudTable>

        <StructureItemModal
            v-if="showModal && selectedStructure"
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
