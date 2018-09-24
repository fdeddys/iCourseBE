package com.ddabadi.repository;

import com.ddabadi.model.ErrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorCodeRepository extends JpaRepository<ErrCode, Long > {

    ErrCode findByCode(String code);

}
