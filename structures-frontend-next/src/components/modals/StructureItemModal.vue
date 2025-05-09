<template>
  <Dialog modal v-model:visible="visible" header="Structure Details" :style="{ width: '100vw', height: '100vh' }"
     @hide="onHide">
    <div class="h-[600vh]">
      <VueFlow ref="flow" :nodes="flowNodes" :edges="flowEdges" :node-types="nodeTypes">
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

  flowNodes: Node[] = []
  flowEdges: Edge[] = []

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

  mounted() {
    this.setupGraph()
  }

  setupGraph() {
    const entity = this.item?.entityDefinition
    if (!entity || !Array.isArray(entity.properties)) return

    this.flowEdges = []
    let nodeCounter = 0
    let yOffset = 20
    const nodes: Node[] = []
    const getRandomColor = (): string => {
  const colors = ['bg-orange-300', 'bg-blue-300', 'bg-green-300', 'bg-purple-300', 'bg-pink-300', 'bg-yellow-300', 'bg-teal-300']
  return colors[Math.floor(Math.random() * colors.length)]
}
    const createNode = (id: string, label: string, fields: string[], depth = 0): string => {
      const nodeId = `${id}_${nodeCounter++}`
      nodes.push({
        id: nodeId,
        type: 'erTable',
        position: { x: 100 + depth * 350, y: yOffset },
        data: { label, fields, color: getRandomColor(), },
      })
      if (fields.length === 0) yOffset += 200 
      console.log(fields.length, "asdasdsada  ")
      // yOffset += Math.max(120, fields.length * 10 + 170)
      yOffset += 20
      return nodeId
    }

    const processProperties = (properties: any[], label: string, depth = 0): string => {
      const fields: string[] = []
      const nodeId = createNode(label, label, fields, depth)

      properties.forEach((prop: any, idx: number) => {
        const propName = prop.name || `prop${idx}`
        const type = prop.type?.type
        let fieldLabel = `${propName}: ${type}`
        let childId = ''

        if (type === 'object' && Array.isArray(prop.type?.properties)) {
          childId = processProperties(prop.type.properties, prop.type.name || propName, depth + 1)
        } else if (type === 'array' && prop.type.contains?.type === 'object') {
          fieldLabel = `${propName}: ${(prop.type.contains.name || propName)}[]`
          childId = processProperties(prop.type.contains.properties, prop.type.contains.name || propName, depth + 1)
        } else if (type === 'union' && Array.isArray(prop.type.types)) {
          fieldLabel = `${propName}: union`
          prop.type.types.forEach((unionType: any, idx: number) => {
            const enumName = prop.type.types?.[idx]?.name;
            const unionId = processProperties(unionType.properties || [], `${enumName}`, depth + 1)
            console.log('nodeId => ', nodeId, unionId)
            this.flowEdges.push({
              id: `e-${nodeId}-${childId}`,
              source: nodeId,
              sourceHandle: `out-${fields.length}`,
              target: unionId,
              targetHandle: 'in-0',
              type: 'default',
              animated: true,
              markerEnd: { type: MarkerType.Arrow },
            })
          })
        }
        else if (type === 'enum' && Array.isArray(prop.type?.values)) {
          fieldLabel = `${propName}: ${prop.type?.name || 'enum'}`

          const enumLabel = prop.type?.name || `${propName}_Enum`
          const enumFields = prop.type.values.map((val: string) => `• ${val}`)

          const enumNodeId = createNode(`${propName}_enum`, enumLabel, enumFields, depth + 1)

          this.flowEdges.push({
            id: `e-${nodeId}-${childId}`,
            source: nodeId,
            sourceHandle: `out-${fields.length}`,
            target: enumNodeId,
            targetHandle: 'in-0',
            type: 'default',
            animated: true,
            markerEnd: { type: MarkerType.Arrow },
          })
        }
        fields.push(fieldLabel)

        if (childId) {
          this.flowEdges.push({
            id: `e-${nodeId}-${childId}`,
            source: nodeId,
            sourceHandle: `out-${fields.length - 1}`,
            target: childId,
            targetHandle: 'in-0',
            type: 'default',
            animated: true,
            markerEnd: { type: MarkerType.Arrow },
          })
        }
      })

      return nodeId
    }
    console.log('entity => ', entity)
    processProperties(entity.properties, entity.name || 'Root')
    this.flowNodes = nodes

    this.$nextTick(() => {
      (this.$refs.flow as any)?.fitView?.()
    })
  }
}
</script>
