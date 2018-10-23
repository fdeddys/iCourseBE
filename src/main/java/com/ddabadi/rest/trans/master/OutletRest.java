package com.ddabadi.rest.trans.master;


import com.ddabadi.model.Outlet;
import com.ddabadi.model.dto.OutletDto;

import com.ddabadi.service.impl.master.OutletService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/outlet")
public class OutletRest {

    @Autowired private OutletService outletService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public Page<Outlet> getAll(@PathVariable Integer page,
                               @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        return outletService.findAll(page, count);
    }

    @PostMapping
    public OutletDto save(@RequestBody OutletDto outletDto){

        logger.info("save " + outletDto.toString());
        return outletService.addnew(outletDto);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<Outlet>> filterEntity(@RequestBody OutletDto filterDto,
                                                     @PathVariable Integer page,
                                                     @PathVariable Integer total){
        logger.info("filter object " + filterDto);
        Page<Outlet> result =  outletService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<OutletDto> ubah(@PathVariable String id,
                                            @RequestBody OutletDto outletDto){

        OutletDto updateRec = outletService.update(outletDto );

        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
