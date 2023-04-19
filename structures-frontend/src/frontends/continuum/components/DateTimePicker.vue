<template>
    <v-dialog v-model="dateTimeDisplay"
              :width="width"
              :max-width="maxWidth" >
        <template v-slot:activator="{ on }">
            <v-text-field
                    :ref="identity"
                    :key="identity"
                    v-bind="textFieldProps"
                    :disabled="disabled"
                    :label="label"
                    :value="selectedDateTime"
                    :rules="[testValidation]"
                    v-on="on"
                    readonly
                    required >
            </v-text-field>
        </template>

        <v-card>
            <v-card-text class="px-0 py-0">
                <v-tabs fixed-tabs
                        v-model="activeTab" >
                    <v-tab key="calendar">
                        <slot name="dateIcon">
                            <v-icon>event</v-icon>
                        </slot>
                    </v-tab>
                    <v-tab key="timer" :disabled="dateSelected">
                        <slot name="timeIcon">
                            <v-icon>access_time</v-icon>
                        </slot>
                    </v-tab>
                    <v-tab-item key="calendar">
                        <v-date-picker ref="date"
                                       v-model="date"
                                       v-bind="datePickerProps"
                                       :class="customDateClass"
                                       :full-width="fullWidth"
                                       @input="showTimePicker" >
                        </v-date-picker>
                    </v-tab-item>
                    <v-tab-item key="time">
                        <v-time-picker ref="time"
                                       v-model="time"
                                       v-bind="timePickerProps"
                                       :class="customTimeClass"
                                       :full-width="fullWidth" >
                        </v-time-picker>
                    </v-tab-item>
                </v-tabs>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <slot name="actions">
                    <v-btn color="grey lighten-1" text @click="clearHandler">CLEAR</v-btn>
                    <v-btn color="green darken-1" text @click="okHandler">DONE</v-btn>
                </slot>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script lang="ts" >
    import {Component, Emit, Prop, Vue, Watch} from 'vue-property-decorator'

    @Component({
        components: { }
    })
    export default class DateTimePicker extends Vue {

        @Prop({type: Number}) public datetime: number | undefined
        @Prop({type: String}) public label: string | undefined
        @Prop({type: Boolean}) public disabled: boolean | undefined
        @Prop({type: Boolean}) public fullWidth: boolean | undefined
        @Prop({type: String}) public width: string | undefined
        @Prop({type: String}) public maxWidth: string | undefined
        @Prop({type: String}) public customDateClass: string | undefined
        @Prop({type: String}) public customTimeClass: string | undefined
        @Prop({type: Object}) public textFieldProps: object | undefined
        @Prop({type: Object}) public datePickerProps: object | undefined
        @Prop({type: Object}) public timePickerProps: object | undefined

        @Prop({type: String, required: true}) public identity: string | undefined
        @Prop({type: Function, required: true}) public validator: Function | undefined

        private dateTimeDisplay: boolean = false
        private activeTab: number = 0
        private date: string = ''
        private time: string = ''
        private selectedDateTime: string = ''


        constructor() {
            super()
        }

        // Lifecycle hook
        public mounted() {
            this.init()
        }

        public init() {
            if (this.datetime) {
                const [date, time] = new Date(Number(this.datetime)).toLocaleString('en-US', {hour12: false}).split(', ')
                this.date = date
                this.time = time
                this.selectedDateTime = this.date + ' ' + this.time
            } else {
                this.date = ''
                this.time = ''
                this.selectedDateTime = ''
            }
        }

        get dateSelected() {
            return !this.date
        }

        @Emit('input')
        public okHandler() {
            this.resetPicker()
            this.selectedDateTime = this.date + ' ' + this.time
            return new Date(this.selectedDateTime).getTime()
        }

        @Emit('input')
        public clearHandler() {
            this.resetPicker()
            this.date = ''
            this.time = ''
            this.selectedDateTime = ''
            return null
        }

        public resetPicker() {
            this.dateTimeDisplay = false
            this.activeTab = 0
            if (this.$refs.time) {
                (this.$refs['time'] as any).selectingHour = true
            }
        }

        public showTimePicker() {
            this.activeTab = 1
        }

        public testValidation(value: any) {
            if (this.validator && this.identity) {
                return this.validator(this.identity, this.selectedDateTime.length > 0 ? new Date(this.date + ' ' + this.time).getTime() : undefined)
            }
            return true
        }
    }
</script>

<style scoped>

</style>
