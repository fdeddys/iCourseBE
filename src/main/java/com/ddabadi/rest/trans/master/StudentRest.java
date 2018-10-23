package com.ddabadi.rest.trans.master;

import com.ddabadi.model.Student;
import com.ddabadi.model.dto.StudentDto;
import com.ddabadi.service.impl.master.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/student")
public class StudentRest {

    @Autowired private StudentService studentService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public Page<Student> getAll(@PathVariable Integer page,
                                @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        return studentService.searchByFilter(new StudentDto(), page, count);
    }

    @PostMapping
    public StudentDto save(@RequestBody StudentDto studentDto){

        logger.info("save " + studentDto.toString());
        return studentService.addnew(studentDto);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<Student>> filterEntity(@RequestBody StudentDto filterDto,
                                                      @PathVariable Integer page,
                                                      @PathVariable Integer total){
        logger.info("filter object " + filterDto);
        Page<Student> result =  studentService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StudentDto> ubah(@RequestBody StudentDto studentDto){

        StudentDto updateRec = studentService.update(studentDto );
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
