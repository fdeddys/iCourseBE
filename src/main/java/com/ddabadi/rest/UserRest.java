package com.ddabadi.rest;


import com.ddabadi.AppStartup;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.FilterDto;
import com.ddabadi.model.dto.UserDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user")
@Api(value = "usr", tags = "CRUD for User")
public class UserRest {

    private Logger logger= Logger.getLogger(AppStartup.class);
    @Autowired private UserServiceImpl userService;

    @RequestMapping(value = "user/{id}")
    public String getUser(@PathVariable Long id){
        return userService.findOne(id).toString();
    }

    @GetMapping(value = "/page/{page}/count/{total}")
    public ResponseEntity<Page<User>> getByAll(@PathVariable Integer page,
                                               @PathVariable Integer total){

        Page<User> userPage = userService.findAllByPage(page, total);
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    @GetMapping(value = "current")
    public ResponseEntity<UserDto> getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        logger.info ("Auth  "+ userDetails);
        logger.info("Auth  " + auth.getName());
        logger.info("Auth  "+ auth.getAuthorities());

        User user = userService.findOneByName(auth.getName());
        UserDto userDto = new UserDto();
        if (user.getId() == null ){
            userDto.setErrMsg("user not found");
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } else {
            System.out.println("isi cur "+ user.toString());
            userDto.setName(user.getName());
            userDto.setId(user.getId());
            userDto.setStatus(user.getStatus());
            userDto.setEmail(user.getEmail());
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<UserDto> simpan(@RequestBody UserDto userDto){

        UserDto newRec = userService.addnew(userDto);
        return new ResponseEntity<>(newRec, HttpStatus.OK);
    }

    @PostMapping(value = "{userId}/role/{roleId}")
    public ResponseEntity<User> addRole(@PathVariable Long userId,
                                        @PathVariable Long roleId){

        User newRec = userService.addRole(userId, roleId);
        return new ResponseEntity<>(newRec, HttpStatus.OK);
    }

    @PutMapping(value = "{userId}/role/{roleId}/changeStatus")
    public ResponseEntity<User> changeStatusRole(@RequestBody UserDto userDto){

        User newRec = userService.changeStatusRole(userDto);
        return new ResponseEntity<>(newRec, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> ubahao(@PathVariable Long id,
                                          @RequestBody UserDto userDto){

        UserDto updateRec = userService.update(userDto, id);

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }

    @PutMapping(value = "changeP")
    public ResponseEntity<UserDto> changePassword(@RequestBody UserDto userDto){

        UserDto updateRec = userService.changePassword(userDto);
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }

    @PutMapping(value = "{id}/resetpassword")
    public ResponseEntity<UserDto> resetPassword(@PathVariable Long id){
        UserDto result = userService.resetPassword(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<User>> filterEntity(@RequestBody FilterDto filterDto,
                                                   @PathVariable Integer page,
                                                   @PathVariable Integer total){

        Page<User> newRec = userService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(newRec, HttpStatus.OK);
    }

    @PutMapping(value = "{userId}/role/{roleId}/removeRole")
    public ResponseEntity<User> removeRole(@PathVariable("userId")Long userId,
                                           @PathVariable("roleId")Long roleId){

        User newRec = userService.removeRole(userId, roleId);
        return new ResponseEntity<>(newRec, HttpStatus.OK);
    }

}
