package com.ddabadi.rest;


import com.ddabadi.model.Role;
import com.ddabadi.model.dto.RoleDto;
import com.ddabadi.service.impl.RoleServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping(value = "api/role")
@Api(value = "role", description = "CRUD for entity Role")
public class RoleRest {

    private static Logger Log = LoggerFactory.getLogger(RoleRest.class);

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping(value = "{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails roleDetails = (UserDetails) auth.getPrincipal();
        System.out.println("Auth " + roleDetails);
        System.out.println("Auth " + auth.getName());
        System.out.println("Auth " + auth.getAuthorities());

        Role role = roleService.findOne(id);
        return new ResponseEntity<>(role,HttpStatus.OK);
    }


    @GetMapping(value = "/page/{page}/count/{total}")
    public ResponseEntity<Page<Role>> getByAll(@PathVariable Integer page,
                                               @PathVariable Integer total){

        Page<Role> rolePage = roleService.findAllByPage(page, total);
        return new ResponseEntity<>(rolePage,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RoleDto> simpan(@RequestBody RoleDto roleDto ){

        RoleDto newRec = roleService.addnew(roleDto);
        return new ResponseEntity<>(newRec, HttpStatus.OK);
    }

    @PutMapping(value = "{roleid}/menu/{menuid}")
    public ResponseEntity<Role> updateActivate(@PathVariable Long roleid,
                                               @PathVariable Long menuid){

        roleService.updateRoleActivate(roleid, menuid);
        return new ResponseEntity<>(new Role(), HttpStatus.OK);

    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Role> ubah(@PathVariable Long id,
                                       @RequestBody Role role){

        Role updateRec = roleService.update(role, id);
        if (updateRec == null ){
            updateRec = new Role();
            updateRec.setName("ID NOT FOUND, ERROR UPDATE");
        };

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }

    @PostMapping(value = "{id}")
    public ResponseEntity<Role> ubahRole(@PathVariable Long id,
                                         @RequestBody List<Long> menuIds){

        Role updateRec = roleService.updateMenu(id, menuIds);

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }

}
