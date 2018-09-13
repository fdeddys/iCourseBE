package com.ddabadi.model.enu;

public enum  PaymentType {
    MONTHLY(0),
    MONTHLY_FREE_REG(1),
    REGISTRATION (2);

    private Integer paymenTypeCode;

    PaymentType(Integer paymenTypeCode) {
        this.paymenTypeCode = paymenTypeCode;
    }

    public Integer getPaymenTypeCode() {
        return paymenTypeCode;
    }

}
