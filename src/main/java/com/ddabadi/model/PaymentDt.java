package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.PaymentType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "payment_dt")
public class PaymentDt extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @ManyToOne
    @JoinColumn(name = "payment_hd_id")
    private PaymentHd paymentHd;

    @Column(length = 250)
    private String description;

    @Column
    private PaymentType paymentType;

    @Column(length = 6)
    private String paymentMonth;

    @Column
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "reg_id")
    private Registration registration;

}
