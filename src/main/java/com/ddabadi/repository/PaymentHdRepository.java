package com.ddabadi.repository;

import com.ddabadi.model.PaymentHd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentHdRepository extends JpaRepository<PaymentHd, String> {

    @Query(value = "select p from PaymentHd p " +
            "  where substring(p.paymentNumber,2,4) = :tahunBulan ")
    Page<PaymentHd> findByTahunBulan(@Param("tahunBulan") String tahunBulan,
                                    Pageable pageable);
}
