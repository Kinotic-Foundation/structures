<template>
  <v-container class="no-gutters" fill-height fluid>
    <v-row class="no-gutters fill-height">
      <v-col>
        <CrudTable :data-source="dataSource"
                   :headers="headers"
                   @add-item="onAddItem"
                   @edit-item="onEditItem">
        </CrudTable>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { PropType } from 'vue'
import { Component, Prop, Vue } from 'vue-property-decorator'
import { DataTableHeader } from 'vuetify'
import {Identifiable, ICrudServiceProxy, Continuum} from '@kinotic/continuum-client'
import CrudTable from '@/frontends/continuum/components/CrudTable.vue'

/**
 * Provides a List page that can be used with the {@link CrudLayout}
 * to display a {@link CrudTable} when you do not need to override any columns
 */
// noinspection TypeScriptValidateTypes
@Component({
             components: { CrudTable }
           })
export default class BasicCrudList extends Vue {

  /**
   * Identifier of remote service that will be used to populate the table
   * The service must provide an interface compatible with the {@link ICrudServiceProxy}
   */
  @Prop({ type: String, required: true })
  public crudServiceIdentifier!: string

  /**
   * The {@link DataTableHeader}'s for all columns that should be rendered
   */
  @Prop({type: Array as PropType<DataTableHeader[]>, required: true})
  public headers!: DataTableHeader[]

  /**
   * Services
   */
  private dataSource!: ICrudServiceProxy<any>

  constructor() {
    super()
  }

  // Lifecycle hooks
  public created() {
    this.dataSource = Continuum.crudServiceProxy(this.crudServiceIdentifier)
  }

  public onAddItem() {
    this.$router.push(`${this.$route.path }/add`)
  }

  public onEditItem(identifiable: Identifiable<string>) {
    this.$router.push(`${this.$route.path}/edit/${identifiable.id}`)
  }

}
</script>

<style scoped>

</style>
