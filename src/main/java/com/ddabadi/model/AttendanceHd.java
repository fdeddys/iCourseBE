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
@EqualsAndHashCode
@ToString
@Table(name = "attendance_hd")
public class AttendanceHd extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column
    @Temporal(TemporalType.DATE)
    private Date attendanceDate;

    @Column
    @Temporal(TemporalType.TIME)
    private Date attendanceTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="outlet_id")
    private Outlet outlet;
}
