package com.ddabadi.service.impl.master;


import com.ddabadi.model.Room;
import com.ddabadi.model.User;

import com.ddabadi.model.dto.RoomDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.RoomRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class RoomService implements CustomService<RoomDto> {

    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private RoomRepository repository;
    @Autowired private UserServiceImpl userService;

    @Override
    public RoomDto addnew(RoomDto roomDto) {
        User user = userService.getCurUserAsObj();

        roomDto.setId(null);
        Room newRec = new Room();
        newRec.setName(roomDto.getName());
        newRec.setOutlet(userService.getCurOutlet());
        newRec.setStatus(EntityStatus.ACTIVE);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        roomDto.setId(newRec.getId());
        roomDto.setErrCode(ERR_CODE_SUCCESS);
        roomDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return roomDto;
    }

    @Override
    public RoomDto update(RoomDto roomDto, Long idOld) {
        return null;
    }

    @Override
    public RoomDto update(RoomDto roomDto) {
        User user = userService.getCurUserAsObj();

        Optional<Room> updateDataOpt = repository.findById(roomDto.getId());
        if (updateDataOpt.isPresent()){
            Room updateOutlet = updateDataOpt.get();
            updateOutlet.setName(roomDto.getName());
            updateOutlet.setStatus(roomDto.getStatus());
            updateOutlet.setUpdatedBy(user);
            repository.save(updateOutlet);
            roomDto.setErrCode(ERR_CODE_SUCCESS);
            roomDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            roomDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            roomDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return roomDto;
    }

    public Page<Room> findAll(Integer page, Integer count) {
        User user = userService.getCurUserAsObj();
        Sort sort = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page -1 , count, sort);

        return repository.findByOutlet(userService.getCurOutlet(), pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Room> searchByFilter(RoomDto filterDto, int page, int total) {
        User user = userService.getCurUserAsObj();
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        System.out.println("filter " + filterDto);
        Page<Room> res = repository.findByFilter(filterDto,
                                                 userService.getCurOutlet() == null? null : userService.getCurOutlet().getId(),
                                                 pageRequest);

        return  res;

    }

    public Optional<Room> findById(String id) {

        return repository.findById(id);
    }

}
