package com.ddabadi.rest.trans.master;

import com.ddabadi.model.Classes;
import com.ddabadi.model.dto.ClassesDto;
import com.ddabadi.service.impl.trans.master.ClassesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/classes")
public class ClassesRest {

    @Autowired private ClassesService classesService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public Page<Classes> getAll(@PathVariable Integer page,
                                @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        return classesService.findAll(page, count);
    }

    @PostMapping
    public ClassesDto save(@RequestBody ClassesDto classesDto){

        logger.info("save " + classesDto.toString());
        return classesService.addnew(classesDto);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<Classes>> filterEntity(@RequestBody ClassesDto filterDto,
                                                      @PathVariable Integer page,
                                                      @PathVariable Integer total){
        logger.info("filter object " + filterDto);
        Page<Classes> result =  classesService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ClassesDto> ubah(@RequestBody ClassesDto classesDto){

        ClassesDto updateRec = classesService.update(classesDto );

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
