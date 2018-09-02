package com.ddabadi.rest;

import com.ddabadi.model.Menu;
import com.ddabadi.model.dto.RoleMenuViewDto;
import com.ddabadi.model.dto.UserMenuDto;
import com.ddabadi.service.impl.MenuServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api/menu")
@Api(value = "menu", tags = {"Menu [needed by system] "} )
public class MenuRestController {

    private static Logger log = LoggerFactory.getLogger(MenuRestController.class);

    @Autowired
    private MenuServiceImpl menuService;

    @GetMapping(value = "{id}")
    public ResponseEntity<Menu> getById(@PathVariable Long id){

        Menu menu = menuService.findOne(id);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }


    @GetMapping(value = "/page/{page}/count/{total}")
    public ResponseEntity<Page<Menu>> getByAll(@PathVariable Integer page,
                                               @PathVariable Integer total){
        Page<Menu> menuPage = menuService.findAllByPage(page, total);
        return new ResponseEntity<>(menuPage, HttpStatus.OK);
    }

    @GetMapping(value = "/usermenu/{username}")
    public ResponseEntity<List<UserMenuDto>> getUserMenu(@PathVariable String username){
        log.debug("get user menu");
        List<UserMenuDto> userMenuList = menuService.getUserMenuByUsername(username,null);
        return new ResponseEntity<>(userMenuList, HttpStatus.OK);
    }

    @GetMapping(value = "/listusermenu")
    public ResponseEntity<List<UserMenuDto>> getUserMenu(){

        log.debug("get user menu");
        List<UserMenuDto> userMenuList = menuService.getUserMenuCurUsername();
        return new ResponseEntity<>(userMenuList, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Menu> ubah(@PathVariable Long id,
                                     @RequestBody Menu menu){

        Menu updateRec = menuService.update(menu, id);
        if (updateRec == null ){
            updateRec = new Menu();
            updateRec.setName("ID NOT FOUND, ERROR UPDATE");
        }
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }

    @GetMapping(value = "role/{roleid}")
    public ResponseEntity<List<RoleMenuViewDto>> getByRoleId(@PathVariable Long roleid){
        return new ResponseEntity<>(menuService.findRoleId(roleid), HttpStatus.OK);
    }

    @GetMapping(value = "list-all-active-menu")
    public ResponseEntity<List<Menu>> getAllActiveMenu(){
        List<Menu> menuList = menuService.getMenuByStatusIsActive();
        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }

}
