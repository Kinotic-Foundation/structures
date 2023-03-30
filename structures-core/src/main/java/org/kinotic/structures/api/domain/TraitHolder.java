package org.kinotic.structures.api.domain;

public class TraitHolder {

    private int order;
    private String fieldName;
    private Trait fieldTrait;

    public TraitHolder(){}

    public TraitHolder(int order, String fieldName, Trait fieldTrait) {
        this.order = order;
        this.fieldName = fieldName;
        this.fieldTrait = fieldTrait;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Trait getFieldTrait() {
        return fieldTrait;
    }

    public void setFieldTrait(Trait fieldTrait) {
        this.fieldTrait = fieldTrait;
    }
}
