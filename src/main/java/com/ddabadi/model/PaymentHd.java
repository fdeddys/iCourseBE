package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.PaymentStatus;
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
@Table(name = "payment_hd")
public class PaymentHd extends Audit implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @Column(length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column
    private String paymentNumber;

    @ManyToOne
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @Column
    private PaymentStatus paymentStatus;

    @Column
    private BigDecimal total;

    @PrePersist
    public void prePersist(){
        this.paymentStatus = PaymentStatus.OUTSTANDING;
        this.total = BigDecimal.ZERO;
        this.setUpdatedAt(new Date());
        this.setCreatedAt(new Date());
    }

}
