package com.ddabadi.model;


import com.ddabadi.model.auditor.Audit;
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
@Table(name = "registration")
public class Registration extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(nullable = false)
    private String id;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date")
    private Date regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @Column(name = "officer")
    private String officer;

    @Column(name = "hari")
    private String courseDate;

    @Column(name = "jam")
    private String CourseTime;
}
