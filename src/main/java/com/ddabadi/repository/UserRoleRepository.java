package com.ddabadi.repository;

import com.ddabadi.model.UserRole;
import com.ddabadi.model.compositekey.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("select r from UserRole r where r.userRoleId.userId = :userid ")
    List<UserRole> findByUserId(@Param("userid") Long userid);

    @Query("select r from UserRole r where r.userRoleId.roleId = :roleId ")
    List<UserRole> findByRoleId(@Param("roleId") Long roleId);

    @Query("select r from UserRole r where r.userRoleId.roleId = :roleId and r.userRoleId.userId = :userId ")
    Optional<UserRole> findByRoleIdAndUserId(@Param("roleId") Long roleId,
                                             @Param("userId") Long userId);

}
