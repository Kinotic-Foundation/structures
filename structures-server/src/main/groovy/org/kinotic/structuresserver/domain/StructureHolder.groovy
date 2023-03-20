package org.kinotic.structuresserver.domain

import org.kinotic.structures.api.domain.Structure

class StructureHolder {

    Structure structure
    List<TraitHolder> traits

    StructureHolder(){}

    StructureHolder(Structure structure, List<TraitHolder> traits) {
        this.structure = structure
        this.traits = traits
    }

    Structure getStructure() {
        return structure
    }

    void setStructure(Structure structure) {
        this.structure = structure
    }

    List<TraitHolder> getTraits() {
        return traits
    }

    void setTraits(List<TraitHolder> traits) {
        this.traits = traits
    }
}
