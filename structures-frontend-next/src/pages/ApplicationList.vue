<script lang="ts">
import { Component, Vue, Ref, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import ContainerMedium from '@/components/ContainerMedium.vue'
import ApplicationSidebar from '@/components/ApplicationSidebar.vue'
import GraphQLModal from '@/components/modals/GraphQLModal.vue'
import { Structures, type IApplicationService } from '@kinotic/structures-api'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { mdiGraphql, mdiApi } from '@mdi/js'
import { onClickOutside } from '@vueuse/core'
import type { CrudHeader } from '@/types/CrudHeader'
import type { Identifiable } from '@kinotic/continuum-client'
import { shallowRef } from 'vue'

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
    searchText: string = this?.$route?.query.search as string || ''

    @Ref('sidebarWrapper') sidebarWrapper!: HTMLElement
    @Ref('crudTable') crudTable!: InstanceType<typeof CrudTable>

    async mounted(): Promise<void> {
        try {
            this.refreshTable()
            const el = shallowRef<HTMLElement | null>(this.sidebarWrapper)

            onClickOutside(el, () => {
                if (this.showSidebar) {
                    this.onSidebarClose()
                }
            })

            if (this?.$route?.query.created === 'true') {
                this.$router.replace({ query: {} })
            }
        } catch (error) {
            const message = error instanceof Error ? error.message : 'Unknown error'
            console.error('[NamespaceList] Initialization error:', message)
        }
    }

    @Watch('$route.query.search')
    onRouteSearchQueryChanged(newVal: string) {
        this.searchText = newVal || ''
    }

    private refreshTable(): void {
        this.crudTable?.find()
    }

    updateRouteQuery(search: string) {
        const query = { ...this?.$route?.query }

        if (search) {
            query.search = search
        } else {
            delete query.search
        }

        this.$router.replace({ query })
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
        this.refreshTable()
        this.showSidebar = false
    }

    onEditItem(item: Identifiable<string>): void {
        this.$router.push(`${this.$route.path}/edit/${item.id}`)
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
    <ContainerMedium>
        <h1 class="text-2xl font-semibold mb-5 text-surface-950">Applications</h1>

        <CrudTable
            ref="crudTable"
            createNewButtonText="New application"
            rowHoverColor=""
            :data-source="dataSource"
            :headers="headers"
            :singleExpand="false"
            :enableViewSwitcher="true"
            emptyStateText="No applications yet"
            :search="searchText"
            @update:search="updateRouteQuery"
            @add-item="onAddItem"
            @edit-item="onEditItem"
            @onRowClick="toApplicationPage"
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

        <div v-show="showSidebar" ref="sidebarWrapper">
            <ApplicationSidebar
                :visible="showSidebar"
                @close="onSidebarClose"
                @submit="onApplicationSubmit"
            />
        </div>
    </ContainerMedium>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
    cursor: pointer;
}
</style>
