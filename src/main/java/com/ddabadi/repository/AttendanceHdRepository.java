package com.ddabadi.repository;

import com.ddabadi.model.AttendanceHd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AttendanceHdRepository extends JpaRepository<AttendanceHd, String> {

    @Query("select a from AttendanceHd  a join a.teacher t join a.room r join a.outlet o " +
            " where t.id = :teacherId and r.id = :roomId and o.id = :outletId " +
            " and a.attendanceDate = :attendanceDate and a.attendanceTime = :attendanceTime ")
    List<AttendanceHd> findAttendance(@Param("teacherId") String teacherId,
                                      @Param("roomId")String roomId,
                                      @Param("outletId")String outletId,
                                      @Param("attendanceDate")Date attendanceDate,
                                      @Param("attendanceTime")Date attendanceTime);
}
