package org.kinotic.structuresserver.domain

import org.kinotic.structures.api.domain.Trait


class TraitHolder {

    int order
    String fieldName
    Trait fieldTrait

    TraitHolder(){}

    TraitHolder(int order, String fieldName, Trait fieldTrait) {
        this.order = order
        this.fieldName = fieldName
        this.fieldTrait = fieldTrait
    }

    int getOrder() {
        return order
    }

    void setOrder(int order) {
        this.order = order
    }

    String getFieldName() {
        return fieldName
    }

    void setFieldName(String fieldName) {
        this.fieldName = fieldName
    }

    Trait getFieldTrait() {
        return fieldTrait
    }

    void setFieldTrait(Trait fieldTrait) {
        this.fieldTrait = fieldTrait
    }
}
