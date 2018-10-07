package com.ddabadi.repository;

import com.ddabadi.model.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDetailRepository extends JpaRepository<StudentDetail, String> {
}
