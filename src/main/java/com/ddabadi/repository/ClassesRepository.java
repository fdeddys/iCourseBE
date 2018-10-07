package com.ddabadi.repository;

import com.ddabadi.model.Classes;
import com.ddabadi.model.Outlet;
import com.ddabadi.model.dto.ClassesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassesRepository extends JpaRepository<Classes, String> {

    @Query(value = "select c from Classes c " +
            " where ( true = true ) " +
            " and  ((lower(c.name) like lower(:#{#filter.name}))   or (null = :#{#filter.name}) ) " +
            " and  ((c.outlet.id = :#{#outletId} )                 or (null = :#{#outletId})    ) ")
    Page<Classes> findByFilter(@Param("filter") ClassesDto filter,
                               @Param("outletId") String outletId,
                               Pageable pageable);

    Page<Classes> findByOutlet(Outlet outlet, Pageable pageable);

}

