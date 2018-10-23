package com.ddabadi.model.dto;

import com.ddabadi.model.PaymentHd;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaymentHdDto extends PaymentHd implements Serializable {

    private String studentId;
    private String errCode;
    private String errDesc;

    // untuk save header dan detail bersamaan
    private List<PaymentDtDto> paymentDtDtoList;

}
