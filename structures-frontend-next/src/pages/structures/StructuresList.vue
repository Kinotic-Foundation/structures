<template>
    <div class="pt-4">

        <CrudTable title="Structures" subtitle="" :data-source="dataSource" :headers="headers" :singleExpand="false" ref="crudTable">
            <template #item.id="{ item }">
                <span>{{ item.id }}</span>
            </template>
            <template #additional-actions="{ item }">
                <Button @click="editItem" text class="!text-[#334155] !bg-white" :title="'OpenAPI'">
                    <!-- {{ item.id }} -->
                </Button>

                <Button text class="!text-[#334155] !bg-white" :title="'GraphQL'">

                </Button>
            </template>
            <template #item.description="{ item }">
                {{ item.description }}
            </template>
        </CrudTable>
    </div>
</template>

<script lang="ts">

import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import { Structures, type IStructureService } from '@kinotic/structures-api'
@Component({
    components: { CrudTable }
})
class StructuresList extends Vue {
    headers = [
        { field: 'id', header: 'Id', sortable: false },
        { field: 'namespace', header: 'Namespace', sortable: false },
        { field: 'name', header: 'Name', sortable: false },
        { field: 'description', header: 'Description', sortable: false },
        { field: 'created', header: 'Created', sortable: false },
        { field: 'updated', header: 'Last Updated', sortable: false },
        { field: 'published', header: 'Published', sortable: false },
        { field: 'publishedTimestamp', header: 'Published On', sortable: false },
    ]

    private dataSource: IStructureService = Structures.getStructureService()
    public async publish(item: any) {
        item['publishing'] = true
        if (confirm('Are you sure you want to Publish this Structure?')) {
            let table: any = this.$refs?.crudTable
            try {
                await this.dataSource.publish(item.item.id)
                table?.find()
                delete item['publishing']
            } catch (error: any) {
                delete item['publishing']
                console.error(error.stack, error)
                table?.displayAlert(error.message)
            }
        }
    }
    public editItem = () => {

    }
}

export default StructuresList
</script>