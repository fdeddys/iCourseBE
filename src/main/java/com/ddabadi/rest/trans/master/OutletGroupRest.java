package com.ddabadi.rest.trans.master;

import com.ddabadi.model.GroupOutlet;
import com.ddabadi.model.dto.OutletGroupDto;
import com.ddabadi.service.impl.master.OutletGroupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/outletgroup")
public class OutletGroupRest {

    @Autowired
    private OutletGroupService groupOutletService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public ResponseEntity<Page<GroupOutlet>> getAll(@PathVariable Integer page,
                                                    @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        Page<GroupOutlet> memberPage =  groupOutletService.findAll(page, count);
        return new ResponseEntity<>(memberPage,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OutletGroupDto> save(@RequestBody OutletGroupDto outletDto){

        logger.info("save " + outletDto.toString());
        OutletGroupDto result = groupOutletService.addnew(outletDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<GroupOutlet>> filterEntity(@RequestBody OutletGroupDto filterDto,
                                                       @PathVariable Integer page,
                                                       @PathVariable Integer total
                                                      ){

        logger.info("filter object " + filterDto);
        Page<GroupOutlet> result =  groupOutletService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<GroupOutlet> ubah(@PathVariable String id,
                                          @RequestBody OutletGroupDto outletGroupDto){

        OutletGroupDto updateRec = groupOutletService.update(outletGroupDto );

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }

}
