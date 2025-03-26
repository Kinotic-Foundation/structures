/**
 * Represents a navigation item in the navigation bar.
 */
export class NavItem {

    public icon: string
    public label: string
    public parent: NavItem | null = null
    public children: NavItem[] = []
    private readonly navigateFunction: () => Promise<void>

    constructor(icon: string,
                label: string,
                navigateFunction: () => Promise<void>) {
        this.icon = icon
        this.label = label
        this.navigateFunction = navigateFunction
    }

    public navigate(): Promise<void> {
        return this.navigateFunction()
    }

    public addChild(child: NavItem): void {
        child.parent = this
        this.children.push(child)
    }

}