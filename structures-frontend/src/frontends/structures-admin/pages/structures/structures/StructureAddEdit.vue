<template>
  <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                        title="Namespace"
                        :identity="id"
                        :entity.sync="namespace">

    <template #basic-info="{ entity }" >
      <v-text-field v-model="entity.id" label="Name"></v-text-field>
    </template>
    <template #basic-info="{ entity }" >
      <v-text-field v-model="entity.description" label="Description"></v-text-field>
    </template>

  </crud-entity-add-edit>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import {Namespace} from "@/frontends/structures-admin/pages/structures/namespaces/Namespace";

// noinspection TypeScriptValidateTypes
@Component({
  components: { CrudEntityAddEdit }
})
export default class NamespaceAddEdit extends Vue {

  @Prop({type: String, required: false, default: null})
  public id!: string | null

  /**
   * Data Vars
   */
  private crudServiceIdentifier: string = 'org.kinotic.structures.api.services.StructureService'
  private namespace: Namespace = new Namespace('', '', 0)

  constructor() {
    super()
  }

}
</script>

<style scoped>

</style>