<template>
    <CrudEntityAddEdit
      :crud-service-identifier="crudServiceIdentifier"
      title="Namespace"
      :identity="id"
      :identityRules="namespaceRules"
      :entity.sync="namespace"
      update:entity="handleEntityUpdate"
    >
      <template #basic-info="{ entity }">
        <div class="mb-4">
          <label for="description" class="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <InputText
            id="description"
            v-model="entity.description"
            class="w-full p-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
            placeholder="Enter description"
          />
        </div>
      </template>
    </CrudEntityAddEdit>
  </template>
  
  <script lang="ts">
  import { Component, Prop, Vue } from 'vue-facing-decorator'
  import InputText from 'primevue/inputtext'
  import CrudEntityAddEdit from '@/components/CrudEntityAddEdit.vue'
  import { Namespace } from '@kinotic/structures-api'
  import { IndexNameHelper } from '@/util/IndexNameHelper'
  
  type RuleValidator = (value: any) => string | boolean
  
  @Component({
    components: {
      CrudEntityAddEdit,
      InputText,
    },
  })
  export default class NamespaceAddEdit extends Vue {
    @Prop({ type: String, required: false, default: null })
    public id!: string | null
  
    private crudServiceIdentifier: string = 'org.kinotic.structures.api.services.NamespaceService'
    private namespace: Namespace = new Namespace('', '', 0)
    private namespaceRules: RuleValidator[] = [
      (v) => !!v || 'Name is required',
      (v) => {
        const ret: string = IndexNameHelper.checkNameAndNamespace(v as string, 'Name')
        return ret.length === 0 ? true : ret
      },
    ]
  }
  </script>
