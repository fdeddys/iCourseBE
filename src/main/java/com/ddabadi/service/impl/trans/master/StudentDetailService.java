package com.ddabadi.service.impl.trans.master;

import com.ddabadi.model.Classes;
import com.ddabadi.model.Student;
import com.ddabadi.model.StudentDetail;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.StudentDetailDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.StudentDetailRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.util.GenerateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class StudentDetailService implements CustomService<StudentDetailDto> {

    @Autowired
    private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private StudentDetailRepository repository;
    @Autowired private StudentService studentService;
    @Autowired private ClassesService classesService;

    @Override
    public StudentDetailDto addnew(StudentDetailDto studentDetailDto) {
        User user = userService.getCurUserAsObj();
        Optional<Classes> classesOptional = classesService.findById(studentDetailDto.getClassId());
        if (!classesOptional.isPresent()){
           studentDetailDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
           studentDetailDto.setErrDesc("Classes ["+studentDetailDto.getClassId()+"]" +
                   errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
           return studentDetailDto;
        }

        Optional<Student> studentOptional = studentService.findById(studentDetailDto.getStudentId());
        if (!studentOptional.isPresent()){
            studentDetailDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDetailDto.setErrDesc("Student ["+studentDetailDto.getClassId()+"]" +
                    errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return studentDetailDto;
        }

        studentDetailDto.setId(null);
        StudentDetail newRec = new StudentDetail();
        newRec.setClasses(classesOptional.get());
        newRec.setStudent(studentOptional.get());
        newRec.setStatus(EntityStatus.ACTIVE);
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        studentDetailDto.setId(newRec.getId());
        studentDetailDto.setClasses(newRec.getClasses());
        studentDetailDto.setStudent(newRec.getStudent());
        studentDetailDto.setErrCode(ERR_CODE_SUCCESS);
        studentDetailDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return studentDetailDto;
    }

    @Override
    public StudentDetailDto update(StudentDetailDto studentDetailDto, Long idOld) {
        return null;
    }

    @Override
    public StudentDetailDto update(StudentDetailDto studentDetailDto) {

        Optional<StudentDetail> updateDataOpt = repository.findById(studentDetailDto.getId());
        if (updateDataOpt.isPresent()){
            User user = userService.getCurUserAsObj();
            Optional<Classes> classesOptional = classesService.findById(studentDetailDto.getClassId());
            if (!classesOptional.isPresent()){
                studentDetailDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
                studentDetailDto.setErrDesc("Classes ["+studentDetailDto.getClassId()+"]" +
                        errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
                return studentDetailDto;
            }

            Optional<Student> studentOptional = studentService.findById(studentDetailDto.getStudentId());
            if (!studentOptional.isPresent()){
                studentDetailDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
                studentDetailDto.setErrDesc("Student ["+studentDetailDto.getClassId()+"]" +
                        errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
                return studentDetailDto;
            }

            StudentDetail updateRec = updateDataOpt.get();

            updateRec.setClasses(classesOptional.get());
            updateRec.setStudent(studentOptional.get());
            updateRec.setStatus(EntityStatus.ACTIVE);
            updateRec.setUpdatedBy(user);
            updateRec.setCreatedBy(user);
            updateRec = repository.save(updateRec);
            updateRec.setUpdatedBy(user);

            repository.save(updateRec);
            studentDetailDto.setErrCode(ERR_CODE_SUCCESS);
            studentDetailDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        } else {
            studentDetailDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDetailDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }

        return studentDetailDto;
    }

}