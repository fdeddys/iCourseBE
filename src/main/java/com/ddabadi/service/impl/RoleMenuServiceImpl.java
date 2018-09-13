package com.ddabadi.service.impl;

import com.ddabadi.model.RoleMenu;
import com.ddabadi.model.User;
import com.ddabadi.model.compositekey.RoleMenuId;
import com.ddabadi.model.dto.RoleMenuDto;
import com.ddabadi.repository.RoleMenuRepository;
import com.ddabadi.service.CustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleMenuServiceImpl implements CustomService<RoleMenu> {

    @Autowired
    private RoleMenuRepository repository;
    @Autowired
    private UserServiceImpl userService;

    private static Logger Log = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

    @Transactional(readOnly = true)
    public Page<RoleMenu> findAllByPage(Integer page, Integer total) {
        Log.debug("Find Pageable page {} , total {}", page, total);
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        return repository.findAll(pageRequest) ;
    }

    @Override
    public RoleMenu addnew(RoleMenu roleMenu) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addnew(RoleMenuDto roleMenuDto) {
        Log.debug("New Record ");
        roleMenuDto.getMenuId().forEach(menuId -> {
            User user = userService.getCurUserAsObj();
            RoleMenu newRec = new RoleMenu();
            RoleMenuId roleMenuId = new RoleMenuId(menuId, roleMenuDto.getRoleId());
            newRec.setRoleMenuId(roleMenuId);
            newRec.setUpdatedBy(user);
            newRec.setCreatedBy(user);
            repository.save(newRec);
        });
    }

    @Override
    public RoleMenu update(RoleMenu roleMenu, Long idOld) {
        return null;
    }

    @Override
    public RoleMenu update(RoleMenu roleMenu) {
        return null;
    }

    @Transactional(readOnly = false)
    public RoleMenu update(RoleMenuId idOld, RoleMenu roleMenu) {
        Optional<RoleMenu> optionalOldRec = repository.findById(idOld);

        if (optionalOldRec.isPresent()){
            User user = userService.getCurUserAsObj();
            RoleMenu oldRole = optionalOldRec.get();
            oldRole.setStatus(roleMenu.getStatus());
            oldRole.setUpdatedBy(user);
            return repository.save(oldRole);
        }else {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RoleMenu> save(List<RoleMenu> roleMenus) {
        return repository.saveAll(roleMenus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RoleMenu save(RoleMenu roleMenus) {
        return repository.save(roleMenus);
    }

    @Transactional(readOnly = true)
    public Optional<RoleMenu> findByRoleMenu(Long roleid, Long menuid) {
        return repository.findByRoleMenu(roleid, menuid);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByRole(Long roleId) {

        List<RoleMenu> roleMenus = repository.findByRole(roleId);
        repository.deleteAll(roleMenus);

    }

}
