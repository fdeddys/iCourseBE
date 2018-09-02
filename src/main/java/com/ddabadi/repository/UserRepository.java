package com.ddabadi.repository;

import com.ddabadi.model.User;
import com.ddabadi.model.dto.UserDto;
import com.ddabadi.model.enu.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByNameAndStatus(String username, EntityStatus status);

    @Query(value = "select u from User u where " +
            " lower(u.name)          like lower(:#{#filter.name}) " +
            " and lower(u.email)     like lower(:#{#filter.email}) " +
            " and lower(u.firstName) like lower(:#{#filter.firstName}) " +
            " and lower(u.lastName)  like lower(:#{#filter.lastName}) " +
            " and ((u.status= :#{#filter.status}) or (null = :#{#filter.status}))  ")
    Page<User> findByFilter(@Param("filter") UserDto filter,
                            Pageable pageable);

}
