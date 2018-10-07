package com.ddabadi.service.impl.trans.master;

import com.ddabadi.model.Classes;
import com.ddabadi.model.Student;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.StudentDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.StudentRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.util.GenerateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class StudentService implements CustomService<StudentDto> {

    @Autowired private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private StudentRepository repository;
    @Autowired private GenerateNumber generator;
    @Autowired private ClassesService classesService;

    @Override
    public StudentDto addnew(StudentDto studentDto) {

        Optional<Classes> classesOptional = classesService.findById(studentDto.getClassesId());
        if (!classesOptional.isPresent()){
            studentDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDto.setErrDesc("Class ["+studentDto.getClassesId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return studentDto;
        }

        User user = userService.getCurUserAsObj();

        studentDto.setId(null);
        Student newRec = new Student();
        newRec.setName(studentDto.getName());
        newRec.setStudentCode(generator.generateStudentCode(studentDto.getName()));
        newRec.setAddress1(studentDto.getAddress1());
        newRec.setAddress2(studentDto.getAddress2());
        newRec.setPhone(studentDto.getPhone());
        newRec.setSchool(studentDto.getSchool());
        newRec.setOutlet(user.getOutlet());
        newRec.setClasses(classesOptional.get());
        newRec.setStatus(EntityStatus.ACTIVE);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        studentDto.setId(newRec.getId());
        studentDto.setStudentCode(newRec.getStudentCode());
        studentDto.setErrCode(ERR_CODE_SUCCESS);
        studentDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return studentDto;
    }

    @Override
    public StudentDto update(StudentDto studentDto, Long idOld) {
        return null;
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        Optional<Classes> classesOptional = classesService.findById(studentDto.getClassesId());
        if (!classesOptional.isPresent()){
            studentDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDto.setErrDesc("Class ["+studentDto.getClassesId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return studentDto;
        }


        User user = userService.getCurUserAsObj();

        Optional<Student> updateDataOpt = repository.findById(studentDto.getId());
        if (updateDataOpt.isPresent()){
            Student updateRec = updateDataOpt.get();

            updateRec.setName(studentDto.getName());
            updateRec.setAddress1(studentDto.getAddress1());
            updateRec.setAddress2(studentDto.getAddress2());
            updateRec.setPhone(studentDto.getPhone());
            updateRec.setSchool(studentDto.getSchool());
            updateRec.setStatus(studentDto.getStatus());
            updateRec.setClasses(classesOptional.get());
            updateRec.setUpdatedBy(user);

            repository.save(updateRec);
            studentDto.setErrCode(ERR_CODE_SUCCESS);
            studentDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            studentDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return studentDto;
    }

    public Optional<Student> findById(String studentId) {
        return repository.findById(studentId);
    }

    public Page<Student> searchByFilter(StudentDto filterDto, Integer page, Integer total) {
        User user = userService.getCurUserAsObj();
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        System.out.println("filter " + filterDto);
        Page<Student> res = repository.findByFilter(filterDto,
                user.getOutlet() == null? null : user.getOutlet().getId(),
                pageRequest);

        return  res;
    }
}
