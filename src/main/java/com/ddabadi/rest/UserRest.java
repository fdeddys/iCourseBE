package com.ddabadi.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class UserRest {

    @RequestMapping(value = "user")
    public String getUser(){
        return "userrr";
    }

}
