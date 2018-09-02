package com.ddabadi.model.enu;

public enum EntityStatus {

    INACTIVE(0),
    ACTIVE (1);


    private Integer entityStatusCode;

    EntityStatus(Integer entityStatusCode) {
        this.entityStatusCode = entityStatusCode;
    }

    public Integer getEntityStatusCode() {
        return entityStatusCode;
    }

}
