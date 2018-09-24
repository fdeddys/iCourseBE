package com.ddabadi.repository;

import com.ddabadi.model.Outlet;
import com.ddabadi.model.dto.OutletDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutletRepository extends JpaRepository<Outlet, String> {

    @Query(value = "select o from Outlet o " +
            " where ( true = true ) " +
            " and  ((lower(o.name) like lower(:#{#filter.name})) or (null = :#{#filter.name}) ) ")
    Page<Outlet> findByFilter(@Param("filter") OutletDto filter, Pageable pageable);

}
