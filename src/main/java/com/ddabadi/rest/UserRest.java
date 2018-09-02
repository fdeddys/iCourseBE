package com.ddabadi.rest;


import com.ddabadi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class UserRest {

    @Autowired private UserServiceImpl userService;
    @RequestMapping(value = "user")
    public String getUser(){
        return userService.findOne(1L).toString();
    }

}
