package com.ddabadi.repository;

import com.ddabadi.model.GroupOutlet;
import com.ddabadi.model.dto.OutletGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface GroupOutletRepository extends JpaRepository<GroupOutlet, String> {


    @Query(value = "select go from GroupOutlet go " +
            " where ( true = true ) " +
            " and  ((lower(go.name) like lower(:#{#filter.name})) or (null = :#{#filter.name}) ) ")
    Page<GroupOutlet> findByFilter(@Param("filter") OutletGroupDto filter, Pageable pageable);

}
