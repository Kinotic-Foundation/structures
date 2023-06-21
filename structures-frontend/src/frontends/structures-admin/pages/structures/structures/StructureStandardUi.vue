<template>
  <v-container class="pa-3 justify-center text-center editor-height" style="min-width: 85%">
    <v-row>
      <v-col>
        <v-container fluid>
          <v-row class="text-h6" >
            <v-col cols="2" >
              Name
            </v-col>
            <v-col cols="2" >
              Type
            </v-col>
            <v-col cols="2" >
              Not Null
            </v-col>
            <v-col cols="2" >
              Full Text
            </v-col>
            <v-col cols="2" >
              Array
            </v-col>
            <v-col cols="2" >
              Actions
            </v-col>
            <v-divider></v-divider>
          </v-row>
          <v-row v-for="key in keys" :key="key" >
            <v-col cols="2" >
              {{ key }}
            </v-col>
            <v-col cols="2" >
              {{ structure.entityDefinition.properties[key].type }}
            </v-col>
            <v-col cols="2">
              <v-icon x-small
                      title="Not Null">
                {{ hasDecorator('NotNull', structure.entityDefinition.properties[key]?.decorators) ? "fas fa-check" : "" }}
              </v-icon>
            </v-col>
            <v-col cols="2">
              <v-icon x-small
                      title="Full Text">
                {{ hasDecorator('Text', structure.entityDefinition.properties[key]?.decorators) ? "fas fa-check" : "" }}
              </v-icon>
            </v-col>
            <v-col cols="2">
              <v-icon x-small
                      title="Array">
                {{ structure.entityDefinition.properties[key].type === 'array' ? "fas fa-check" : "" }}
              </v-icon>
            </v-col>
            <v-col cols="2" >
              <v-icon small
                      @click="editProperty(key)"
                      title="Edit"
                      class="mr-2"
                      :disabled="structure.published">
                {{icons.edit}}
              </v-icon>
              <v-icon small
                      @click="deleteProperty(key)"
                      title="Delete"
                      class="mr-2"
                      :disabled="structure.published">
                {{icons.delete}}
              </v-icon>
            </v-col>
            <v-divider></v-divider>
          </v-row>
        </v-container>
      </v-col>
      <v-divider vertical inset></v-divider>
      <v-col cols="6" sm="6" >
        <v-list two-line rounded >
          <v-list-item-title class="text-h6">Add Properties</v-list-item-title>
          <v-list-item>
            <v-list-item-content>
              <v-text-field v-model="propertyName"
                            label="Property Name"
                            @keydown.space.prevent
                            :readonly="editing"
                            :rules="[ validateNewProperty ]" >
              </v-text-field>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-text-field v-model="propertyDescription"
                            label="Property Description" >
              </v-text-field>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-switch v-model="propertyNotNull"
                        label="Property Not Null (Required)" >
              </v-switch>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-select
                  label="Property Type"
                  v-model="propertyType"
                  :items="allPropertyTypes"
                  item-text="type"
                  hint="Property Type"
                  persistent-hint
                  return-object
                  hide-selected
                  single-line >
              </v-select>
            </v-list-item-content>
          </v-list-item>
          <v-list-item v-if="propertyType?.type === 'array'">
            <v-list-item-content>
              <v-select
                  label="Array Contains Type"
                  v-model="propertyArrayContainsType"
                  :items="arrayContainsPropertyTypes"
                  item-text="type"
                  hint="Array Contains Type"
                  persistent-hint
                  return-object
                  hide-selected
                  single-line >
              </v-select>
            </v-list-item-content>
          </v-list-item>
          <v-list-item v-if="propertyType?.type === 'array'">
            <v-list-item-content>
              <v-switch v-model="arrayContainsPropertyNotNull"
                        label="Array Property Not Null (Required)" >
              </v-switch>
            </v-list-item-content>
          </v-list-item>
          <v-list-item v-if="propertyType.type === 'string' || (propertyType.type === 'array' && propertyArrayContainsType?.type === 'string')">
            <v-list-item-content>
              <v-switch v-model="propertyText"
                        label="String property is a full text search field" >
              </v-switch>
            </v-list-item-content>
          </v-list-item>
          <v-list-item v-if="propertyType.type === 'enum' || (propertyType.type === 'array' && propertyArrayContainsType?.type === 'enum')">
            <v-list-item-content>
              <v-text-field v-model="propertyEnumOptions"
                            label="Enum Property Options, comma delimited"
                            @keydown.space.prevent />
            </v-list-item-content>
          </v-list-item>
          <v-list-item-action>
            <v-btn-toggle rounded >
              <v-btn large tile @click="clearNewProperty" >
                Clear
              </v-btn>
              <v-btn large tile @click="manageProperty" :disabled="!valid"  >
                {{ this.editing ? 'Update' : 'Add' }}
              </v-btn>
            </v-btn-toggle>
          </v-list-item-action>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {Component, Emit, PropSync, Vue, Watch} from 'vue-property-decorator'
