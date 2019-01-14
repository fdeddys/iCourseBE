package com.ddabadi.rest.trans.trans;

import com.ddabadi.model.AttendanceHd;
import com.ddabadi.model.dto.AttendanceDtDto;
import com.ddabadi.model.dto.AttendanceHdDto;
import com.ddabadi.rest.trans.master.OutletRest;
import com.ddabadi.service.impl.trans.AttendanceDtService;
import com.ddabadi.service.impl.trans.AttendanceHdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/attendance")
public class AttendanceRest {

    @Autowired
    private AttendanceHdService attendanceHdService;
    @Autowired private AttendanceDtService attendanceDtService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public ResponseEntity<Page<AttendanceHd>> getAll(@PathVariable Integer page,
                                                     @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        Page<AttendanceHd> memberPage =  attendanceHdService.searchByFilter(new AttendanceHdDto(), page, count);
        return new ResponseEntity<>(memberPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AttendanceHdDto> save(@RequestBody AttendanceHdDto registrationDto){

        logger.info("save " + registrationDto.toString());
        AttendanceHdDto result = attendanceHdService.addnew(registrationDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "detil")
    public ResponseEntity<AttendanceDtDto> saveDetil(@RequestBody AttendanceDtDto attendanceDtDto){

        logger.info("save " + attendanceDtDto.toString());
        AttendanceDtDto result = attendanceDtService.addnew(attendanceDtDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<AttendanceHd>> filterEntity(@RequestBody AttendanceHdDto filterDto,
                                                           @PathVariable Integer page,
                                                           @PathVariable Integer total){

        logger.info("filter object " + filterDto);
        Page<AttendanceHd> result =  attendanceHdService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<AttendanceHd> ubah(@PathVariable String id,
                                             @RequestBody AttendanceHdDto attendanceHdDto){

        AttendanceHdDto updateRec = attendanceHdService.update(attendanceHdDto );
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
