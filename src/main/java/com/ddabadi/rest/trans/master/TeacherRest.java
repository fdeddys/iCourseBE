package com.ddabadi.rest.trans.master;

import com.ddabadi.model.Teacher;
import com.ddabadi.model.dto.TeacherDto;
import com.ddabadi.service.impl.master.TeacherService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/teacher")
public class TeacherRest {

    @Autowired private TeacherService teacherService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public Page<Teacher> getAll(@PathVariable Integer page,
                                @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        return teacherService.searchByFilter(new TeacherDto(), page, count);
    }

    @PostMapping
    public TeacherDto save(@RequestBody TeacherDto teacherDto){

        logger.info("save " + teacherDto.toString());
        return teacherService.addnew(teacherDto);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<Teacher>> filterEntity(@RequestBody TeacherDto filterDto,
                                                      @PathVariable Integer page,
                                                      @PathVariable Integer total){
        logger.info("filter object " + filterDto);
        Page<Teacher> result =  teacherService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TeacherDto> ubah(@RequestBody TeacherDto teacherDto){

        TeacherDto updateRec = teacherService.update(teacherDto );
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
