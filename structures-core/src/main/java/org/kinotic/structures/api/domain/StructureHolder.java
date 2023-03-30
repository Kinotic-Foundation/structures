package org.kinotic.structures.api.domain;

import java.util.List;

public class StructureHolder {

    private Structure structure;
    private List<TraitHolder> traits;

    public StructureHolder(){}

    public StructureHolder(Structure structure, List<TraitHolder> traits) {
        this.structure = structure;
        this.traits = traits;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<TraitHolder> getTraits() {
        return traits;
    }

    public void setTraits(List<TraitHolder> traits) {
        this.traits = traits;
    }
}
