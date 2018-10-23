package com.ddabadi.repository;

import com.ddabadi.model.Registration;
import com.ddabadi.model.dto.RegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistrationRepository extends JpaRepository<Registration, String> {

    @Query(value = "select r from Registration r " +
            " where substring(r.registrationNum,1,2) = :tahun")
    Page<Registration> findByTahun(@Param("tahun") String tahun,
                                   Pageable pageable);

    @Query(value = "select r from Registration r " +
            " where ( true = true ) " +
            " and  ((lower(r.officer) like lower(:#{#filter.officer}))   or (null = :#{#filter.officer}) ) " +
            " and  ((r.outlet.id = :#{#outletId} )                 or (null = :#{#outletId})    ) ")
    Page<Registration> findByFilter(@Param("filter") RegistrationDto filter,
                                    @Param("outletId") String outletId,
                                    Pageable pageable);
}
