package com.ddabadi.repository;

import com.ddabadi.model.Menu;
import com.ddabadi.model.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select rm from RoleMenu rm where rm.roleMenuId.roleId = :roleid ")
    List<RoleMenu> findByIdRole(@Param("roleid") Long roleid);

    @Query("select rm from RoleMenu rm where ( rm.roleMenuId.roleId = :roleId ) and ( rm.roleMenuId.roleId not in :menuId ) ")
    List<RoleMenu> findByExcRoleId(@Param("menuId") List<Long> menuId,
                                   @Param("roleId") Long roleId);

    @Query(nativeQuery = true, value = "select d.id, d.name, d.description, link, icon, parent_id from m_users a join " +
            " m_role_user b on (a.id=b.user_id) join " +
            " m_role_menu c on(b.role_id=c.role_id) join " +
            " m_menus d on(c.menu_id=d.id) " +
            " and ( link <> '' or :all isnull ) " +
            " where a.user_name= :username and d.status='1' group by d.id,a.user_name")
    List<Object> findUserMenuByUsername(@Param("username") String username, @Param("all") String all);

    List<Menu> findByStatus(Integer status);

}
