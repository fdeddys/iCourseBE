package com.ddabadi.repository;

import com.ddabadi.model.RoleMenu;
import com.ddabadi.model.compositekey.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuId> {

    @Query("select rm from RoleMenu rm where rm.roleMenuId.roleId = :roleid and rm.roleMenuId.menuId = :menuid")
    Optional<RoleMenu> findByRoleMenu(@Param("roleid") Long roleid,
                                      @Param("menuid") Long menuid);


    @Query("select rm from RoleMenu rm where rm.roleMenuId.roleId = :roleid")
    List<RoleMenu> findByRole(@Param("roleid") Long roleId);

}