import {
  mdiPencil,
  mdiDelete
} from '@mdi/js'
import {Structure} from '@/frontends/structures-admin/pages/structures/structures/Structure'
import {
  ArrayC3Type,
  BooleanC3Type,
  ByteC3Type, C3Decorator,
  C3Type,
  CharC3Type,
  DateC3Type,
  DoubleC3Type,
  EnumC3Type,
  FloatC3Type,
  IdC3Type,
  IntC3Type,
  LongC3Type,
  MapC3Type, NotNullC3Decorator,
  ObjectC3Type,
  ReferenceC3Type,
  ShortC3Type,
  StringC3Type, TextC3Decorator,
  UnionC3Type
} from "@kinotic/continuum-idl-js"
import {IndexNameHelper} from "@/frontends/structures-admin/pages/structures/util/IndexNameHelper"
import {PropType} from "vue";
import {Identifiable} from "@kinotic/continuum";


@Component({
  components: { }
})
export default class StructureStandardUi extends Vue {

  @PropSync('entity', {type: Object as PropType<Identifiable<string>>, required: true})
  public structure!: Structure


  private icons = {
    edit: mdiPencil,
    delete: mdiDelete
  }

  private editing: boolean = false
  private valid: boolean = false
  private keys: string[] = []
  private propertyType: C3Type = new StringC3Type()
  private propertyArrayContainsType: C3Type | null = null
  private propertyName: string = ""
  private propertyDescription: string = ""
  private propertyNotNull: boolean = false
  private arrayContainsPropertyNotNull: boolean = false
  private propertyText: boolean = false
  private propertyEnumOptions: string = ""
  private arrayContainsPropertyTypes: C3Type[] = [
    new BooleanC3Type(),
    new ByteC3Type(),
    new CharC3Type(),
    new DateC3Type(),
    new DoubleC3Type(),
    new EnumC3Type(), // if we have an enum we can specify values for it
    new FloatC3Type(),
    new IdC3Type(),
    new IntC3Type(),
    new LongC3Type(),
    new MapC3Type(), // Flattened/Nested Decorators
    new ObjectC3Type(), // Entity?/Flattened/Nested Decorators
    new ReferenceC3Type(), // Entity Decorator
    new ShortC3Type(),
    new StringC3Type(), // Text Decorator
    new UnionC3Type() // Flattened/Nested Decorators
  ]
  private allPropertyTypes: C3Type[] = [
    new ArrayC3Type(), // Flattened/Nested Decorators
    ...this.arrayContainsPropertyTypes
  ]

  constructor() {
    super()
  }

  public mounted() {
    this.keys = Object.keys(this.structure.entityDefinition.properties)
  }

  @Emit()
  public update(): Structure {
    return this.structure
  }

  @Watch('structure')
  public watchStructure(value: any, oldValue: any) {
    this.keys = Object.keys(this.structure.entityDefinition.properties)
  }

  private validateNewProperty(value: string) {
    // preempt the other check - we can save a structure when we are not editing or modifying
    if(value?.length === 0 && !this.editing){
      return true
    }
    let ret: string = IndexNameHelper.checkNameAndNamespace(value as string, 'Property Name')
    if(ret.length === 0){
      // name is good, check to see if we already have the property
      if(!this.editing){
        if(this.structure.entityDefinition.properties.hasOwnProperty(value)){
          ret = `Structure already has property defined with a name of ${value}`
        }
      }
    }
    this.valid = (ret.length === 0)
    return ret.length === 0 ? true : ret
  }

