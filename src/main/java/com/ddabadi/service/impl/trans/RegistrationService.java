package com.ddabadi.service.impl.trans;

import com.ddabadi.model.AttendanceHd;
import com.ddabadi.model.Registration;
import com.ddabadi.model.Student;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.RegistrationDto;
import com.ddabadi.repository.RegistrationRepository;
import com.ddabadi.service.CustomService;

import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.service.impl.master.StudentService;
import com.ddabadi.util.GenerateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ddabadi.config.BaseErrorCode.*;

@Service
public class RegistrationService implements CustomService<RegistrationDto> {

    @Autowired private RegistrationRepository repository;
    @Autowired private UserServiceImpl userService;
    @Autowired private StudentService studentService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private GenerateNumber generator;

    @Override
    @Transactional
    public RegistrationDto addnew(RegistrationDto registrationDto) {

        Student student = studentService.addnew(registrationDto.getStudentDto());
        if (student == null){
            registrationDto.setErrCode(ERR_CODE_REGISTRATION_NOT_CREATED);
            registrationDto.setErrDesc("Cannot Create Student ! "+ errorCodeService.findByCode(ERR_CODE_REGISTRATION_NOT_CREATED).getDescription());
            return registrationDto;
        }

        User user = userService.getCurUserAsObj();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateReg = null;
        try {
            dateReg = sdf.parse(registrationDto.getStrRegDate());
        } catch (ParseException e) {

            registrationDto.setErrCode(ERR_CODE_REGISTRATION_NOT_CREATED);
            registrationDto.setErrDesc("Invalid registration date ! "+ errorCodeService.findByCode(ERR_CODE_REGISTRATION_NOT_CREATED).getDescription());
            return registrationDto;
        }

        registrationDto.setId(null);
        Registration newRec = new Registration();
        newRec.setOfficer(registrationDto.getOfficer());
        newRec.setCourseDate(registrationDto.getCourseDate());
        newRec.setCourseTime(registrationDto.getCourseTime());
        newRec.setTypeOfCourse(registrationDto.getTypeOfCourse());
        newRec.setRegistrationNum(generator.GenerateRegistrationNumber());
        newRec.setStudent(student);
        newRec.setRegDate(dateReg);
        newRec.setOutlet(userService.getCurOutlet());
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);
        registrationDto.setRegistrationNum(newRec.getRegistrationNum());
        registrationDto.setId(newRec.getId());
        registrationDto.setStudent(newRec.getStudent());
        registrationDto.setErrCode(ERR_CODE_SUCCESS);
        registrationDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return registrationDto;

    }

    @Override
    public RegistrationDto update(RegistrationDto registrationDto, Long idOld) {
        return null;
    }

    @Override
    @Transactional
    public RegistrationDto update(RegistrationDto registrationDto) {

        User user = userService.getCurUserAsObj();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateReg = null;
        try {
            dateReg = sdf.parse(registrationDto.getStrRegDate());
        } catch (ParseException e) {

            registrationDto.setErrCode(ERR_CODE_REGISTRATION_NOT_UPDATED);
            registrationDto.setErrDesc("Invalid registration date ! "+ errorCodeService
                    .findByCode(ERR_CODE_REGISTRATION_NOT_UPDATED).getDescription());
            return registrationDto;
        }

        Optional<Student> studentOptional = studentService.findById(registrationDto.getStudent().getId());
        if (studentOptional== null){
            registrationDto.setErrCode(ERR_CODE_REGISTRATION_NOT_UPDATED);
            registrationDto.setErrDesc("Cannot find Student ! "+ errorCodeService
                    .findByCode(ERR_CODE_REGISTRATION_NOT_UPDATED).getDescription());
            return registrationDto;
        }

        Registration newRec = repository.getOne(registrationDto.getId());
        newRec.setOfficer(registrationDto.getOfficer());
        newRec.setCourseDate(registrationDto.getCourseDate());
        newRec.setCourseTime(registrationDto.getCourseTime());
        newRec.setTypeOfCourse(registrationDto.getTypeOfCourse());
//        newRec.setRegistrationNum(generator.GenerateRegistrationNumber());
        newRec.setStudent(studentOptional.get());
        newRec.setRegDate(dateReg);
        newRec.setOutlet(userService.getCurOutlet());
        newRec.setUpdatedBy(user);
        newRec = repository.save(newRec);

        registrationDto.setId(newRec.getId());
        registrationDto.setErrCode(ERR_CODE_SUCCESS);
        registrationDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return registrationDto;

    }

    @Transactional(readOnly = true)
    public Page<Registration> searchByFilter(RegistrationDto filterDto, int page, int total) {
        User user = userService.getCurUserAsObj();
        Sort sort = new Sort(Sort.Direction.DESC,"registrationNum");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setOfficer(filterDto.getOfficer() == null ? "%" : "%" + filterDto.getOfficer().trim() + "%");
        System.out.println("filter " + filterDto);
        Page<Registration> res = repository.findByFilter(filterDto,
                userService.getCurOutlet() == null? null : userService.getCurOutlet().getId(),
                pageRequest);

        return  res;

    }
    public Optional<Registration> findById(String registrationId) {
        return repository.findById(registrationId);
    }
}
