<template>
    <div>
        <v-text-field v-model='structure.name' label='Name' :rules="nameRules"></v-text-field>

        <v-text-field v-model='structure.description' label='Description'></v-text-field>

        <v-select
            label="Namespace"
            v-model="selectedNamespace"
            :items="namespaces"
            :disabled="structure.published"
            :hint="selectedNamespace.id + (selectedNamespace.description ? ' | ' + selectedNamespace.description : '')"
            :error-messages="namespaceErrorMessage"
            @change="selected"
            item-text="id"
            return-object
            persistent-hint
            hide-selected
            single-line>
        </v-select>
    </div>
</template>

<script lang="ts">

import {Component, Emit, PropSync, Vue, Watch} from 'vue-property-decorator'
import {PropType} from 'vue'
import {Continuum, ICrudServiceProxy, Identifiable, Pageable} from '@kinotic/continuum-client'
import {Structure, Namespace} from '@kinotic/structures-api'
import {IndexNameHelper} from '@/frontends/structures-admin/pages/structures/util/IndexNameHelper'

type RuleValidator = (value: any) => string | boolean

@Component({
    components: {}
})
export default class StructureStandardUi extends Vue {

    @PropSync('entity', {type: Object as PropType<Identifiable<string>>, required: true})
    public structure!: Structure


    private namespaceServiceIdentifier: string = 'org.kinotic.structures.api.services.NamespaceService'
    private namespaceService: ICrudServiceProxy<Namespace> = Continuum.crudServiceProxy(this.namespaceServiceIdentifier)
    private selectedNamespace: Namespace = new Namespace("", "", 0)
    private namespaceErrorMessage: string = ""
    private namespaces: Namespace[] = []
    private nameRules: RuleValidator[] = [
        (v) => {
            let ret: string = IndexNameHelper.checkNameAndNamespace(v as string, 'Name')
            return ret.length === 0 ? true : ret
        }
    ]
    private structureNamespaceLoaded: boolean = false

    constructor() {
        super()
    }

    public mounted() {

        const pageable = Pageable.create(0, 200, null)

        this.namespaceService.findAll(pageable)
            .then((response) => {
                this.namespaces = response.content
            })
            .catch((error) => {
                console.error('Error setting up to add/edit Structure')
                this.displayAlert(error.message)
            })

        this.loadNamespace(this.structure)
    }

    @Emit()
    public update(): Structure {
        return this.structure
    }

    @Watch('structure')
    public watchStructure(value: any, oldValue: any) {
        if (value !== undefined && value !== null) {
            if (value.id !== '') {
                this.loadNamespace(value)
            }
        }
    }

    private selected() {
        this.structureNamespaceLoaded = true
        this.structure.namespace = this.selectedNamespace.id
        this.update()
    }

    private displayAlert(text: string) {
        this.$notify({group: 'alert', type: 'crudEntityAddEditAlert', text})
    }

    private loadNamespace(structure: Structure) {
        if (!this.structureNamespaceLoaded && structure.namespace !== '') {
            this.namespaceService.findById(structure.namespace)
                .then((namespace) => {
                    this.selectedNamespace = namespace
                    this.structureNamespaceLoaded = true
                })
                .catch((error) => {
                    console.error('Error setting up to add/edit Structure', error)
                    this.displayAlert(error.message)
                })
        }
    }
}
</script>

<style scoped>

</style>
