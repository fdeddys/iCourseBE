package com.ddabadi.rest.trans.master;


import com.ddabadi.model.Room;
import com.ddabadi.model.dto.RoomDto;
import com.ddabadi.service.impl.trans.master.RoomService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/room")
public class RoomRest {

    @Autowired
    private RoomService roomService;

    private static final Logger logger = Logger.getLogger(OutletRest.class);

    @GetMapping
    @RequestMapping(value = "/{page}/{count}")
    public ResponseEntity<Page<Room>> getAll(@PathVariable Integer page,
                                             @PathVariable Integer count){

        logger.info("Find all page " + page.toString() + " count " +  count.toString());
        Page<Room> memberPage =  roomService.findAll(page, count);
        return new ResponseEntity<>(memberPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDto> save(@RequestBody RoomDto roomDto){

        logger.info("save " + roomDto.toString());
        RoomDto result = roomService.addnew(roomDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value= "filter/page/{page}/count/{total}")
    public ResponseEntity<Page<Room>> filterEntity(@RequestBody RoomDto filterDto,
                                                   @PathVariable Integer page,
                                                   @PathVariable Integer total){

        logger.info("filter object " + filterDto);
        Page<Room> result =  roomService.searchByFilter(filterDto, page, total);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Room> ubah(@PathVariable String id,
                                     @RequestBody RoomDto outletGroupDto){

        RoomDto updateRec = roomService.update(outletGroupDto );
        return new ResponseEntity<>(updateRec, HttpStatus.OK);
    }
}
