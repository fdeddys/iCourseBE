package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "m_outlet")
public class Outlet extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(50)")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
    private String id;

    @ManyToOne
    @JoinColumn(name = "outlet")
    private GroupOutlet groupOutlet;

    @Column(length = 150)
    private String name;

    @Column(length = 150)
    private String address1;

    @Column(length = 150)
    private String address2;

    @Column
    private BigDecimal registrationFee;

}
