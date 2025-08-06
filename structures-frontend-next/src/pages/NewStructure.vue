<script lang="ts">
import { Vue, Prop, Emit, Component, Ref, Watch } from "vue-facing-decorator";
import { VueFlow, type Node, type Edge } from "@vue-flow/core";
import { Background } from "@vue-flow/background";
import { Controls } from "@vue-flow/controls";
import '@vue-flow/controls/dist/style.css'
import { MiniMap } from "@vue-flow/minimap";
import Button from "primevue/button";
import InputText from "primevue/inputtext";
import "@vue-flow/core/dist/style.css";
import "@vue-flow/core/dist/theme-default.css";
import StructureSidebarDashboard from "@/components/structures/StructureSidebarDashboard.vue"
import Structure from "@/components/structures/flow-components/Structure.vue";

@Component({
  components: {
    Structure,
    StructureSidebarDashboard,
    Button,
    VueFlow,
    Background,
    Controls,
    MiniMap,
    InputText,
  },
})
export default class StructureItemModal extends Vue {
  @Prop({ default: null }) item!: any;
  visible = true;

  name: string = "UntitledStructure1";
  isEditing: boolean = false;
  textWidth: number = 0;
  textHeight: number = 0;

  @Ref("nameTextEl") readonly nameTextEl!: HTMLElement;

  @Watch('name')
  onNameChange(newName: string) {
    if (this.flowNodes.length > 0) {
      this.flowNodes[0].data.label = newName
    }
  }

  flowNodes: Node[] = [];

  created() {
    this.flowNodes = [
      {
        id: '1',
        type: 'structure',
        position: { x: 250, y: 100 },
        data: {
          label: this.name,
          fields: [],
          color: null,
        },
      },
    ];
  }

  flowEdges: Edge[] = [];

  nodeTypes = {
    structure: Structure,
  };

  @Emit("close") closeModal() {
    return true;
  }
  onHide() {
    this.visible = false;
    this.closeModal();
  }

  handleMouseEnter() {
    this.measureText();
    this.isEditing = true;
  }

  handleMouseLeave() {
    this.isEditing = false;
  }

  measureText() {
    if (this.nameTextEl) {
      const rect = this.nameTextEl.getBoundingClientRect();
      this.textWidth = rect.width;
      this.textHeight = rect.height;
    }
  }
}
</script>
<template>
  <div
    v-show="visible"
    class="fixed inset-0 z-50 flex items-center justify-center bg-opacity-50"
  >
    <div class="relative w-full h-screen bg-white shadow-lg overflow-hidden">
      <div class="flex h-full">
        <div
          class="flex flex-col h-full"
          :style="{ width: `calc(100% - 320px)` }"
        >
          <div
            class="p-2 bg-transparent fixed top-0 left-0 z-3"
            :style="{ width: `calc(100% - 320px)` }"
          >
            <div
              class="flex items-center justify-between bg-white p-2 rounded-xl"
            >
              <div class="flex items-center">
                <Button
                  severity="secondary"
                  variant="text"
                  icon="pi pi-arrow-left"
                  size="small"
                  class="mr-2"
                />
                <div
                  class="relative w-fit"
                  @mouseenter="handleMouseEnter"
                  @mouseleave="handleMouseLeave"
                >
                  <p
                    v-show="!isEditing"
                    class="!font-semibold px-2 py-1 w-[140px] rounded border border-transparent cursor-pointer"
                  >
                    {{ name }}
                  </p>
                  <InputText
                    v-show="isEditing"
                    v-model="name"
                    size="small"
                    class="!font-semibold w-[160px] px-2 py-1 rounded border border-gray-300"
                  />
                </div>
              </div>
              <div class="flex gap-4">
                <Button
                  icon="pi pi-replay"
                  severity="secondary"
                  size="small"
                  aria-label="Undo"
                  variant="text"
                />
                <Button
                  icon="pi pi-refresh"
                  severity="secondary"
                  size="small"
                  aria-label="Redo"
                  variant="text"
                />
                <Button
                  icon="pi pi-sitemap"
                  severity="secondary"
                  size="small"
                  aria-label="Undo"
                />
              </div>
            </div>
          </div>
          <div class="flex-1 bg-surface-50">
            <VueFlow
                ref="flow"
                :nodeTypes="nodeTypes"
                :nodes="flowNodes"
                :edges="flowEdges"
                :minZoom="0.01"
            >
              <Background pattern-color="red" color="black" :gap="20" />
              <MiniMap />
              <Controls position="bottom-left" :show-fit-view="false" :show-interactive="false" />
            </VueFlow>
          </div>
        </div>
        <StructureSidebarDashboard />
      </div>
    </div>
  </div>
</template>

<style scoped>
.p-row-even,
.p-row-odd {
  cursor: pointer !important;
}
.p-button-sm.p-button-icon-only {
  width: 44px;
}
</style>