  private manageProperty(){

    this.propertyType.metadata.description = this.propertyDescription
    if(this.propertyNotNull){
      this.checkForDecoratorAndAddIfMissing("NotNull", new NotNullC3Decorator(), this.propertyType)
    }
    if(this.propertyType.type === "string" && this.propertyText){
      this.checkForDecoratorAndAddIfMissing("Text", new TextC3Decorator(), this.propertyType)
    }
    if(this.propertyType.type === "enum"){
      (this.propertyType as EnumC3Type).values = this.propertyEnumOptions.split(',')
    }

    if(this.propertyType.type === "array" && this.propertyArrayContainsType !== null){
      if(this.arrayContainsPropertyNotNull){
        this.checkForDecoratorAndAddIfMissing("NotNull", new TextC3Decorator(), this.propertyArrayContainsType)
      }
      if(this.propertyArrayContainsType?.type === "string" && this.propertyText){
        this.checkForDecoratorAndAddIfMissing("Text", new TextC3Decorator(), this.propertyArrayContainsType)
      }
      if(this.propertyArrayContainsType.type === "enum"){
        (this.propertyArrayContainsType as EnumC3Type).values = this.propertyEnumOptions.split(',')
      }
      (this.propertyType as ArrayC3Type).contains = this.propertyArrayContainsType
    }

    this.structure.entityDefinition.properties[this.propertyName] = this.propertyType
    this.editing = false
    this.keys = Object.keys(this.structure.entityDefinition.properties)
    this.update()
    this.clearNewProperty()
  }

  private clearNewProperty(){
    this.propertyType = new StringC3Type()
    this.propertyArrayContainsType = new StringC3Type()
    this.propertyName = ""
    this.propertyDescription = ""
    this.propertyNotNull = false
    this.arrayContainsPropertyNotNull = false
    this.propertyText = false
    this.propertyEnumOptions = ""
    this.editing = false
  }

  private deleteProperty(key: string) {
    delete this.structure.entityDefinition.properties[key]
    this.keys = Object.keys(this.structure.entityDefinition.properties)
    this.update()
  }

  private editProperty(key: string) {
    this.editing = true
    this.propertyType = this.structure.entityDefinition.properties[key]
    this.propertyName = key
    this.propertyDescription = this.propertyType.metadata?.description || ""
    if(this.propertyType?.decorators && this.propertyType.decorators.length > 0){
      this.propertyType.decorators.forEach((decorator) => {
        if(decorator.type === "NotNull"){
          this.propertyNotNull = true
        }else if(decorator.type === "Text"){
          this.propertyText = true
        }
      })
    }

    if(this.propertyType.type === "enum"){
      this.propertyEnumOptions = (this.propertyType as EnumC3Type).values.join(',')
    }

    if(this.propertyType.type === "array"){
      this.propertyArrayContainsType = (this.propertyType as ArrayC3Type).contains
      if(this.propertyArrayContainsType !== null){
        if(this.propertyArrayContainsType?.decorators && this.propertyArrayContainsType?.decorators.length > 0){
          this.propertyArrayContainsType.decorators.forEach((decorator) => {
            if(decorator.type === "NotNull"){
              this.arrayContainsPropertyNotNull = true
            }else if(decorator.type === "Text"){
              this.propertyText = true
            }
          })
        }
      }
    }
  }

  private checkForDecoratorAndAddIfMissing(decoratorName: string, decorator: C3Decorator, property: C3Type){
    let hasDecorator: boolean = this.hasDecorator(decoratorName, property.decorators)
    if(!hasDecorator){
      property.decorators.push(decorator)
    }
  }

  private hasDecorator(decoratorName: string, decorators: C3Decorator[] | undefined): boolean {
    let hasDecorator: boolean = false
    if(decorators !== undefined && decorators.length > 0){
      // check to see if we already have not null
      decorators.forEach((decorator) => {
        if(decorator.type === decoratorName){
          hasDecorator = true
        }
      })
    }
    return hasDecorator
  }

}
</script>

<style scoped>

</style>