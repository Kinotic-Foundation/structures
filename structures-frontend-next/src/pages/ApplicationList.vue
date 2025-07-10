<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import ContainerMedium from '@/components/ContainerMedium.vue'
import ApplicationSidebar from '@/components/ApplicationSidebar.vue'
import { type Identifiable } from '@kinotic/continuum-client'
import { mdiGraphql, mdiApi } from '@mdi/js'
import GraphQLModal from '@/components/modals/GraphQLModal.vue'
import { Structures, type IApplicationService } from '@kinotic/structures-api'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import type { CrudHeader } from '@/types/CrudHeader'
@Component({
    components: {
        CrudTable,
        ContainerMedium,
        ApplicationSidebar,
        GraphQLModal
    }
})
export default class NamespaceList extends Vue {
    headers: CrudHeader[] = [
        { field: 'id', header: 'Id', sortable: false },
        { field: 'description', header: 'Description', sortable: false },
        { field: 'created', header: 'Created', sortable: false },
        { field: 'updated', header: 'Updated', sortable: false }
    ]

    dataSource: IApplicationService = Structures.getApplicationService()
    icons = { graph: mdiGraphql, api: mdiApi }
    showGraphQLModal = false
    showSidebar = false

    openGraphQL(): void {
        this.showGraphQLModal = true
    }

    closeGraphQL(): void {
        this.showGraphQLModal = false
    }

    async mounted(): Promise<void> {
        try {
            this.refreshTable()
            if (this.$route.query.created === 'true') {
                this.$router.replace({ query: {} })
            }
        } catch (error) {
            const message = error instanceof Error ? error.message : 'Unknown error'
            console.error('[NamespaceList] Initialization error:', message)
        }
    }

    private refreshTable(): void {
        const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
        tableRef?.find()
    }

    onAddItem(): void {
        this.showSidebar = true
    }

async toApplicationPage(item: Identifiable<string>): Promise<void> {
    try {
        const appId = item.id ?? ''
        const app = await this.dataSource.findById(appId)
        APPLICATION_STATE.currentApplication = app
        this.$router.push(`/application/${encodeURIComponent(appId)}`)
    } catch (e) {
        console.error('[NamespaceList] Failed to navigate to application:', e)
    }
}


    onSidebarClose(): void {
        this.showSidebar = false
    }

    onApplicationSubmit(): void {
        const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
        if (tableRef) {
            tableRef.find()
        }
        this.showSidebar = false
    }

    onEditItem(item: Identifiable<string>): void {
        this.$router.push(`${this.$route.path}/edit/${item.id}`)
    }
}
</script>

<template>
    <ContainerMedium>
        <h1 class="text-2xl font-semibold mb-5 text-[color:var(--surface-950)]">Applications</h1>
        <CrudTable
            createNewButtonText="New application"
            rowHoverColor=""
            :data-source="dataSource"
            :headers="headers"
            :singleExpand="false"
            @add-item="onAddItem"
            @edit-item="onEditItem"
            ref="crudTable"
            @onRowClick="toApplicationPage"
            :enableViewSwitcher="true"
            emptyStateText="No applications yet"
        >
            <template #item.id="{ item }">
                <span>{{ item.id }}</span>
            </template>

            <template #additional-actions="{ item }">
                <Button
                    v-if="item.enableGraphQL"
                    text
                    title="GraphQL"
                    @click="openGraphQL"
                >
                    <img src="@/assets/graphql.svg" />
                </Button>

                <Button
                    v-if="item.enableOpenAPI"
                    text
                    title="OpenAPI"
                >
                    <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
                        <img src="@/assets/scalar.svg" />
                    </RouterLink>
                </Button>
            </template>
        </CrudTable>

        <GraphQLModal :visible="showGraphQLModal" @close="closeGraphQL" />
        <ApplicationSidebar :visible="showSidebar" @close="onSidebarClose" @submit="onApplicationSubmit" />
    </ContainerMedium>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
    cursor: pointer;
}
</style>
