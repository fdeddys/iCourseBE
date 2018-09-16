package com.ddabadi.service.impl;

import com.ddabadi.model.Menu;
import com.ddabadi.model.RoleMenu;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.RoleMenuViewDto;
import com.ddabadi.model.dto.UserMenuDto;
import com.ddabadi.repository.MenuRepository;
import com.ddabadi.service.CustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MenuServiceImpl implements CustomService<Menu> {

    @Autowired
    private MenuRepository repository;
    @Autowired
    private UserServiceImpl userService;

    private static Logger Log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Transactional(readOnly = true)
    public Menu findOne(Long id) {
        Log.debug("Find one by Id  {}", id );

        Optional<Menu> menuOptional = repository.findById(id);
        return menuOptional.orElseGet(Menu::new);
    }

    @Transactional(readOnly = true)
    public Page<Menu> findAllByPage(Integer page, Integer total) {
        Log.debug("Find Pageable page {} , total {}", page, total);
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);
        return repository.findAll(pageRequest) ;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll(){
        return repository.findAll();
    }

    @Override
    public Menu addnew(Menu menu) {
        return repository.save(menu);
    }

    @Override
    public Menu update(Menu menu, Long idOld) {
        Optional<Menu> optionalMenu = repository.findById(idOld);
        if (optionalMenu.isPresent()){

            User user = userService.getCurUserAsObj();
            Menu oldMenu = optionalMenu.get();
            oldMenu.setName(menu.getName());
            oldMenu.setDescription(menu.getDescription());
            oldMenu.setUpdatedBy(user);
            return repository.save(oldMenu);
        }else {
            return null;
        }
    }

    @Override
    public Menu update(Menu menu) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<RoleMenuViewDto> findRoleId(Long roleid) {

        final List<RoleMenuViewDto> results = new ArrayList<>();
        List<RoleMenu> roleMenus = repository.findByIdRole(roleid) ;
        roleMenus.forEach( roleMenu -> {
           Optional<Menu> optionalMenu = repository.findById(roleMenu.getRoleMenuId().getMenuId()) ;
           RoleMenuViewDto dto = new RoleMenuViewDto();
           if ( optionalMenu.isPresent() ){
               Menu menu = optionalMenu.get();
               dto.setMenuDescription(menu.getDescription());
               dto.setMenuId(menu.getId());
               dto.setStatus(roleMenu.getStatus());
           }
            results.add(dto)  ;
        });
        return results;
    }

    public List<Menu> findRoleIdExc(Long roleid) {
        List<RoleMenu> exsistingRoleMenu = repository.findByIdRole(roleid) ;
        List<Long> exsistingList = new ArrayList<>();
        exsistingRoleMenu.forEach(roleMenu -> {
            exsistingList.add(roleMenu.getRoleMenuId().getMenuId());
            System.out.println("id yg terdaftar " + roleMenu);
        });

        final List<Menu> results = new ArrayList<>();
        repository.findAll().forEach(menu -> {
            System.out.println("Iterate menu " + menu);
            if  (!exsistingList.contains(menu.getId())) {
                results.add(menu);
                System.out.println("menu added " + menu);
            }
        });

        return  results;
    }

    @Transactional(readOnly = true)
    public List<UserMenuDto> getUserMenuByUsername(String username, String all){
        List<Object> objList =  repository.findUserMenuByUsername(username, all);
        List<UserMenuDto> userMenuDtoList = new ArrayList<>();
        for (Object obj : objList){
            Object[] objval = (Object[]) obj;
            UserMenuDto userMenuDto = new UserMenuDto();
            userMenuDto.setId((objval[0] != null)? Long.valueOf(objval[0].toString()):null);
            userMenuDto.setName((objval[1] != null)? objval[1].toString():"");
            userMenuDto.setDescription((objval[2] != null)?objval[2].toString():"");
            userMenuDto.setLink((objval[3] != null)? objval[3].toString():"");
            userMenuDto.setIcon((objval[4] != null)? objval[4].toString():"");
            userMenuDto.setParentId((objval[5] != null)? Long.valueOf(objval[5].toString()):null);
            userMenuDtoList.add(userMenuDto);
        }
        return userMenuDtoList;
    }

    public List<UserMenuDto> getUserMenuCurUsername() { return this.getUserMenuByUsername(this.userService.getCurrentUser(),null); }

    public List<Menu> getMenuByStatusIsActive(){
        return repository.findByStatus(1);
    }

}
