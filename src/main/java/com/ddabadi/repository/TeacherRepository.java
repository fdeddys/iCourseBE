package com.ddabadi.repository;

import com.ddabadi.model.Student;
import com.ddabadi.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

    @Query(value = "select t from Teacher t " +
            "where substring(t.name,1) = :karakterNama ")
    Page<Teacher> findByKarakter(@Param("karakterNama")String karakterNama, Pageable pageable);

}
