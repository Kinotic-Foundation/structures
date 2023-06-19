<template>
  <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                        title="Structure"
                        :identity="id"
                        :identity-editable="false"
                        :entity.sync="structure"
                        @before-save="updateStructure"
                        ref="crud" >

    <template #basic-info="{ entity }" >

      <v-text-field v-model='entity.name' label='Name' :rules="nameRules" ></v-text-field>

      <v-text-field v-model='entity.description' label='Description'></v-text-field>

      <v-select
          label="Namespace"
          v-model="selectedNamespace"
          :items="namespaces"
          :disabled="entity.published"
          :hint="selectedNamespace.id + (selectedNamespace.description ? ' | ' + selectedNamespace.description : '')"
          :error-messages="namespaceErrorMessage"
          @select="entity.namespace = selectedNamespace.id"
          item-text="id"
          return-object
          persistent-hint
          hide-selected
          single-line >
      </v-select>

<!--      if we have an array of objects or an object we need to switch to advanced editor here-->

<!--      we could separate the primitives from the complex types.  we can then merge then when-->
<!--      we want to save.-->

<!--      we can add complex types using a text editor, we can give them the option to specify the name and then-->
<!--      give them an editor to provide the C3Object, we could give them an empty one to start-->

<!--      Id - string specialization via Id decorator type-->
<!--      Array - NESTED Type via decorator - if contains an object it is a complex type -->
<!--      Boolean-->
<!--      Byte-->
<!--      Char-->
<!--      Date-->
<!--      Double-->
<!--      Enum - leave out for now? -->
<!--      Float-->
<!--      Int-->
<!--      Long-->
<!--      Map - leave out for now? -->
<!--      Object - complex type-->
<!--      Reference - leave out for now? -->
<!--      Short-->
<!--      String - decorate for TEXT operations-->
<!--      Union - complex type -->
<!--      Void - only useful in functions. -->

<!--      User metadata to add a description field -->


    </template>

  </crud-entity-add-edit>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import {Structure} from '@/frontends/structures-admin/pages/structures/structures/Structure'
import {BooleanC3Type, EntityDecorator, IdC3Type, MultiTenancyType, ObjectC3Type} from '@kinotic/continuum-idl-js'
import {IndexNameHelper} from '@/frontends/structures-admin/pages/structures/util/IndexNameHelper'
import {Namespace} from '@/frontends/structures-admin/pages/structures/namespaces/Namespace'
import {Continuum, ICrudServiceProxy, Pageable} from '@kinotic/continuum'
import {IStructureService, StructureService} from "@/frontends/structures-admin/services";

type RuleValidator = (value: any) => string | boolean

@Component({
  components: {CrudEntityAddEdit }
})
export default class StructureAddEdit extends Vue {

  @Prop({type: String, required: false, default: null})
  public id!: string | null

  /**
   * Data Vars
   */
  private crudServiceIdentifier: string = 'org.kinotic.structures.api.services.StructureService'
  private namespaceServiceIdentifier: string = 'org.kinotic.structures.api.services.NamespaceService'
  private structure: Structure = new Structure('', '', '', '', new ObjectC3Type().addProperty('id', new IdC3Type()), 0, 0, false, 0, '', [])
  public selectedNamespace: Namespace = new Namespace("", "", 0)
  public namespaceErrorMessage: string = ""
  public namespaces: Namespace[] = []
  private nameRules: RuleValidator[] = [
    (v) => {
      return !!v || 'Name is required'
    },
    (v) => {
      let ret: string = IndexNameHelper.checkNameAndNamespace(v as string, 'Name')
      return ret.length === 0 ? true : ret
    }
  ]
  private namespaceService: ICrudServiceProxy<Namespace> = Continuum.crudServiceProxy(this.namespaceServiceIdentifier)
  private structureService: IStructureService = new StructureService(Continuum.serviceProxy('org.kinotic.structures.api.services.StructureService'))


  constructor() {
    super()
  }

  public mounted() {
    const pageable: Pageable = {
      pageNumber: 0,
      pageSize: 100,
      sort: null
    }

    if(this.id !== null){
      this.structureService.findById(this.id)
          .then((structure) => {
            return this.namespaceService.findById(structure.namespace)
          })
          .then((namespace) => {
            this.selectedNamespace = namespace
            return this.namespaceService.findAll(pageable)
          })
          .then((response) => {
            this.namespaces = response.content
          })
          .catch((error) => {
            console.error("Error setting up to add/edit Structure")
            this.displayAlert(error.message)
          })
    } else {
      this.namespaceService.findAll(pageable)
          .then((response) => {
            this.namespaces = response.content
          })
          .catch((error) => {
            console.error("Error setting up to add/edit Structure")
            this.displayAlert(error.message)
          })
    }

  }

  public updateStructure(structure: Structure){
    structure.namespace = this.selectedNamespace.id
    let objectC3Type: ObjectC3Type = new ObjectC3Type()
    objectC3Type = objectC3Type.addProperty('id', new IdC3Type())
    objectC3Type = objectC3Type.addProperty('isValid', new BooleanC3Type())
    structure.entityDefinition = objectC3Type
    structure.entityDefinition.namespace = this.selectedNamespace.id
    structure.entityDefinition.name = structure.name
    let entityDecorator: EntityDecorator = new EntityDecorator()
    entityDecorator.multiTenancyType = MultiTenancyType.SHARED
    structure.entityDefinition.addDecorator(entityDecorator)
  }

  private displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'crudEntityAddEditAlert', text })
  }

}
</script>

<style scoped>

</style>