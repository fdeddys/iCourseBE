package com.ddabadi.model;

import com.ddabadi.model.enu.EntityStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "m_teacher")
public class Teacher extends Person implements Serializable {

    // A99999
    @Column(length = 5)
    private String teacherCode;

    @Column
    private EntityStatus status;

    @ManyToOne
    @JoinColumn(name = "person")
    private Outlet outlet;

    @Column(name = "gaji_pokok")
    private BigDecimal baseSalary;

    @Column(name = "tunjangan")
    private BigDecimal allowance;

}
