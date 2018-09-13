package com.ddabadi.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "attendance_dt")
public class AttendanceDt implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @ManyToOne
    @JoinColumn(name = "attendanceDt")
    private AttendanceHd attendanceHd;

    @ManyToOne
    private Student student;

    @Column
    private String timeStart;

    @Column
    private String timeFinish;
}
