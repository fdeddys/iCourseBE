package com.ddabadi.repository;

import com.ddabadi.model.Student;
import com.ddabadi.model.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {


    @Query(value = "select s from Student s" +
            " where substring(s.studentCode,1,4) = :tahunBulan")
    Page<Student> findByTahunBulan(@Param("tahunBulan") String tahunBulan,
                                   Pageable pageable);

    @Query(value = "select s from Student s  " +
            " where ( true = true ) " +
            " and  ((lower(s.name) like lower(:#{#filter.name}))   or (null = :#{#filter.name}) ) " +
            " and  ((s.outlet.id = :#{#outletId} )                 or (null = :#{#outletId})    ) ")
    Page<Student> findByFilter(@Param("filter") StudentDto filterDto,
                               @Param("outletId") String outletId,
                               Pageable pageable);

}
