package com.ddabadi.service.impl.master;

import com.ddabadi.model.GroupOutlet;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.OutletGroupDto;
import com.ddabadi.repository.GroupOutletRepository;
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

import static com.ddabadi.config.BaseErrorCode.*;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class OutletGroupService implements CustomService<OutletGroupDto> {

    @Autowired private GroupOutletRepository repository;
    @Autowired private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;

    @Override
    public OutletGroupDto addnew(OutletGroupDto outletGroupDto) {

        User user = userService.getCurUserAsObj();
        Date lastUpdate = new Date();

        outletGroupDto.setId(null);
        GroupOutlet newRec = new GroupOutlet();
        newRec.setName(outletGroupDto.getName());
        newRec.setCreatedAt(lastUpdate);
        newRec.setUpdatedAt(lastUpdate);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        outletGroupDto.setId(newRec.getId());
        outletGroupDto.setErrCode(ERR_CODE_SUCCESS);
        outletGroupDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return outletGroupDto;
    }

    @Override
    public OutletGroupDto update(OutletGroupDto outlet, Long idOld) { return null; }

    @Override
    public OutletGroupDto update(OutletGroupDto outletGroupDto) {

        User user = userService.getCurUserAsObj();
        Date lastUpdate = new Date();

        Optional<GroupOutlet> updateDataOpt = repository.findById(outletGroupDto.getId());
        if (updateDataOpt.isPresent()){
            GroupOutlet updateOutlet = updateDataOpt.get();
            updateOutlet.setName(outletGroupDto.getName());
            updateOutlet.setUpdatedAt(lastUpdate);
            updateOutlet.setUpdatedBy(user);
            repository.save(updateOutlet);
            outletGroupDto.setErrCode(ERR_CODE_SUCCESS);
            outletGroupDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            outletGroupDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            outletGroupDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return outletGroupDto;
    }

    public Page<GroupOutlet> findAll(Integer page, Integer count) {

        Sort sort = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page -1 , count, sort);

        return repository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<GroupOutlet> searchByFilter(OutletGroupDto filterDto, int page, int total) {

        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        System.out.println("filter " + filterDto);
        return repository.findByFilter(filterDto, pageRequest);
    }

    public Optional<GroupOutlet> findById(String id) {

        return repository.findById(id);

    }
}
