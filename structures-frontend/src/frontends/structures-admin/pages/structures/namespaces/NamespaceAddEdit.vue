<template>
  <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                        title="Namespace"
                        :identity="id"
                        :identityRules="namespaceRules"
                        :entity.sync="namespace">

      <template #basic-info="{ entity }" >
        <v-text-field v-model="entity.description" label="Description"></v-text-field>
      </template>

  </crud-entity-add-edit>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import {Namespace} from '@kinotic/structures-api'
import {IndexNameHelper} from '@/frontends/structures-admin/pages/structures/util/IndexNameHelper'

type RuleValidator = (value: any) => string | boolean

@Component({
  components: { CrudEntityAddEdit }
})
export default class NamespaceAddEdit extends Vue {

  @Prop({type: String, required: false, default: null})
  public id!: string | null
  private crudServiceIdentifier: string = 'org.kinotic.structures.api.services.NamespaceService'
  private namespace: Namespace = new Namespace('', '', 0)
  private namespaceRules: RuleValidator[] = [
                                              (v) => {
                                                  return !!v || 'Name is required'
                                              },
                                              (v) => {
                                                let ret: string = IndexNameHelper.checkNameAndNamespace(v as string, 'Name')
                                                return ret.length === 0 ? true : ret
                                              }
                                            ]

  constructor() {
    super()
  }

}
</script>

<style scoped>

</style>
