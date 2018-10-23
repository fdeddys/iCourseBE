package com.ddabadi.service.impl.master;

import com.ddabadi.model.Teacher;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.TeacherDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.TeacherRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.util.GenerateNumber;
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
public class TeacherService implements CustomService<TeacherDto> {

    @Autowired
    private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private TeacherRepository repository;
    @Autowired private GenerateNumber generator;

    @Override
    public TeacherDto addnew(TeacherDto teacherDto) {
        User user = userService.getCurUserAsObj();

        teacherDto.setId(null);
        Teacher newRec = new Teacher();
        newRec.setName(teacherDto.getName());
        newRec.setAllowance(teacherDto.getAllowance());
        newRec.setBaseSalary(teacherDto.getBaseSalary());
        newRec.setTeacherCode(generator.generateTeacherCode(teacherDto.getName()));
        newRec.setAddress1(teacherDto.getAddress1());
        newRec.setAddress2(teacherDto.getAddress2());
        newRec.setPhone(teacherDto.getPhone());
        newRec.setOutlet(userService.getCurOutlet());
        newRec.setStatus(EntityStatus.ACTIVE);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        teacherDto.setId(newRec.getId());
        teacherDto.setTeacherCode(newRec.getTeacherCode());
        teacherDto.setErrCode(ERR_CODE_SUCCESS);
        teacherDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return teacherDto;
    }

    @Override
    public TeacherDto update(TeacherDto teacherDto, Long idOld) {
        return null;
    }

    @Override
    public TeacherDto update(TeacherDto teacherDto) {
        User user = userService.getCurUserAsObj();

        Optional<Teacher> updateDataOpt = repository.findById(teacherDto.getId());
        if (updateDataOpt.isPresent()){
            Teacher updateRec = updateDataOpt.get();

            updateRec.setName(teacherDto.getName());
            updateRec.setAllowance(teacherDto.getAllowance());
            updateRec.setBaseSalary(teacherDto.getBaseSalary());
            updateRec.setAddress1(teacherDto.getAddress1());
            updateRec.setAddress2(teacherDto.getAddress2());
            updateRec.setPhone(teacherDto.getPhone());
            updateRec.setStatus(teacherDto.getStatus());
            updateRec.setUpdatedBy(user);

            repository.save(updateRec);
            teacherDto.setErrCode(ERR_CODE_SUCCESS);
            teacherDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            teacherDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            teacherDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return teacherDto;
    }

    @Transactional(readOnly = true)
    public Page<Teacher> searchByFilter(TeacherDto filterDto, int page, int total) {
        User user = userService.getCurUserAsObj();
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        System.out.println("filter " + filterDto);
        Page<Teacher> res = repository.findByFilter(filterDto,
                userService.getCurOutlet() == null? null : userService.getCurOutlet().getId(),
                pageRequest);

        return  res;
    }

    public Optional<Teacher> findById(String teacherId) {
            return repository.findById(teacherId);
    }
}
