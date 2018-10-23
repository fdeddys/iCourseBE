package com.ddabadi.rest.trans;

import com.ddabadi.service.impl.util.UtilServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/util")
public class utilRest {

    @Autowired private UtilServiceImpl utilService;

    @RequestMapping(value = "coursetype")
    List<String> getAllCourseType(){
        return utilService.getAllCourseType() ;
    }

}
