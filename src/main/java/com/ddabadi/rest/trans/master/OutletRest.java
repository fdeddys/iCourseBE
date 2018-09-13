package com.ddabadi.rest.trans.master;

import com.ddabadi.model.dto.OutletDto;
import com.ddabadi.service.impl.trans.master.OutletService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api")
public class OutletRest {

    @Autowired private OutletService outletService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public OutletDto getAll(@PathVariable Integer page,
                            @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        return outletService.findAll(page, count);
    }

    @PostMapping
    public OutletDto getAll(@RequestBody OutletDto outletDto){

        logger.info("save " + outletDto.toString());
        return outletService.addnew(outletDto);
    }

}
