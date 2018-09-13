package com.ddabadi.rest;


import com.ddabadi.model.User;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
@Api(value = "usr", tags = "CRUD for User")
public class UserRest {

    @Autowired private UserServiceImpl userService;

    @RequestMapping(value = "user/{id}")
    public String getUser(@PathVariable Long id){
        return userService.findOne(id).toString();
    }

//    @RequestMapping(value = "user/add")
    private User addUser(){

        User user = new User();
        user.setFirstName("deddy");
        user.setLastName("syuhendra");
        user.setRememberToken("");
        user.setEmail("ddd.dd@co");
        user.setPassword("$2a$10$JO27HeXnPJrkPGBMVU40a.2x1ShX1YLKzSvKYxlRtvhHahtxfJIiu");
        user.setStatus(EntityStatus.ACTIVE);
        user.setName("deddy");
        return userService.save(user);
    }
}
