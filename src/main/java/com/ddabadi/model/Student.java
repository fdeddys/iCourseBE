package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.EntityStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "m_student")
public class Student extends Person implements Serializable {


    @Column
    private String studendCode;

    @Column(length = 150)
    private String school;

    @Column
    private EntityStatus status;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private List<StudentDetail> studentDetails = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;


}
