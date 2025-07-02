import { markRaw, computed, type ComputedRef, reactive } from 'vue'
import type { Router, RouteRecordRaw, RouteLocationNormalized, NavigationGuardNext } from 'vue-router'
import { NavItem } from '@/components/NavItem'
import { createSimpleDataSource } from '@/util/dataSource'
import { ProjectService, StructureService, Structures, type ApplicationDTO } from '@kinotic/structures-api'
import type { IDataSource, Identifiable } from '@kinotic/continuum-client'
import { Pageable } from '@kinotic/continuum-client'

export interface IApplicationState {
    mainNavItems: NavItem[]
    bottomNavItems: NavItem[]
    selectedNavItem: NavItem | null
    breadcrumbItems: ComputedRef<NavItem[]>

    projectSource: IDataSource<Identifiable<string>> | null
    structureSource: IDataSource<Identifiable<string>> | null
    projectsCount: number
    structuresCount: number

    currentApplication: ApplicationDTO | null
    setCurrentApplication(app: ApplicationDTO): void
    fetchAndSetCurrentApplication(applicationId: string): Promise<void>

    loadProjects(applicationId: string): Promise<void>
    loadStructures(applicationId: string): Promise<void>
    loadAllApplications(): Promise<void>

    initialize(router: Router): void
}

class ApplicationState implements IApplicationState {
    public mainNavItems: NavItem[] = markRaw([])
    public bottomNavItems: NavItem[] = markRaw([])
    public selectedNavItem: NavItem | null = null
    private router!: Router
    private allNavItems: NavItem[] = []
    public allApplications: ApplicationDTO[] = []
    public breadcrumbItems: ComputedRef<NavItem[]>

    public projectSource: IDataSource<Identifiable<string>> | null = null
    public structureSource: IDataSource<Identifiable<string>> | null = null
    public projectsCount = 0
    public structuresCount = 0

    public currentApplication: ApplicationDTO | null = null

    constructor() {
        this.breadcrumbItems = computed(() => this.getBreadcrumbItems())
    }

    public initialize(router: Router): void {
        this.router = router

        if (router.options.routes) {
            router.options.routes.forEach(route => {
                if (this.showInMainNav(route)) {
                    const navItem = this.createNavItem(route, '')
                    this.mainNavItems.push(navItem)
                    this.allNavItems.push(navItem)
                }
                if (this.showInBottomNav(route)) {
                    const navItem = this.createNavItem(route, '')
                    this.bottomNavItems.push(navItem)
                    this.allNavItems.push(navItem)
                }
                if (this.showInBreadcrumbs(route)) {
                    this.allNavItems.push(this.createNavItem(route, ''))
                }
            })
        }

        router.beforeResolve((to: RouteLocationNormalized, _, next: NavigationGuardNext) => {
            this.updateSelectedNavItem(to.path)
            next()
        })

        this.updateSelectedNavItem(router.currentRoute.value.path)
    }

    public async loadProjects(applicationId: string): Promise<void> {
        const pageable = Pageable.create(0, 1000)
        const service = new ProjectService()
        const result = await service.findAllForApplication(applicationId, pageable)

        this.projectsCount = result.totalElements ?? 0
        this.projectSource = createSimpleDataSource(result.content ?? [])
    }

    public async loadStructures(applicationId: string): Promise<void> {
        const pageable = Pageable.create(0, 1000)
        const service = new StructureService()
        const result = await service.findAllForApplication(applicationId, pageable)

        this.structuresCount = result.totalElements ?? 0
        this.structureSource = createSimpleDataSource(result.content ?? [])
    }

    public async loadAllApplications(): Promise<void> {
        try {
            const service = Structures.getApplicationService()
            const pageable = Pageable.create(0, 1000)
            const result = await service.findAll(pageable)
            this.allApplications = result.content ?? []
        } catch (e) {
            console.error('[ApplicationState] Failed to load all applications:', e)
            this.allApplications = []
        }
    }

    public setCurrentApplication(app: ApplicationDTO): void {
        this.currentApplication = app
    }

    public async fetchAndSetCurrentApplication(applicationId: string): Promise<void> {
        const service = Structures.getApplicationService()
        const app = await service.findById(applicationId)
        this.currentApplication = app
    }

    // ---------- Utility methods ----------
    private createNavItem(route: RouteRecordRaw, parentPath: string): NavItem {
        const fullPath = this.resolveFullPath(route.path, parentPath)
        const navItem = new NavItem(
            route.meta?.icon as string || '',
            route.meta?.label as string || route.name?.toString() || '',
            fullPath,
            async () => { await this.router.push(fullPath) }
        )

        if (route.children?.length) {
            route.children.forEach(child => {
                if (this.showInMainNav(child) || this.showInBottomNav(child) || this.showInBreadcrumbs(child)) {
                    const childItem = this.createNavItem(child, fullPath)
                    navItem.addChild(childItem)
                    this.allNavItems.push(childItem)
                }
            })
        }

        return navItem
    }

    private resolveFullPath(routePath: string, parentPath: string): string {
        if (routePath.startsWith('/') || !parentPath) {
            return routePath
        }
        return `${parentPath}${parentPath.endsWith('/') ? '' : '/'}${routePath}`
    }

    private updateSelectedNavItem(path: string): void {
        let bestMatch: NavItem | null = null
        let longestMatchLength = 0

        const checkNavItem = (navItem: NavItem): void => {
            if (path.startsWith(navItem.path) && navItem.path.length > longestMatchLength) {
                bestMatch = navItem
                longestMatchLength = navItem.path.length
            }
            navItem.children.forEach(checkNavItem)
        }

        [...this.mainNavItems, ...this.bottomNavItems].forEach(checkNavItem)
        this.selectedNavItem = bestMatch
    }

    private getBreadcrumbItems(): NavItem[] {
        if (!this.router) return []
        const currentPath = this.router.currentRoute.value.path
        const items: NavItem[] = []

        this.allNavItems.forEach(navItem => {
            if (currentPath.startsWith(navItem.path) && this.showInBreadcrumbsForPath(navItem.path)) {
                items.push(navItem)
            }
        })

        return items.sort((a, b) => a.path.length - b.path.length)
    }

    private showInMainNav(routeConfig: RouteRecordRaw): boolean {
        return routeConfig.meta?.showInMainNav === true
    }

    private showInBottomNav(routeConfig: RouteRecordRaw): boolean {
        return routeConfig.meta?.showInBottomNav === true
    }

    private showInBreadcrumbs(routeConfig: RouteRecordRaw): boolean {
        return routeConfig.meta?.showInBreadcrumbs === true
    }

    private showInBreadcrumbsForPath(path: string): boolean {
        const route = this.router.getRoutes().find(r => r.path === path)
        return route?.meta?.showInBreadcrumbs === true
    }
}

export const APPLICATION_STATE: IApplicationState = reactive(new ApplicationState())
