package com.ddabadi.repository;

import com.ddabadi.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {


    @Query(value = "select s from Student s" +
            " where substring(s.studentCode,4) = :tahunBulan")
    Page<Student> findByTahunBulan(@Param("tahunBulan") String tahunBulan,
                                   Pageable pageable);

}
