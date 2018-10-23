package com.ddabadi.service.impl.master;

import com.ddabadi.model.Classes;
import com.ddabadi.model.Student;
import com.ddabadi.model.StudentDt;
import com.ddabadi.model.User;

import com.ddabadi.model.dto.StudentDtDto;
import com.ddabadi.repository.StudentDtRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class StudentDtService implements CustomService<StudentDtDto> {

    @Autowired private StudentService studentService;
    @Autowired private StudentDtRepository repository;
    @Autowired private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private ClassesService classesService;

    @Override
    public StudentDtDto addnew(StudentDtDto studentDtDto) {


        Optional<Student> studentOptional = studentService.findById(studentDtDto.getStudentId());
        if (!studentOptional.isPresent()){
            studentDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDtDto.setErrDesc("Student ["+studentDtDto.getStudentId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return studentDtDto;
        }

        Optional<Classes> classesOptional = classesService.findById(studentDtDto.getClassesId());
        if (!classesOptional.isPresent()){
            studentDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            studentDtDto.setErrDesc("Classes ["+studentDtDto.getClassesId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return studentDtDto;
        }

        User user = userService.getCurUserAsObj();

        studentDtDto.setId(null);
        StudentDt newRec = new StudentDt();
        newRec.setStudent(studentOptional.get());
        newRec.setClasses(classesOptional.get());
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        studentDtDto.setId(newRec.getId());
        studentDtDto.setErrCode(ERR_CODE_SUCCESS);
        studentDtDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return studentDtDto;
    }

    @Override
    public StudentDtDto update(StudentDtDto studentDtDto, Long idOld) {
        return null;
    }

    @Override
    public StudentDtDto update(StudentDtDto studentDtDto) {
        return null;
    }

    public Optional<StudentDt> findByIdStudent(String studentId) {
        return repository.findByIdStudent(studentId);
    }
}
