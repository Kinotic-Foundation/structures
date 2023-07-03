<template>
    <v-container class="pa-3 justify-center text-center editor-height" style="min-width: 85%">
        <v-row>
            <v-col cols="12">
                <v-fab-transition>
                    <v-btn dark
                           absolute
                           right
                           fab
                           color="primary"
                           title="Manage Properties"
                           @click="dialog = true">
                        <v-icon>{{ icons.add }}</v-icon>
                    </v-btn>
                </v-fab-transition>
            </v-col>
        </v-row>
        <v-row class="text-h6" style="border-bottom-color: grey; border-bottom-width: thin; border-bottom-style: inset">
            <v-col cols="2">
                Name
            </v-col>
            <v-col cols="2">
                Type
            </v-col>
            <v-col cols="2">
                Not Null
            </v-col>
            <v-col cols="2">
                Full Text
            </v-col>
            <v-col cols="2">
                Collection
            </v-col>
            <v-col cols="2">
                Actions
            </v-col>
        </v-row>
        <v-row v-for="key in keys" :key="key"
               style="border-bottom-color: grey; border-bottom-width: thin; border-bottom-style: inset"
               ref="properties ">
            <v-col cols="2">
                {{ key }}
            </v-col>
            <v-col cols="2">
                {{
                    structure.entityDefinition.properties[key].type === 'array' ? 'array -> ' + structure.entityDefinition.properties[key].contains.type : structure.entityDefinition.properties[key].type
                }}
            </v-col>
            <v-col cols="2">
                <v-icon x-small
                        title="Not Null">
                    {{
                        StructureUtil.hasDecorator('NotNull', structure.entityDefinition.properties[key]?.decorators) ? "fas fa-check" : ""
                    }}
                </v-icon>
            </v-col>
            <v-col cols="2">
                <v-icon x-small
                        title="Full Text">
                    {{
                        StructureUtil.hasDecorator('Text', structure.entityDefinition.properties[key]?.decorators) ? "fas fa-check" : ""
                    }}
                </v-icon>
            </v-col>
            <v-col cols="2">
                <v-icon x-small
                        title="Array">
                    {{ structure.entityDefinition.properties[key].type === 'array' ? "fas fa-check" : "" }}
                </v-icon>
            </v-col>
            <v-col cols="2">
                <v-icon small
                        @click="editProperty(key)"
                        title="Edit"
                        class="mr-2"
                        :disabled="structure.published">
                    {{ icons.edit }}
                </v-icon>
                <v-icon small
                        @click="deleteProperty(key)"
                        title="Delete"
                        class="mr-2"
                        :disabled="structure.published">
                    {{ icons.delete }}
                </v-icon>
            </v-col>
        </v-row>

        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-list two-line subheader rounded>
                    <v-subheader>
                        Add Properties
                        <div style="min-width: 80%">
                            <v-btn icon @click="closeDialog" class="float-right bg-surface-variant">
                                <v-icon>{{ icons.close }}</v-icon>
                            </v-btn>
                        </div>
                    </v-subheader>
                    <v-list-item>
                        <v-list-item-content>
                            <v-text-field v-model="propertyName"
                                          label="Property Name"
                                          @keydown.space.prevent
                                          autofocus
                                          :readonly="editing"
                                          :rules="[ validateNewProperty ]">
                            </v-text-field>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-text-field v-model="propertyDescription"
                                          label="Property Description">
                            </v-text-field>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content>
                            <v-switch v-model="propertyNotNull"
                                      label="Property Not Null (Required)">
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
                                single-line>
                            </v-select>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item v-if="propertyType?.type === 'array'">
                        <v-list-item-content>
                            <v-select
                                label="Collection Contains Type"
                                v-model="propertyArrayContainsType"
                                :items="arrayContainsPropertyTypes"
                                item-text="type"
                                hint="Collection Contains Type"
                                persistent-hint
                                return-object
                                hide-selected
                                single-line>
                            </v-select>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item v-if="propertyType?.type === 'array'">
                        <v-list-item-content>
                            <v-switch v-model="arrayContainsPropertyNotNull"
                                      label="Collection Property Not Null (Required)">
                            </v-switch>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item
                        v-if="propertyType.type === 'string' || (propertyType.type === 'array' && propertyArrayContainsType?.type === 'string')">
                        <v-list-item-content>
                            <v-switch v-model="propertyText"
                                      label="String property is a full text search field">
                            </v-switch>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item
                        v-if="propertyType.type === 'enum' || (propertyType.type === 'array' && propertyArrayContainsType?.type === 'enum')">
                        <v-list-item-content>
                            <v-text-field v-model="propertyEnumOptions"
                                          label="Enum Property Options, comma delimited"
                                          @keydown.space.prevent/>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn-toggle rounded>
                        <v-btn large tile @click="closeDialog">
                            Done
                        </v-btn>
                        <v-btn large tile @click="clearNewProperty">
                            Reset
                        </v-btn>
                        <v-btn large tile @click="manageProperty" :disabled="!valid">
                            {{ this.editing ? 'Update' : 'Add' }}
                        </v-btn>
                    </v-btn-toggle>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script lang="ts">
import {Component, Emit, PropSync, Vue, Watch} from 'vue-property-decorator'
import {
    mdiPencil,
    mdiDelete,
    mdiClose,
    mdiPlus
} from '@mdi/js'
import {Structure} from '@/frontends/structures-admin/pages/structures/structures/Structure'
import {
    ArrayC3Type,
    BooleanC3Type,
    ByteC3Type,
    C3Decorator,
    C3Type,
    CharC3Type,
    DateC3Type,
    DoubleC3Type,
    EnumC3Type,
    FloatC3Type,
    IntC3Type,
    LongC3Type,
    MapC3Type,
    NotNullDecorator,
    ObjectC3Type,
    ReferenceC3Type,
    ShortC3Type,
    StringC3Type,
    TextDecorator,
    UnionC3Type
} from "@kinotic/continuum-idl"
import {IndexNameHelper} from "@/frontends/structures-admin/pages/structures/util/IndexNameHelper"
import {PropType} from "vue";
import {Identifiable} from "@kinotic/continuum";
import {StructureUtil} from "@/frontends/structures-admin/pages/structures/util/StructureUtil";


