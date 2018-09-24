package com.ddabadi.service.impl.trans.master;


import com.ddabadi.model.GroupOutlet;
import com.ddabadi.model.Outlet;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.OutletDto;
import com.ddabadi.repository.OutletRepository;
import com.ddabadi.service.CustomService;

import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.service.util.ErrCodeUtil;
import com.ddabadi.util.SmStockUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.*;

@Service
public class OutletService implements CustomService<OutletDto> {

    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private OutletGroupService outletGroupService;
    @Autowired private OutletRepository repository;
    @Autowired private UserServiceImpl userService;

    @Override
    public OutletDto addnew(OutletDto outletDto) {

        Optional<GroupOutlet> groupOutlet = outletGroupService.findById(outletDto.getGroupOutletId());
        if (!groupOutlet.isPresent()){
            outletDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            outletDto.setErrDesc((errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription()));
            return outletDto;
        }

        User user = userService.getCurUserAsObj();
        Date lastUpdate = new Date();

        outletDto.setId(null);
        Outlet newRec = new Outlet();
        newRec.setName(outletDto.getName());
        newRec.setGroupOutlet(groupOutlet.get());
        newRec.setRegistrationFee(outletDto.getRegistrationFee());
        newRec.setAddress1(outletDto.getAddress1());
        newRec.setAddress2(outletDto.getAddress2());
        newRec.setCreatedAt(lastUpdate);
        newRec.setUpdatedAt(lastUpdate);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        outletDto.setId(newRec.getId());
        outletDto.setErrCode(ERR_CODE_SUCCESS);
        outletDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return outletDto;
    }

    @Override
    public OutletDto update(OutletDto outlet, Long idOld) { return null; }

    @Override
    public OutletDto update(OutletDto outletDto) {
        Optional<Outlet> updateDataOpt = repository.findById(outletDto.getId());
        Optional<GroupOutlet> groupOutlet = outletGroupService.findById(outletDto.getGroupOutletId());
        if (!groupOutlet.isPresent()){
            outletDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            outletDto.setErrDesc((errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription()));
            return outletDto;
        }

        if (updateDataOpt.isPresent()){
            Outlet updateOutlet = updateDataOpt.get();
            User user = userService.getCurUserAsObj();
            Date lastUpdate = new Date();

            updateOutlet.setName(outletDto.getName());
            updateOutlet.setGroupOutlet(groupOutlet.get());
            updateOutlet.setRegistrationFee(outletDto.getRegistrationFee());
            updateOutlet.setAddress1(outletDto.getAddress1());
            updateOutlet.setAddress2(outletDto.getAddress2());
            updateOutlet.setUpdatedAt(lastUpdate);
            updateOutlet.setUpdatedBy(user);


            repository.save(updateOutlet);
            outletDto.setErrCode(ERR_CODE_SUCCESS);
            outletDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            outletDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            outletDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return outletDto;
    }

    public Page<Outlet> findAll(Integer page, Integer count) {

        Sort sort = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page -1 , count, sort);

        return repository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Outlet> searchByFilter(OutletDto filterDto, int page, int total) {

        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        System.out.println("filter " + filterDto);
        return repository.findByFilter(filterDto, pageRequest);
    }


}
