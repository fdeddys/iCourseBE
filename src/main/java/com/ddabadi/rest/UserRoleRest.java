package com.ddabadi.rest;

import com.ddabadi.model.UserRole;
import com.ddabadi.model.dto.UserRoleDto;
import com.ddabadi.model.dto.UserRoleViewDto;
import com.ddabadi.service.impl.UserRoleServiceImpl;
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


@RestController
@RequestMapping(value = "api/roleuser")
@Api(value = "roleuser", tags = "CRUD for entity Role - User")
public class UserRoleRest {

    private static Logger Log = LoggerFactory.getLogger(UserRoleRest.class);

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @GetMapping(value = "roleId/{roleId}/userId/{userId}")
    public ResponseEntity<UserRole> getById(@PathVariable Long roleId,
                                            @PathVariable Long userId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails roleUserDetails = (UserDetails) auth.getPrincipal();
        System.out.println("Auth " + roleUserDetails);
        System.out.println("Auth " + auth.getName());
        System.out.println("Auth " + auth.getAuthorities());

        UserRole roleUser = userRoleService.findByRoleIdAndUserId(roleId, userId);
        return new ResponseEntity<>(roleUser,HttpStatus.OK);
    }

    @GetMapping(value = "userid/{userid}")
    public ResponseEntity<List<Long>> getByUserId(@PathVariable Long userid){

        List<Long> roles = userRoleService.findByUserId(userid);
        return new ResponseEntity<>(roles, HttpStatus.OK) ;
    }


    @GetMapping(value = "user/{userid}/listrole")
    public ResponseEntity<List<UserRoleViewDto>> getViewByUserId(@PathVariable Long userid){

        List<UserRoleViewDto> roles = userRoleService.findRoleViewByUserId(userid);
        return new ResponseEntity<>(roles, HttpStatus.OK) ;
    }

    @GetMapping(value = "/page/{page}/count/{total}")
    public ResponseEntity<Page<UserRole>> getByAll(@PathVariable Integer page,
                                                   @PathVariable Integer total){

        Page<UserRole> roleUserPage = userRoleService.findAllByPage(page, total);
        return new ResponseEntity<>(roleUserPage,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserRole> simpan(@RequestBody UserRoleDto userRoleDto ){

        userRoleService.addnew(userRoleDto);
        return new ResponseEntity<>(new UserRole(), HttpStatus.OK);

    }

    @PutMapping(value = "{id}")
    public ResponseEntity<UserRole> ubah(@PathVariable Long id,
                                         @RequestBody UserRole roleUser){

        UserRole updateRec = userRoleService.update(roleUser, id);
        if (updateRec == null ){
            updateRec = new UserRole();
        };

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
