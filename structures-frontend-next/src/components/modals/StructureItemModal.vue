<template>
  <Dialog modal v-model:visible="visible" header="Structure Details" :style="{ width: '80vw' }"
    :breakpoints="{ '1199px': '90vw', '575px': '95vw' }" @hide="onHide">
    <div class="h-[600px]">
      <VueFlow ref="flow" :nodes="nodes" :edges="edges" :node-types="nodeTypes">
        <Background pattern-color="#ccc" :gap="20" />
        <MiniMap />
        <Controls position="top-left" />
      </VueFlow>
    </div>
  </Dialog>
</template>

<script lang="ts">
import { Vue, Prop, Emit, Component } from 'vue-facing-decorator'
import Dialog from 'primevue/dialog'
import { VueFlow, type Node, type Edge, MarkerType } from '@vue-flow/core'
import { Background } from '@vue-flow/background'
import { Controls } from '@vue-flow/controls'
import { MiniMap } from '@vue-flow/minimap'
import ERTable from '@/components/modals/ERTable.vue'

import '@vue-flow/core/dist/style.css'
import '@vue-flow/core/dist/theme-default.css'

@Component({
  components: {
    Dialog,
    VueFlow,
    Background,
    Controls,
    MiniMap,
  },
})
export default class StructureItemModal extends Vue {
  @Prop({ default: null }) item!: any
  visible = true

  _edges: Edge[] = []
  _nodeCounter = 0
  _yOffset = 50

  @Emit('close')
  closeModal() {
    return true
  }

  onHide() {
    this.visible = false
    this.closeModal()
  }

  get nodeTypes() {
    return {
      erTable: ERTable,
    }
  }

  get nodes(): Node[] {
    const entity = this.item?.entityDefinition
    if (!entity) return []

    this._edges = []
    this._nodeCounter = 0
    this._yOffset = 50

    const nodes: Node[] = []

    const createNode = (id: string, label: string, fields: string[], depth = 0): string => {
      const nodeId = `${id}_${this._nodeCounter++}`
      nodes.push({
        id: nodeId,
        type: 'erTable',
        position: { x: 100 + depth * 350, y: this._yOffset },
        data: {
          label,
          fields,
        },
      })
      this._yOffset += Math.max(120, fields.length * 25 + 40)
      return nodeId
    }

    const processEntity = (obj: any, label: string, depth = 0): string => {
      const fields: string[] = []
      const fieldIndexMap: Record<string, number> = {}
      const nodeId = createNode(label, label, fields, depth)

      for (const key of Object.keys(obj)) {
        const value = obj[key]

        const currentIndex = fields.length

        if (Array.isArray(value)) {
          fields.push(`${key}: array`)
          fieldIndexMap[key] = currentIndex

          value.forEach((v, idx) => {
            if (typeof v === 'object') {
              const childId = processEntity(v, `${label}_${key}_${idx}`, depth + 1)
              this._edges.push({
                id: `e-${nodeId}-${childId}`,
                source: nodeId,
                sourceHandle: `out-${currentIndex}`,
                target: childId,
                targetHandle: 'in-0',
                type: 'default',
                animated: true,
                markerEnd: { type: MarkerType.Arrow },
              })
            }
          })
        } else if (typeof value === 'object' && value !== null) {
          fields.push(`${key}: object`)
          fieldIndexMap[key] = currentIndex

          const childId = processEntity(value, `${label}_${key}`, depth + 1)
          this._edges.push({
            id: `e-${nodeId}-${childId}`,
            source: nodeId,
            sourceHandle: `out-${currentIndex}`,
            target: childId,
            targetHandle: 'in-0',
            type: 'default',
            animated: true,
            markerEnd: { type: MarkerType.Arrow },
          })
        } else {
          fields.push(`${key}: ${typeof value}`)
          fieldIndexMap[key] = currentIndex
        }
      }

      return nodeId
    }

    processEntity(entity, entity.name || 'Root')

    this.$nextTick(() => {
      (this.$refs.flow as any)?.fitView?.()
    })

    return nodes
  }

  get edges(): Edge[] {
    return this._edges
  }
}
</script>
