<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import StructureItemModal from '@/components/modals/StructureItemModal.vue'
import { Structure, Structures } from '@kinotic/structures-api'
import type { CrudHeader } from '@/types/CrudHeader'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { STRUCTURES_STATE } from '@/states/IStructuresState'
import type { Identifiable, IterablePage, Pageable } from '@kinotic/continuum-client'

@Component({
    components: { CrudTable, StructureItemModal }
})
export default class ProjectStructuresTable extends Vue {

    @Prop({ required: true }) applicationId!: string

    get projectId(): string {
        return this.$route.params.projectId as string
    }

    structureTableHeaders: CrudHeader[] = [
        { field: 'name', header: 'Structure Name', sortable: true },
        { field: 'description', header: 'Description', sortable: false },
        { field: 'created', header: 'Created', sortable: false },
        { field: 'updated', header: 'Updated', sortable: false },
        { field: 'published', header: 'Status', sortable: false }
    ]

    mounted() {
        this.refreshTable()
        this.markProjectAsActive()
    }

    @Watch('$route.params.projectId', { immediate: true })
    onProjectIdChange() {
        this.refreshTable()
        this.markProjectAsActive()
    }

    get dataSource() {
        return {
            findAll: async (pageable: Pageable): Promise<IterablePage<Structure>> => {
                const result = await Structures.getStructureService().findAllForProject(this.projectId, pageable)
                APPLICATION_STATE.structuresCount = result.totalElements ?? 0
                return result
            },
            search: async (searchText: string, pageable: Pageable): Promise<IterablePage<Structure>> => {
                const search = `projectId:${this.projectId} && ${searchText}`
                return Structures.getStructureService().search(search, pageable)
            }
        }
    }

    refreshTable() {
        const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
        tableRef?.find()
    }

    openModal(item: Structure) {
        STRUCTURES_STATE.openModal({
            id: item.id ?? '',
            name: item.name,
            description: item.description ?? ''
        })
    }

    closeModal() {
        STRUCTURES_STATE.closeModal()
    }

    onAddItem() {
        console.log('[ProjectStructuresTable] Add new structure to project:', this.projectId)
    }

    onEditItem(item: Identifiable<string>) {
        this.$router.push(`${this.$route.path}/edit/${item.id}`)
    }

    handleRowClick(item: Structure) {
        this.openModal(item)
    }

    get isModalOpen() {
        return STRUCTURES_STATE.isModalOpen.value
    }

    get selectedStructure() {
        return STRUCTURES_STATE.selectedStructure.value
    }

    /**
     * Marks the correct project as active in the header when landing directly on this page
     */
    async markProjectAsActive() {
        try {
            const header = this.$root!.$refs.header as any
            if (header && header.setActiveProjectById) {
                await header.setActiveProjectById(this.applicationId, this.projectId)
            }
        } catch (e) {
            console.error('[ProjectStructuresTable] Failed to mark active project:', e)
        }
    }
}
</script>

<template>
    <div>
        <CrudTable
            ref="crudTable"
            :data-source="dataSource"
            :headers="structureTableHeaders"
            :singleExpand="false"
            @add-item="onAddItem"
            @edit-item="onEditItem"
            @onRowClick="handleRowClick"
            createNewButtonText="New Structure"
            emptyStateText="No structures yet for this project"
        >
            <template #item.name="{ item }">
                <span class="font-semibold">{{ item.name }}</span>
            </template>
            <template #item.description="{ item }">
                <span>{{ item.description }}</span>
            </template>
        </CrudTable>

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
