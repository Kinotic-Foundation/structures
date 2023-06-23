<template>
  <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                        title="Structure"
                        :identity="id"
                        :identity-editable="false"
                        :entity.sync="structure"
                        @before-save="updateStructure"
                        :show-basic-info-subheader="false"
                        ref="crud" >

    <template #basic-info="{ entity }" >

      <v-tabs fixed-tabs style="width: 100%;">
        <v-tab key="standard" v-if="!complexType">
          Standard
        </v-tab>
        <v-tab-item key="standard" v-if="!complexType" >
          <structure-basic-info :entity="entity" key="standard" />
          <structure-standard-ui :entity="entity" @update="loadEntityDefinition(entity)" />
        </v-tab-item>
        <v-tab key="advanced">
          Advanced
        </v-tab>
        <v-tab-item key="advanced">
          <structure-basic-info :entity="entity" key="advanced" />
          <v-alert dense
                   type="error"
                   v-show="jsonSyntaxError.length > 0"
                   class="float-end mt-2">
            {{ jsonSyntaxError }}
          </v-alert>
          <div class="editor-width editor-height mt-16" >
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
} from '@kinotic/continuum-idl'
import {
  mdiFormatText,
  mdiBugCheck
} from '@mdi/js'
import {IStructureService, Structures} from "@/frontends/structures-admin/services";
import {PrismEditor} from "vue-prism-editor";
// import highlighting library
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-json';
import 'prismjs/themes/prism-tomorrow.css';
import StructureStandardUi from "@/frontends/structures-admin/pages/structures/structures/StructureStandardUi.vue";
import StructureBasicInfo from "@/frontends/structures-admin/pages/structures/structures/StructureBasicInfo.vue";


@Component({
  components: {StructureBasicInfo, CrudEntityAddEdit, StructureStandardUi, PrismEditor }
})
export default class StructureAddEdit extends Vue {

  @Prop({type: String, required: false, default: null})
  public id!: string | null

  /**
   * Data Vars
   */
  private crudServiceIdentifier: string = 'org.kinotic.structures.api.services.StructureService'
  // @ts-ignore
  private objectC3Type: ObjectC3Type = new ObjectC3Type()
                                            .addProperty('id', new IdC3Type())
                                            .addDecorator(new EntityDecorator().withMultiTenancyType(MultiTenancyType.SHARED))
  private structure: Structure = new Structure('', '', '', '', this.objectC3Type, 0, 0, false, 0, '', [])


  private structureService: IStructureService = Structures.getStructureService()

  private entityDefinition: string = ""
  private complexType: boolean = false
  private jsonSyntaxError: string = ''

  private icons = {
    format: mdiFormatText,
    validate: mdiBugCheck
  }

  constructor() {
    super()
  }

  public mounted() {
    if(this.id !== null){
      this.structureService.findById(this.id)
          .then((structure) => {
            this.loadEntityDefinition(structure)
          })
          .catch((error) => {
            console.error("Error setting up to add/edit Structure", error)
            this.displayAlert(error.message)
          })
    }else{
      this.loadEntityDefinition(this.structure)
    }
  }

  private highlighter(code: string) {
    return highlight(code, languages.json); // languages.<insert language> to return html with markup
  }

  private displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'crudEntityAddEditAlert', text })
  }

  private validateEntityDefinition(value: any) {
    try {
      // FIXME: need to ensure here that we have valid C3Type objects
      JSON.parse(value as string)
      this.jsonSyntaxError = ""
      return true
    }catch(error: any){
      this.jsonSyntaxError = error.message
      return `Format Failed\n ${error.message}`
    }
  }

  private updateStructure(structure: Structure){
    structure.entityDefinition = JSON.parse(this.entityDefinition)
    structure.entityDefinition.namespace = this.structure.namespace
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
  width: 100%;
}


</style>
