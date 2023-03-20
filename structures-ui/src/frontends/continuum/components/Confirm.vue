<template>
    <v-dialog
            v-model="show"
            :max-width="options.width"
            :persistent="options.persistent"
            :style="{ zIndex: options.zIndex }"
            @keydown.esc="cancel">
        <v-card>
            <v-toolbar :color="options.color" dark dense flat>
                <v-toolbar-title class="white--text">{{ title }}</v-toolbar-title>
            </v-toolbar>
            <v-card-text v-show="!!message" class="pa-4">{{ message }}</v-card-text>
            <v-card-actions class="pt-0">
                <v-spacer></v-spacer>
                <v-btn autofocus @click.native="agree" color="primary darken-1" text>Yes</v-btn>
                <v-btn @click.native="cancel" color="grey" text>Cancel</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">
    import Vue from 'vue'
    import { Component } from 'vue-property-decorator'

    /**
     * Vuetify Confirm Dialog component
     *
     * Insert component where you want to use it:
     * <confirm ref="confirm"></confirm>
     *
     * Call it:
     * this.$refs.confirm.open('Delete', 'Are you sure?', { color: 'red' }).then((confirm) => {})
     * Or use await:
     * if (await this.$refs.confirm.open('Delete', 'Are you sure?', { color: 'red' })) {
     *   // yes
     * }
     * else {
     *   // cancel
     * }
     *
     * Alternatively you can place it in main App component and access it globally via this.$root.$confirm
     * <template>
     *   <v-app>
     *     ...
     *     <confirm ref="confirm"></confirm>
     *   </v-app>
     * </template>
     *
     * mounted() {
     *   this.$root.$confirm = this.$refs.confirm.open
     * }
     */

    export interface ConfirmOptions {
        color?: string,
        width?: number,
        zIndex?: number,
        persistent?: boolean
    }

    @Component({
      components: { }
    })
    export default class Confirm extends Vue {

        private dialog: boolean = false
        private resolve!: (value: boolean) => void
        private reject!: (reason?: any) => void
        private message: string | null = null
        private title: string | null = null

        private options: ConfirmOptions = {
            color: 'primary',
            width: 290,
            zIndex: 200,
            persistent: false
        }


        constructor() {
            super()
        }

        public get show(): boolean {
            return this.dialog
        }

        public set show(value: boolean){
            this.dialog = value
            if (!value) {
                this.cancel()
            }
        }

        public open(title: string, message: string, options: ConfirmOptions): Promise<boolean> {
            this.dialog = true
            this.title = title
            this.message = message
            this.options = Object.assign(this.options, options)
            return new Promise<boolean>((resolve, reject) => {
                this.resolve = resolve
                this.reject = reject
            })
        }

        public agree(): void {
            this.resolve(true)
            this.dialog = false
        }

        public cancel(): void {
            this.resolve(false)
            this.dialog = false
        }
    }

</script>
