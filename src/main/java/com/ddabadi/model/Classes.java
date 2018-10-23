package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "m_class")
public class Classes extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @Column(length = 10)
    private String classCode;

    @Column(length = 150)
    private String name;

    @Column
    private BigDecimal monthlyFee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @Column
    private EntityStatus status;

    @JsonProperty
    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }
}
