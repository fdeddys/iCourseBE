package com.ddabadi.rest.trans.trans;

import com.ddabadi.model.Registration;

import com.ddabadi.model.dto.RegistrationDto;
import com.ddabadi.rest.trans.master.OutletRest;
import com.ddabadi.service.impl.trans.RegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/registration")
public class RegistrationRest {

    @Autowired
    private RegistrationService registrationService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public ResponseEntity<Page<Registration>> getAll(@PathVariable Integer page,
                                                     @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        Page<Registration> memberPage =  registrationService.searchByFilter(new RegistrationDto(), page, count);
        return new ResponseEntity<>(memberPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegistrationDto> save(@RequestBody RegistrationDto registrationDto){

        logger.info("save " + registrationDto.toString());
        RegistrationDto result = registrationService.addnew(registrationDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<Registration>> filterEntity(@RequestBody RegistrationDto filterDto,
                                                   @PathVariable Integer page,
                                                   @PathVariable Integer total){

        logger.info("filter object " + filterDto);
        Page<Registration> result =  registrationService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Registration> ubah(@PathVariable String id,
                                     @RequestBody RegistrationDto outletGroupDto){

        RegistrationDto updateRec = registrationService.update(outletGroupDto );
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
