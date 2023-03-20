<template>
    <v-autocomplete ref="autocomplete"
                    v-model="selected"
                    :loading="loading"
                    :search-input.sync="search"
                    :items="items"
                    :placeholder="label"
                    :item-text="textKey"
                    :rules="[testValidation]"
                    :key="identity"
                    :name="identity"
                    @input="emitChange"
                    type="object"
                    :prepend-icon="icons.search"
                    class="mx-4"
                    :clear-icon="icons.closeCircle"
                    open-on-clear
                    auto-select-first
                    return-object
                    clearable
                    hide-selected
                    required >
    </v-autocomplete>
</template>

<script lang="ts" >
    import {Component, Emit, Prop, Vue, Watch} from 'vue-property-decorator'
    import {IItemManager, IStructureManager} from '@/frontends/structures-admin/services'
    import { inject } from 'inversify-props'
    import {
        mdiCloseCircle,
        mdiDatabaseSearch
    } from '@mdi/js'

    @Component({
        components: { }
    })
    export default class ReferenceAutocomplete extends Vue {

        @Prop({type: Object, required: true}) public model: object | undefined
        @Prop({type: Object, required: true}) public reference: any | undefined
        @Prop({type: String, required: true}) public label: string | undefined

        @Prop({type: String}) public identity: string | undefined
        @Prop({type: Function}) public validator: Function | undefined

        @inject()
        private itemManager!: IItemManager
        @inject()
        private structureManager!: IStructureManager

        private selected: any | undefined
        private loading: boolean = false
        private textKey: string = ''
        private search: string = ''
        private items: any[] = []
        private serverErrors: string = ''
        private structureName: string = ''
        private structureJsonSchema: any = {}
        private defaultTraitRegex: RegExp = new RegExp(/^id|createdTime|updatedTime|deleted|deletedTime|structureId|structureName$/)

        public searchTimeoutHandle: any = {}
        public searchTimeoutInterval: number = 1000
        public searchTimeoutStartTime: number = 0

        private icons = {
            closeCircle: mdiCloseCircle,
            search: mdiDatabaseSearch
        }

        constructor() {
            super()
        }

        // Lifecycle hook
        public mounted() {
            this.selected = this.model

            this.structureManager.getJsonSchema(this.reference.urn).then((jsonSchema: string) => {
                this.structureJsonSchema = JSON.parse(jsonSchema)
                this.structureName = this.structureJsonSchema.structure
                for(let key in this.structureJsonSchema.properties) {
                    if(this.structureJsonSchema.properties.hasOwnProperty(key)
                        && !this.defaultTraitRegex.test(key)) {
                        let fieldName = key.charAt(0).toUpperCase() + key.slice(1)
                        if(this.structureJsonSchema.properties[key].type === "string"){
                            this.textKey = key
                            if(typeof(this.selected) !== 'undefined' && this.selected !== null){
                                this.search = this.selected[this.textKey]
                            }
                        }
                    }
                }
            })
            .catch((error: any) => {
                console.log(error.stack)
                this.serverErrors = error.message
            })
        }

        public beforeDestroy() {
        }

        @Emit('input')
        public emitChange(selected: any){
            return selected
        }

        @Watch('search')
        public searchChanged(value: any, oldValue: any) {
            let test: any = this.$refs['autocomplete'] as any
            if((typeof(oldValue) === 'undefined' || oldValue === null || oldValue.length >= 1)
                && (typeof(value) === 'undefined' || value === null || value.length === 0)){
                // new value is cleared and we had an old value
                this.items.length = 0
            }else if(value !== null && value.length >= 2){
                this.serverErrors = ''
                // start to search when we have a couple characters.
                if(this.searchTimeoutStartTime === 0){
                    this.searchTimeoutStartTime = new Date().getTime()
                }else if((new Date().getTime() - this.searchTimeoutStartTime) < this.searchTimeoutInterval){
                    clearTimeout(this.searchTimeoutHandle)
                }

                this.searchTimeoutHandle = setTimeout(() => {
                    this.loading = true
                    this.itemManager.search(this.reference.urn, this.search.trim()+"*", 100, 0).then((returnedItems: any) => {
                        this.loading = false
                        this.items.length = 0 // reset the list
                        this.items = returnedItems.content
                    })
                    .catch((error: any) => {
                        this.loading = false
                        console.log(error.stack)
                        this.serverErrors = error.message
                    })
                }, this.searchTimeoutInterval)
            }
        }

        public testValidation(value: any){
            if(this.validator && this.identity){
                return this.validator(this.identity, value)
            }
            return true
        }
    }
</script>

<style scoped>

</style>
