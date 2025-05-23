<script lang="ts">
import { Vue, Prop, Emit, Component } from 'vue-facing-decorator'

import { VueFlow, type Node, type Edge, MarkerType } from '@vue-flow/core'
import { Background } from '@vue-flow/background'
import { Controls } from '@vue-flow/controls'
import { MiniMap } from '@vue-flow/minimap'
import ERTable from '@/components/modals/ERTable.vue'

import '@vue-flow/core/dist/style.css'
import '@vue-flow/core/dist/theme-default.css'

@Component({
  components: {
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
      const estimatedHeight = Math.max(150, 50 + fields.length * 20)

      nodes.push({
        id: nodeId,
        type: 'erTable',
        position: { x: 100 + depth * 350, y: yOffset },
        data: { label, fields, color: getRandomColor() },
      })

      yOffset += estimatedHeight + 40
      return nodeId
    }
    const processedStructures = new Map<string, string>()

    const processProperties = (properties: any[], label: string, depth = 0): string => {
      const structureKey = JSON.stringify(properties) + label

      if (processedStructures.has(structureKey)) {
        return processedStructures.get(structureKey)!
      }

      const fields: string[] = []
      const nodeId = createNode(label, label, fields, depth)
      processedStructures.set(structureKey, nodeId)

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
          prop.type.types.forEach((unionType: any, uIdx: number) => {
            const enumName = unionType.name || `${propName}_Union_${uIdx}`
            const unionId = processProperties(unionType.properties || [], enumName, depth + 1)

            this.flowEdges.push({
              id: `e-${nodeId}-union-${uIdx}`,
              source: nodeId,
              sourceHandle: `out-${fields.length}`,
              target: unionId,
              targetHandle: 'in-0',
              type: 'default',
              animated: true,
              markerEnd: { type: MarkerType.Arrow },
            })
          })
        } else if (type === 'enum' && Array.isArray(prop.type?.values)) {
          fieldLabel = `${propName}: ${prop.type?.name || 'enum'}`
          const enumLabel = prop.type?.name || `${propName}_Enum`
          const enumFields = prop.type.values.map((val: string) => `• ${val}`)
          const enumKey = JSON.stringify(enumFields) + enumLabel

          let enumNodeId: string
          if (processedStructures.has(enumKey)) {
            enumNodeId = processedStructures.get(enumKey)!
          } else {
            enumNodeId = createNode(`${propName}_enum`, enumLabel, enumFields, depth + 1)
            processedStructures.set(enumKey, enumNodeId)
          }

          this.flowEdges.push({
            id: `e-${nodeId}-${enumNodeId}`,
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

    processProperties(entity.properties, entity.name || 'Root')
    this.flowNodes = nodes

    this.$nextTick(() => {
      (this.$refs.flow as any)?.fitView?.()
    })
  }
}
</script>
<template>
  <div v-show="visible" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
    <div class="relative w-full h-screen bg-white shadow-lg overflow-hidden">
      <div class="flex items-center justify-between p-4 border-b border-gray-200">
        <h3 class="text-xl font-semibold text-gray-900">Structure Details</h3>
        <button @click="onHide"
          class="text-gray-400 hover:text-gray-900 hover:bg-gray-200 rounded-lg text-sm w-8 h-8 flex items-center justify-center">
          <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
          </svg>
        </button>
      </div>

      <div class="h-full">
        <VueFlow ref="flow" :nodes="flowNodes" :edges="flowEdges" :node-types="nodeTypes">
          <Background pattern-color="#ccc" :gap="20" />
          <MiniMap />
          <Controls position="top-left" />
        </VueFlow>
      </div>
    </div>
  </div>
</template>
<style>
.p-row-even {
  cursor: pointer !important;
}

.p-row-odd {
  cursor: pointer !important;

}
</style>