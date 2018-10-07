package com.ddabadi.service.impl.trans.master;

import com.ddabadi.model.Classes;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.ClassesDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.ClassesRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class ClassesService implements CustomService<ClassesDto> {

    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private ClassesRepository repository;
    @Autowired private UserServiceImpl userService;

    @Override
    public ClassesDto addnew(ClassesDto classesDto) {
        User user = userService.getCurUserAsObj();

        classesDto.setId(null);
        Classes newRec = new Classes();
        newRec.setName(classesDto.getName());
        newRec.setMonthlyFee(classesDto.getMonthlyFee());
        newRec.setClassCode(classesDto.getClassCode());
        newRec.setOutlet(user.getOutlet());
        newRec.setStatus(EntityStatus.ACTIVE);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        classesDto.setId(newRec.getId());
        classesDto.setErrCode(ERR_CODE_SUCCESS);
        classesDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return classesDto;
    }

    @Override
    public ClassesDto update(ClassesDto classesDto, Long idOld) {
        return null;
    }

    @Override
    public ClassesDto update(ClassesDto classesDto) {
        User user = userService.getCurUserAsObj();

        Optional<Classes> updateDataOpt = repository.findById(classesDto.getId());
        if (updateDataOpt.isPresent()){
            Classes updateRec = updateDataOpt.get();
            updateRec.setMonthlyFee(classesDto.getMonthlyFee());
            updateRec.setName(classesDto.getName());
            updateRec.setClassCode(classesDto.getClassCode());
            updateRec.setStatus(classesDto.getStatus());
            updateRec.setUpdatedBy(user);
            repository.save(updateRec);
            classesDto.setErrCode(ERR_CODE_SUCCESS);
            classesDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            classesDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            classesDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return classesDto;
    }

    public Page<Classes> findAll(Integer page, Integer count) {
        User user = userService.getCurUserAsObj();
        Sort sort = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page -1 , count, sort);
        return repository.findByOutlet(user.getOutlet(), pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Classes> searchByFilter(ClassesDto filterDto, int page, int total) {
        User user = userService.getCurUserAsObj();
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        System.out.println("filter " + filterDto);
        Page<Classes> res = repository.findByFilter(filterDto,
                user.getOutlet() == null? null : user.getOutlet().getId(),
                pageRequest);

        return  res;
    }

    public Optional<Classes> findById(String id) {
        return repository.findById(id);
    }

}
