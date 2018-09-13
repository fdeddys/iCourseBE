package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.EntityStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "m_student_Detail")
public class StudentDetail extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classcourse_id")
    private Classcourse classcourse;

    @Column(name = "status")
    private EntityStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

}
