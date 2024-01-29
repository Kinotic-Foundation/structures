<template>
    <v-container class="no-gutters" fill-height fluid>
        <v-row class="no-gutters fill-height">
            <v-col>
                <CrudTable :data-source="dataSource"
                           :headers="headers"
                           :singleExpand="false"
                           @add-item="onAddItem"
                           @edit-item="onEditItem"
                           ref="crudTable">

                    <template v-slot:item.id="{ item }">
                        <span>{{ item.id }}</span>
                    </template>
                    <template v-slot:item.description="{ item }">
                        {{ item.description }}
                    </template>

                    <template #additional-actions="{ item }">
                        <v-btn icon
                               small
                               class="mr-2"
                               title="OpenAPI"
                               :href="'/scalar-ui.html?namespace='+item.item.id"
                               target="_blank">
                            <v-icon small>
                                {{ icons.api }}
                            </v-icon>
                        </v-btn>
                        <v-btn icon
                               small
                               class="mr-2"
                               title="GraphQL"
                               :href="'/gql-ui.html?namespace='+item.item.id"
                               target="_blank">
                            <v-icon small>
                                {{ icons.graph }}
                            </v-icon>
                        </v-btn>
                    </template>

                </CrudTable>
            </v-col>
        </v-row>
    </v-container>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator'
import {DataTableHeader} from 'vuetify'
import {Identifiable} from '@kinotic/continuum-client'
import CrudTable from '@/frontends/continuum/components/CrudTable.vue'
import {
    mdiGraphql,
    mdiApi
} from '@mdi/js'
import {Structures, INamespaceService} from '@kinotic/structures-api'
import DatetimeUtil from '@/frontends/structures-admin/pages/structures/util/DatetimeUtil'

/**
 * Provides a List page that can be used with the {@link CrudLayout}
 * to display a {@link CrudTable} when you do not need to override any columns
 */

@Component({
    computed: {
        DatetimeUtil() {
            return DatetimeUtil
        }
    },
    components: {CrudTable}
})
export default class NamespaceList extends Vue {

    private headers: DataTableHeader[] = [
        {text: 'Id', value: 'id', sortable: true},
        {text: 'Description', value: 'description', sortable: false}
    ]

    /**
     * Services
     */
    private dataSource: INamespaceService = Structures.getNamespaceService()
    private loading: { [key: string]: boolean } = {}

    private icons = {
        graph: mdiGraphql,
        api: mdiApi
    }

    constructor() {
        super()
    }


    public onAddItem() {
        this.$router.push(`${this.$route.path}/add`)
    }

    public onEditItem(identifiable: Identifiable<string>) {
        this.$router.push(`${this.$route.path}/edit/${identifiable.id}`)
    }

    public toOpenApiPage(namespace: string) {
        this.$router.push(`${this.$route.path}/openapi/${namespace}`)
    }
}
</script>

<style scoped>

</style>
