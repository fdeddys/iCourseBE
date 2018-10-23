package com.ddabadi.service.impl.util;

import com.ddabadi.model.enu.CourseType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UtilServiceImpl {

    public List<String> getAllCourseType(){
        return Stream.of(CourseType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
