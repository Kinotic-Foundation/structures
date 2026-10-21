import { markRaw, type Component } from 'vue'
import { Crosshair, FolderGit2, Globe, ListTree, Package, Server } from '@lucide/vue'
import { TaskIcon, TaskViews } from '@kinotic-ai/management-api'
import WorkloadLogTaskView from '../WorkloadLogTaskView.vue'
import ProjectArtifactsTaskView from '../deploy/ProjectArtifactsTaskView.vue'
import SbomGenerationTaskView from '../deploy/SbomGenerationTaskView.vue'
import type { JobTaskNode } from './JobTaskNode'

/**
 * How the job run views render what tasks declare: the icon for each TaskIcon, and the component
 * for each TaskViews kind. Every view component takes the view's data, the task's node and the
 * run. TaskIcon.DEFAULT, absent here, renders the default icon, and a kind missing here renders no
 * detail pane.
 */
export const TASK_ICONS: Partial<Record<TaskIcon, Component>> = {
  [TaskIcon.TARGET]: markRaw(Crosshair),
  [TaskIcon.GIT]: markRaw(FolderGit2),
  [TaskIcon.PACKAGE]: markRaw(Package),
  [TaskIcon.SERVER]: markRaw(Server),
  [TaskIcon.GLOBE]: markRaw(Globe),
  [TaskIcon.SBOM]: markRaw(ListTree)
}

export const TASK_VIEWS: Record<string, Component> = {
  [TaskViews.WORKLOAD_LOG]: markRaw(WorkloadLogTaskView),
  [TaskViews.PROJECT_ARTIFACTS]: markRaw(ProjectArtifactsTaskView),
  [TaskViews.SBOM_GENERATION]: markRaw(SbomGenerationTaskView)
}

/** The component rendering the task's current view, or null when it shows none this console renders. */
export function taskViewComponent(node: JobTaskNode): Component | null {
  return (node.view && TASK_VIEWS[node.view.kind]) ?? null
}