@Component({
    computed: {
        StructureUtil() {
            return StructureUtil
        }
    },
    components: {}
})
export default class StructureStandardUi extends Vue {

    @PropSync('entity', {type: Object as PropType<Identifiable<string>>, required: true})
    public structure!: Structure


    private icons = {
        add: mdiPlus,
        edit: mdiPencil,
        delete: mdiDelete,
        close: mdiClose
    }

    private editing: boolean = false
    private valid: boolean = false
    private dialog: boolean = false
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

    private closeDialog() {
        this.clearNewProperty()
        this.dialog = false
    }

    private validateNewProperty(value: string) {
        // preempt the other check - we can save a structure when we are not editing or modifying
        if (value?.length === 0 && !this.editing) {
            return true
        }
        let ret: string = IndexNameHelper.checkNameAndNamespace(value as string, 'Property Name')
        if (ret.length === 0) {
            // name is good, check to see if we already have the property
            if (!this.editing) {
                if (this.structure.entityDefinition.properties.hasOwnProperty(value)) {
                    ret = `Structure already has property defined with a name of ${value}`
                }
            }
        }
        this.valid = (ret.length === 0)
        return ret.length === 0 ? true : ret
    }

    private manageProperty() {

        if (this.propertyType?.metadata === undefined) {
            this.propertyType.metadata = {}
        }
        this.propertyType.metadata.description = this.propertyDescription

        this.checkForDecoratorAndManage("NotNull", new NotNullDecorator(), this.propertyType, this.propertyNotNull)

        if (this.propertyType.type === "string") {
            this.checkForDecoratorAndManage("Text", new TextDecorator(), this.propertyType, this.propertyText)
        }
        if (this.propertyType.type === "enum") {
            if ((this.propertyType as EnumC3Type)?.values === undefined) {
                (this.propertyType as EnumC3Type).values = []
            }
            (this.propertyType as EnumC3Type).values = this.propertyEnumOptions.split(',')
        }

        if (this.propertyType.type === "array" && this.propertyArrayContainsType !== null) {
            this.checkForDecoratorAndManage("NotNull", new NotNullDecorator(), this.propertyArrayContainsType, this.arrayContainsPropertyNotNull)

            if (this.propertyArrayContainsType?.type === "string") {
                this.checkForDecoratorAndManage("Text", new TextDecorator(), this.propertyArrayContainsType, this.propertyText)
            }
            if (this.propertyArrayContainsType.type === "enum") {
                if ((this.propertyArrayContainsType as EnumC3Type)?.values === undefined) {
                    (this.propertyArrayContainsType as EnumC3Type).values = []
                }
                (this.propertyArrayContainsType as EnumC3Type).values = this.propertyEnumOptions.split(',')
            }
            (this.propertyType as ArrayC3Type).contains = this.propertyArrayContainsType
        }

        this.structure.entityDefinition.properties[this.propertyName] = this.propertyType
        this.keys = Object.keys(this.structure.entityDefinition.properties)
        this.update()
        this.clearNewProperty()
        if (this.editing) {
            // when we are updating a single property update should close the dialog
            this.dialog = false
        }
        this.editing = false
    }

    private clearNewProperty() {
        this.propertyType = new StringC3Type()
        this.propertyArrayContainsType = new StringC3Type()
        this.propertyName = ""
        this.propertyDescription = ""
        this.propertyNotNull = false
        this.arrayContainsPropertyNotNull = false
        this.propertyText = false
        this.propertyEnumOptions = ""
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
        if (this.propertyType?.decorators && this.propertyType.decorators.length > 0) {
            this.propertyType.decorators.forEach((decorator) => {
                if (decorator.type === "NotNull") {
                    this.propertyNotNull = true
                } else if (decorator.type === "Text") {
                    this.propertyText = true
                }
            })
        }

        if (this.propertyType.type === "enum") {
            this.propertyEnumOptions = (this.propertyType as EnumC3Type).values.join(',')
        }

        if (this.propertyType.type === "array") {
            this.propertyArrayContainsType = (this.propertyType as ArrayC3Type).contains
            if (this.propertyArrayContainsType !== null) {
                if (this.propertyArrayContainsType?.decorators && this.propertyArrayContainsType?.decorators.length > 0) {
                    this.propertyArrayContainsType.decorators.forEach((decorator) => {
                        if (decorator.type === "NotNull") {
                            this.arrayContainsPropertyNotNull = true
                        } else if (decorator.type === "Text") {
                            this.propertyText = true
                        }
                    })
                }
            }
        }

        this.dialog = true
    }

    private checkForDecoratorAndManage(decoratorName: string, decorator: C3Decorator, property: C3Type, add: boolean) {
        let hasDecorator: boolean = false
        if (property?.decorators === undefined) {
            property.decorators = []
        } else {
            hasDecorator = StructureUtil.hasDecorator(decoratorName, property.decorators)
        }

        if (!hasDecorator && add) {
            property.decorators.push(decorator)
        } else if (hasDecorator && !add) {
            for (let i: number = 0; i < property.decorators.length; i++) {
                if (property.decorators[i].type === decoratorName) {
                    property.decorators.splice(i, 1)
                    break
                }
            }
        }
    }

}
</script>

<style scoped>

</style>