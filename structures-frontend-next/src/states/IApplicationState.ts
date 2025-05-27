import { markRaw, shallowReactive, computed, type ComputedRef } from 'vue'
import type { Router, RouteRecordRaw, RouteLocationNormalized, NavigationGuardNext } from 'vue-router'
import { NavItem } from '@/components/NavItem'

export interface IApplicationState {
    mainNavItems: NavItem[]
    bottomNavItems: NavItem[]
    selectedNavItem: NavItem | null
    breadcrumbItems: ComputedRef<NavItem[]>
    initialize(router: Router): void
}

class ApplicationState implements IApplicationState {
    public mainNavItems: NavItem[] = markRaw([])
    public bottomNavItems: NavItem[] = markRaw([])
    public selectedNavItem: NavItem | null = null
    private router!: Router
    private allNavItems: NavItem[] = []

    public breadcrumbItems: ComputedRef<NavItem[]>

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

        router.beforeResolve((to: RouteLocationNormalized, _: RouteLocationNormalized, next: NavigationGuardNext) => {
            this.updateSelectedNavItem(to.path)
            next()
        })

        this.updateSelectedNavItem(router.currentRoute.value.path)
    }

    private createNavItem(route: RouteRecordRaw, parentPath: string): NavItem {
        const fullPath = this.resolveFullPath(route.path, parentPath)

        const navItem = new NavItem(
            route.meta?.icon as string || '',
            route.meta?.label as string || route.name?.toString() || '',
            fullPath,
            async () => {
                await this.router.push(fullPath)
            }
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
        const normalizedPath = path
        let bestMatch: NavItem | null = null
        let longestMatchLength = 0

        const checkNavItem = (navItem: NavItem): void => {
            const navPath = navItem.path

            if (normalizedPath.startsWith(navPath) && navPath.length > longestMatchLength) {
                bestMatch = navItem
                longestMatchLength = navPath.length
            }

            navItem.children.forEach(child => checkNavItem(child))
        }

        [...this.mainNavItems, ...this.bottomNavItems].forEach(navItem => checkNavItem(navItem))
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
        return routeConfig?.meta?.showInMainNav === true
    }

    private showInBottomNav(routeConfig: RouteRecordRaw): boolean {
        return routeConfig?.meta?.showInBottomNav === true
    }

    private showInBreadcrumbs(routeConfig: RouteRecordRaw): boolean {
        return routeConfig?.meta?.showInBreadcrumbs === true
    }

    private showInBreadcrumbsForPath(path: string): boolean {
        const route = this.router.getRoutes().find(r => r.path === path)
        return route?.meta?.showInBreadcrumbs === true
    }
}

export const APPLICATION_STATE: IApplicationState = shallowReactive(new ApplicationState())