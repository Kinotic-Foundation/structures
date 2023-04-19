<template>
    <v-expansion-panel>
        <template v-if="jobDefinition !== null">
        <v-expansion-panel-header>
            {{jobDefinition.description}}

            <v-btn text icon :ripple="false" color="primary"
                   @click.native.stop="execute">
                <v-icon>{{icons.play}}</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <template v-slot:actions>
                <v-icon v-if="!active" :color="getJobIconColor()">
                    {{getJobIcon()}}
                </v-icon>
                <orbit-spinner
                        v-else
                        :animation-duration="1000"
                        :size="35"
                        color="#2196F3"
                />
            </template>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
            <GrindList :steps="jobDefinition.steps">
            </GrindList>
        </v-expansion-panel-content>
        </template>
        <template v-else>
            <v-expansion-panel-header>
                Loading...
                <template v-slot:actions>
                    <v-icon>
                        $expand
                    </v-icon>
                </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
            </v-expansion-panel-content>
        </template>
    </v-expansion-panel>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator'
    import GrindList from './GrindList.vue'
    import { Subscription } from 'rxjs'
    import { Result, ResultType } from '../domain/grind/Result'
    import { Step, StepState } from '../domain/grind/Step'
    import { inject } from 'inversify-props'
    import { IGrindServiceFactory, IGrindServiceProxy } from '../services/IGrindService'
    import { v4 as uuidv4 } from 'uuid'
    import { JobDefinition } from '../domain/grind/JobDefinition'
    import { OrbitSpinner } from 'epic-spinners'
    import { mdiAlertCircle, mdiEmoticonHappyOutline, mdiChevronDown, mdiPlay } from '@mdi/js'
    import { StepInfo } from '../domain/grind/StepInfo'
    import { Progress } from '../domain/grind/Progress'
    import { PropType } from 'vue'

    @Component({
        components: { GrindList, OrbitSpinner }
    })
    export default class GrindJob extends Vue {

        @Prop({ required: true })
        public serviceIdentifier!: string

        @Prop({ required: true })
        public describeMethodIdentifier!: string
        @Prop({ type: Array as PropType<any[]> , required: false })
        public describeMethodArgs?: any[] | undefined

        @Prop({ required: true })
        public executeMethodIdentifier!: string
        @Prop({ type: Array as PropType<any[]> , required: false })
        public executeMethodArgs?: any[] | undefined

        @inject()
        private grindServiceFactory!: IGrindServiceFactory
        private grindServiceProxy!: IGrindServiceProxy

        private active: boolean = false
        private state: StepState = StepState.PENDING
        private jobDefinition: JobDefinition | null = null
        private subscription: Subscription | null = null
        private alertIdentifier: string = uuidv4()

        private icons = {
            play: mdiPlay,
            chevronDown: mdiChevronDown,
            check: mdiEmoticonHappyOutline,
            failed: mdiAlertCircle
        }
        constructor() {
            super()
        }

        // Lifecycle hooks
        public mounted() {
            this.grindServiceProxy = this.grindServiceFactory.grindServiceProxy(this.serviceIdentifier)

            this.grindServiceProxy.describeJob(this.describeMethodIdentifier, this.describeMethodArgs)
                .then((jobDefinition: JobDefinition) => {
                    this.jobDefinition = jobDefinition
                }).catch((error) => {
                    this.displayAlert(error.message)
                })
        }

        public beforeDestroy() {
            if (this.subscription != null && !this.subscription.closed) {
                this.subscription.unsubscribe()
            }
        }

        public getJobIcon(): string{
            let ret: string = this.icons.chevronDown
            if(this.state == StepState.FINISHED){
                ret = this.icons.check
            }else if(this.state == StepState.FAILED){
                ret = this.icons.failed
            }
            return ret
        }

        public getJobIconColor(): string {
            let ret: string = ''
            if(this.state == StepState.FINISHED){
                ret = 'success'
            }else if(this.state == StepState.FAILED){
                ret = 'error'
            }
            return ret
        }

        public execute(): void {
            this.active = true
            this.hideAlert()
            this.subscription = this.grindServiceProxy.executeJob(this.executeMethodIdentifier, this.executeMethodArgs)
                .subscribe(
                (result: Result) => {
                    // Top level job always has a sequence of zero
                    if(result.stepInfo.sequence === 0){
                        if(result.resultType == ResultType.PROGRESS) {
                            let progress: Progress = result.value as Progress
                            if (progress.percentageComplete < 100) {
                                this.state = StepState.ACTIVE
                            } else {
                                this.state = StepState.FINISHED
                            }
                        }else if(result.resultType == ResultType.EXCEPTION){
                            this.state = StepState.FAILED
                        }
                    }else{
                        this.matchStep(result);
                    }
                },
                (error:any) => {
                    this.active = false
                    this.displayAlert(error)
                },
                () => {
                    this.active = false
                });
        }

        private matchStep(result: Result): void{
            // Result step info is backwards from a resolution standpoint so we need to build a forward list
            let stepSequence: number[] = []
            let current: StepInfo = result.stepInfo
            stepSequence.push(current.sequence)

            while(current.ancestor != null){
                current = current.ancestor
                stepSequence.unshift(current.sequence)
            }

            // now that we have step sequence walk the current graph to find the step to update
            let stepToUpdate: Step | null = null
            if(this.jobDefinition != null) {
                let stepsToSearch: Step[] | undefined= this.jobDefinition.steps

                // First in stepSequence should always be job and have a sequence of 0
                // So we always start with second
                for (let i = 1; i < stepSequence.length; i++) {
                    if(stepsToSearch !== undefined) {
                        // sequence is 1 based
                        stepToUpdate = stepsToSearch[stepSequence[i] - 1]
                        stepsToSearch = stepToUpdate.steps
                    }else{
                        stepToUpdate = null
                        break;
                    }
                }
                if(stepToUpdate !== null){

                    // if progress update progress
                    if(result.resultType == ResultType.PROGRESS) {
                        // use vue set since this may not exist yet and we want reactive
                        this.$set(stepToUpdate, 'progress', result.value as Progress)
                        if(stepToUpdate.progress !== undefined) { // wont be but need to make ts compiler happy
                            if (stepToUpdate.progress.percentageComplete < 100) {
                                this.$set(stepToUpdate, 'state', StepState.ACTIVE)
                            } else {
                                this.$set(stepToUpdate, 'state', StepState.FINISHED)
                            }
                        }
                    }else if(result.resultType == ResultType.DYNAMIC_STEPS){
                        if(stepToUpdate.steps == undefined){
                            this.$set(stepToUpdate, 'steps', [])
                        }
                        if(stepToUpdate.steps !== undefined) { // wont be but need to make ts compiler happy
                            stepToUpdate.steps.push(result.value as Step)
                        }
                    }else if(result.resultType == ResultType.EXCEPTION){
                        this.$set(stepToUpdate, 'state', StepState.FAILED)
                        if(stepToUpdate.progress !== undefined){
                            stepToUpdate.progress.message = result.value as string
                        }else{
                            this.$set(stepToUpdate, 'progress', new Progress(0, result.value as string))
                        }
                    }
                }else{
                    this.displayAlert("Could not find step to update, sequence is invalid. Wat!")
                }
            }else{
                this.displayAlert("JobDefinition was not loaded before execute was called. Wat!")
            }
        }

        private hideAlert() {
            (this.$notify as any as { close: (value: string) => void }).close('grindJobAlert'+this.alertIdentifier)
        }

        private displayAlert(text: string) {
            this.$notify({ group: 'alert', type: 'grindJobAlert'+this.alertIdentifier, text})
        }

    }
</script>

<style scoped>

</style>
