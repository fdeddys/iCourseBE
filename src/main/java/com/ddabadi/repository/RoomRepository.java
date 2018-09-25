package com.ddabadi.repository;

import com.ddabadi.model.Outlet;
import com.ddabadi.model.Room;
import com.ddabadi.model.dto.RoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, String> {

    @Query(value = "select r from Room r " +
            " where ( true = true ) " +
            " and  ((lower(r.name) like lower(:#{#filter.name}))   or (null = :#{#filter.name}) ) " +
            " and  ((r.outlet.id = :#{#outletId} )                 or (null = :#{#outletId})    ) ")
    Page<Room> findByFilter(@Param("filter") RoomDto filter,
                            @Param("outletId") String outletId,
                            Pageable pageable);

    Page<Room> findByOutlet(Outlet outlet, Pageable pageable);
}
