package com.ddabadi.model.enu;

public enum PaymentStatus {

    OUTSTANDING(0),
    APPROVED(1),
    CANCELLED (2);

    private Integer paymenStatusCode;

    PaymentStatus(Integer paymenTypeCode) {
        this.paymenStatusCode = paymenStatusCode;
    }

    public Integer getPaymenStatusCode() {
        return paymenStatusCode;
    }

}
