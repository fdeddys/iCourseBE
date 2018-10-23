package com.ddabadi.model.dto;

import com.ddabadi.model.PaymentDt;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDtDto extends PaymentDt implements Serializable {

    private String paymentHdId;

    // payment for registration
    private String registrationId;

    // payment for 6 month free 1 month
    private String DateStartFreeReg;

    private String errCode;
    private String errDesc;
//    private String studentId;

}
