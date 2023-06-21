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

      <v-tabs fixed-tabs>
        <v-tab key="standard" v-if="!complexType">
          Standard
        </v-tab>
        <v-tab-item key="standard" v-if="!complexType" style="width: 100%;">
          <structure-standard-ui :entity="entity" @update="loadEntityDefinition(entity)" />
        </v-tab-item>
        <v-tab key="advanced">
          Advanced
        </v-tab>
        <v-tab-item key="advanced">
          <div class="pa-6 justify-center text-center" >

            <v-btn large tile @click="format" :disabled="validationColor !== 'green'" >
              <v-icon left>{{icons.format}}</v-icon>
              Format
            </v-btn>

            <v-icon medium
                    class="ma-2"
                    title="Validate"
                    :color="validationColor" >
              {{ icons.validate }}
            </v-icon>

            <v-alert dense
                     outlined
                     type="error"
                     style="margin: 0 auto; text-align: center; width: 50em;"
                     v-show="jsonSyntaxError.length > 0">
              {{ jsonSyntaxError }}
            </v-alert>
          </div>
          <div class="editor-width editor-height" style="margin: 0 auto; text-align: center" >
            <v-text-field v-model='entityDefinition' :rules="[ validateEntityDefinition ]" v-show="false"></v-text-field>
            <prism-editor class="my-editor" v-model="entityDefinition" :highlight="highlighter" line-numbers ></prism-editor>
          </div>
        </v-tab-item>
      </v-tabs>
    </template>

  </crud-entity-add-edit>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import {Structure} from '@/frontends/structures-admin/pages/structures/structures/Structure'
import {
  ArrayC3Type,
  C3Type,
  EntityDecorator,
  IdC3Type,
  MultiTenancyType,
  ObjectC3Type
} from '@kinotic/continuum-idl-js'
import {
  mdiFormatText,
  mdiBugCheck
} from '@mdi/js'
import {IndexNameHelper} from '@/frontends/structures-admin/pages/structures/util/IndexNameHelper'
import {Namespace} from '@/frontends/structures-admin/pages/structures/namespaces/Namespace'
import {Continuum, ICrudServiceProxy, Pageable} from '@kinotic/continuum'
import {IStructureService, Structures} from "@/frontends/structures-admin/services";
import {PrismEditor} from "vue-prism-editor";
// import highlighting library
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-json';
import 'prismjs/themes/prism-tomorrow.css';
import StructureStandardUi from "@/frontends/structures-admin/pages/structures/structures/StructureStandardUi.vue";

type RuleValidator = (value: any) => string | boolean

@Component({
  components: { CrudEntityAddEdit, StructureStandardUi, PrismEditor }
})
export default class StructureAddEdit extends Vue {

  @Prop({type: String, required: false, default: null})
  public id!: string | null

  /**
   * Data Vars
   */
  private crudServiceIdentifier: string = 'org.kinotic.structures.api.services.StructureService'
  private namespaceServiceIdentifier: string = 'org.kinotic.structures.api.services.NamespaceService'
  // @ts-ignore
  private objectC3Type: ObjectC3Type = new ObjectC3Type()
                                            .addProperty('id', new IdC3Type())
                                            .addDecorator(new EntityDecorator().withMultiTenancyType(MultiTenancyType.SHARED))
  private structure: Structure = new Structure('', '', '', '', this.objectC3Type, 0, 0, false, 0, '', [])
  private selectedNamespace: Namespace = new Namespace("", "", 0)
  private namespaceErrorMessage: string = ""
  private namespaces: Namespace[] = []
  private nameRules: RuleValidator[] = [
    (v) => {
      let ret: string = IndexNameHelper.checkNameAndNamespace(v as string, 'Name')
      return ret.length === 0 ? true : ret
    }
  ]

  private namespaceService: ICrudServiceProxy<Namespace> = Continuum.crudServiceProxy(this.namespaceServiceIdentifier)
  private structureService: IStructureService = Structures.getStructureService()

  private entityDefinition: string = ""
  private complexType: boolean = false

  private validationColor: string = 'green'
  private jsonSyntaxError: string = ''

  private icons = {
    format: mdiFormatText,
    validate: mdiBugCheck
  }

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
            this.loadEntityDefinition(structure)
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
            console.error("Error setting up to add/edit Structure", error)
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

  private highlighter(code: string) {
    return highlight(code, languages.json); // languages.<insert language> to return html with markup
  }

  private displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'crudEntityAddEditAlert', text })
  }

  private format(){
    this.entityDefinition = JSON.stringify(JSON.parse(this.entityDefinition), null, 2)
  }

  private validateEntityDefinition(value: any) {
    try {
      // FIXME: need to ensure here that we have valid C3Type objects
      JSON.parse(value as string)
      this.validationColor = "green"
      this.jsonSyntaxError = ""
      return true
    }catch(error: any){
      this.validationColor = "red"
      this.jsonSyntaxError = error.message
      return `Format Failed\n ${error.message}`
    }
  }

  private updateStructure(structure: Structure){
    structure.namespace = this.selectedNamespace.id
    structure.entityDefinition = JSON.parse(this.entityDefinition)
    structure.entityDefinition.namespace = this.selectedNamespace.id
    structure.entityDefinition.name = structure.name
  }

  private loadEntityDefinition(structure: any){
    Object.keys(structure.entityDefinition.properties).forEach((key) => {
      const value: C3Type = structure.entityDefinition.properties[key]
      if(value.type === "object"){
        this.complexType = true
      }else if(value.type === "array" && (value as ArrayC3Type).contains?.type === "object"){
        this.complexType = true
      }else if(value.type === "map"){
        this.complexType = true
      }else if(value.type === "ref"){
        this.complexType = true
      }else if(value.type === "union"){
        this.complexType = true
      }
    })
    this.entityDefinition = JSON.stringify(structure.entityDefinition, null, 2)
  }

}
</script>

<style scoped>

.my-editor {
  background: #2d2d2d;
  color: #ccc;

  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}
.prism-editor__textarea:focus {
  outline: none;
}
.editor-height {
  height: 40em;
  overflow-y: scroll;
}
.editor-width {
  width: 50em;
  overflow-x: scroll;
}


</style>