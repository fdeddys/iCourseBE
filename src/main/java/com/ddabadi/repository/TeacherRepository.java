package com.ddabadi.repository;

import com.ddabadi.model.Student;
import com.ddabadi.model.Teacher;
import com.ddabadi.model.dto.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

    @Query(value = "select t from Teacher t " +
            "where SUBSTRING(t.teacherCode,1,1) = :karakterNama " +
            "order by teacherCode desc ")
    Page<Teacher> findByKarakter(@Param("karakterNama")String karakterNama,
                                 Pageable pageable);

    @Query(value = "select t from Teacher t " +
            " where ( true = true ) " +
            " and  ((lower(t.name) like lower(:#{#filter.name}))   or (null = :#{#filter.name}) ) " +
            " and  ((t.outlet.id = :#{#outletId} )                 or (null = :#{#outletId})    ) ")
    Page<Teacher> findByFilter(@Param("filter") TeacherDto filterDto,
                               @Param("outletId") String outletId,
                               Pageable pageable);

}
