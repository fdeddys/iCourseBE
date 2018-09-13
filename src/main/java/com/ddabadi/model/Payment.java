package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.PaymentStatus;
import com.ddabadi.model.enu.PaymentType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "payment")
public class Payment extends Audit implements Serializable{

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
    private PaymentType paymentType;

    @Column
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(length = 6)
    private String paymentMonth;
    // MMyyyy

    @ManyToOne
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @Column
    private PaymentStatus paymentStatus;

}
