import { markRaw, shallowReactive } from 'vue'
import type { Router, RouteRecordRaw, RouteLocationNormalized, NavigationGuardNext } from 'vue-router'
import { NavItem } from '@/components/NavItem'

export interface IApplicationState {
    /**
     * The main navigation items
     */
    mainNavItems: NavItem[]

    /**
     * The bottom navigation items
     */
    bottomNavItems: NavItem[]

    /**
     * The currently selected navigation item
     */
    selectedNavItem: NavItem | null

    /**
     * Must be called with a configured VueRouter
     * @param router to initialize ths with
     */
    initialize(router: Router): void
}

class ApplicationState implements IApplicationState {
    public mainNavItems: NavItem[] = markRaw([])
    public bottomNavItems: NavItem[] = markRaw([])  // Added bottom nav items
    public selectedNavItem: NavItem | null = null
    private router!: Router

    public initialize(router: Router): void {
        this.router = router

        // Build navigation items from routes
        if (router.options.routes) {
            router.options.routes.forEach(route => {
                if (this.showInMainNav(route)) {
                    this.mainNavItems.push(this.createNavItem(route))
                }
                if (this.showInBottomNav(route)) {
                    this.bottomNavItems.push(this.createNavItem(route))
                }
            })
        }

        // Handle navigation and initial state
        router.beforeResolve((to: RouteLocationNormalized, _: RouteLocationNormalized, next: NavigationGuardNext) => {
            this.updateSelectedNavItem(to.path)
            next()
        })

        // Set initial state
        this.updateSelectedNavItem(router.currentRoute.value.path)
    }

    private createNavItem(route: RouteRecordRaw): NavItem {
        const navItem = new NavItem(
            route.meta?.icon as string || '',
            route.meta?.label as string || route.name?.toString() || '',
            route.path,
            async () => {
                await this.router.push(route.path)
            }
        )

        // Add child nav items
        if (route.children?.length) {
            route.children.forEach(child => {
                if (this.showInMainNav(child) || this.showInBottomNav(child)) {
                    navItem.addChild(this.createNavItem(child))
                }
            })
        }

        return navItem
    }

    private updateSelectedNavItem(path: string): void {
        const normalizedPath = path.replace(/\/$/, '')
        let bestMatch: NavItem | null = null
        let longestMatchLength = 0

        const checkNavItem = (navItem: NavItem): void => {
            const navPath = navItem.path.replace(/\/$/, '')

            if (normalizedPath.startsWith(navPath) && navPath.length > longestMatchLength) {
                bestMatch = navItem
                longestMatchLength = navPath.length
            }

            navItem.children.forEach(child => checkNavItem(child))
        }

        // Check both main and bottom nav items
        [...this.mainNavItems, ...this.bottomNavItems].forEach(navItem => checkNavItem(navItem))

        this.selectedNavItem = bestMatch
    }

    private showInMainNav(routeConfig: RouteRecordRaw): boolean {
        return routeConfig?.meta?.showInMainNav === true
    }

    private showInBottomNav(routeConfig: RouteRecordRaw): boolean {
        return routeConfig?.meta?.showInBottomNav === true
    }
}

export const APPLICATION_STATE: IApplicationState = shallowReactive(new ApplicationState())