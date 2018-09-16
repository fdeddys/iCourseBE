package com.ddabadi.service.impl;

import com.ddabadi.model.Menu;
import com.ddabadi.model.Role;
import com.ddabadi.model.RoleMenu;
import com.ddabadi.model.User;
import com.ddabadi.model.compositekey.RoleMenuId;
import com.ddabadi.model.dto.RoleDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.RoleRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.util.SmStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements CustomService<Role> {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleMenuServiceImpl roleMenuService;

    @Autowired
    private MenuServiceImpl menuService;

    @Autowired
    private UserServiceImpl userService;

    private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Transactional(readOnly = true)
    public Role findOne(Long id) {
        log.info("Find one by Id  {}", id );

        Optional<Role> roleOptional = repository.findById(id);
        return roleOptional.orElseGet(Role::new);
    }

    @Transactional(readOnly = true)
    public Page<Role> findAllByPage(Integer page, Integer total) {
        log.debug("Find Pageable page {} , total {}", page, total);
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        return repository.findAll(pageRequest) ;
    }

    @Override
    public Role addnew(Role role) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RoleDto addnew(RoleDto roleDto) {

        try {
            Optional<Role> roleCheck =  repository.findByName(roleDto.getName());
            if(roleCheck.isPresent()){
                roleDto.setErrMsg("Role name already exsist ! ");
                return roleDto;
            }

            User user = userService.getCurUserAsObj();
            List<RoleMenu> roleMenus = new ArrayList<>();
            log.debug("New Record ");
            Role newRec = new Role();
            newRec.setName(roleDto.getName());
            newRec.setDescription(roleDto.getDescription());
            newRec.setUpdatedBy(user);
            newRec.setCreatedBy(user);
            newRec = repository.save(newRec);
            roleMenuService.save(roleMenus);
            roleDto.setId(newRec.getId());
        } catch (Exception e) {
            log.debug("{}", e.getMessage()) ;
            roleDto.setErrMsg(e.getMessage());
            new SmStockException("error " + e.getMessage());
        }
        return roleDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Role update(Role role, Long idOld) {
        Optional<Role> optionalRole = repository.findById(idOld);
        if (optionalRole.isPresent()){
            User user = userService.getCurUserAsObj();
            Role oldRole = optionalRole.get();
            oldRole.setName(role.getName());
            oldRole.setDescription(role.getDescription());
            oldRole.setUpdatedBy(user);
            return repository.save(oldRole);
        }else {
            return null;
        }
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    public void updateRoleActivate(Long roleid, Long menuid) {
        Optional<RoleMenu> roleMenuOptional = roleMenuService.findByRoleMenu(roleid, menuid);
        if (roleMenuOptional.isPresent()){
            User user = userService.getCurUserAsObj();
            RoleMenu roleMenu = roleMenuOptional.get();
            roleMenu.setStatus( roleMenu.getStatus().getEntityStatusCode() == 0 ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
            roleMenu.setUpdatedBy(user);
            roleMenuService.save(roleMenu);
        }
    }

    @Transactional(readOnly = true)
    public List<Role> findAll(){
        return repository.findAll();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Role updateMenu(Long id, List<Long> menuIds) {
        User user = userService.getCurUserAsObj();
        Date updateTime = new Date();
        Optional<Role> optionalRole = repository.findById(id);
        if (optionalRole.isPresent()){
            Role role = optionalRole.get();

            roleMenuService.deleteByRole(role.getId());

            List<RoleMenu> roleMenus = new ArrayList<>();
            menuIds.forEach(menuId -> {

                RoleMenuId roleMenuId = new RoleMenuId();
                roleMenuId.setMenuId(menuId);
                roleMenuId.setRoleId(role.getId());

                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setStatus(EntityStatus.ACTIVE);
                roleMenu.setRoleMenuId(roleMenuId);
                roleMenu.setCreatedBy(user);
                roleMenu.setCreatedAt(updateTime);
                roleMenu.setUpdatedAt(updateTime);
                roleMenu.setUpdatedBy(user);
                roleMenus.add(roleMenu);

            });


            roleMenuService.save(roleMenus);
            updateParent(roleMenus, user, updateTime);
            return role;
        }else {
            return null;
        }
    }

    private boolean checkIfMenuIdExsis(Long menuId, List<Long> menuIds) {
        return menuIds.contains(menuId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateParent(List<RoleMenu> roleMenuList, User user, Date updateTime){

        for(int i=0; i<= roleMenuList.size() -1; i++){

            addRoleParent(roleMenuList.get(i).getRoleMenuId().getMenuId(),
                    roleMenuList.get(i).getRoleMenuId().getRoleId(),
                    user,
                    updateTime);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRoleParent(Long menuId, Long roleId, User user , Date updateTime){
        RoleMenuId roleMenuId = new RoleMenuId();
        roleMenuId.setMenuId(menuId);
        roleMenuId.setRoleId(roleId);

        RoleMenu newRoleMenu = new RoleMenu();
        newRoleMenu.setStatus(EntityStatus.ACTIVE);
        newRoleMenu.setRoleMenuId(roleMenuId);
        newRoleMenu.setCreatedBy(user);
        newRoleMenu.setCreatedAt(updateTime);
        newRoleMenu.setUpdatedAt(updateTime);
        newRoleMenu.setUpdatedBy(user);
        roleMenuService.save(newRoleMenu);

        Menu menu = menuService.findOne(menuId);
        if (menu.getParentId() != 0 ){
            addRoleParent(menu.getParentId(), roleId, user, updateTime );
        }
    }

}
