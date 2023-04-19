import {Structure} from '@/frontends/structures-admin/pages/structures/structures/Structure'
import {TraitHolder} from '@/frontends/structures-admin/pages/structures/structures/TraitHolder'

export class StructureHolder {

    public structure: Structure
    public traits: TraitHolder[]

    constructor(structure: Structure, traits: TraitHolder[]) {
        this.structure = structure
        this.traits = traits
    }
}
