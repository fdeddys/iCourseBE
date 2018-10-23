package com.ddabadi.repository;

import com.ddabadi.model.StudentDt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentDtRepository extends JpaRepository<StudentDt, String> {

    @Query(value = "select s from StudentDt s " +
            " join s.student st " +
            " where ( true = true ) " +
            " and  (st.id = :studentId )" +
            " and  ((st.outlet.id = :#{#outletId} ) or (null = :#{#outletId})    ) ")
    Optional<StudentDt> findByIdStudent(@Param("studentId") String studentId);

}
