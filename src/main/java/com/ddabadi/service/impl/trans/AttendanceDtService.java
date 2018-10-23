package com.ddabadi.service.impl.trans;

import com.ddabadi.model.*;
import com.ddabadi.model.dto.AttendanceDtDto;
import com.ddabadi.repository.AttendanceDtRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.service.impl.master.StudentDtService;
import com.ddabadi.service.impl.master.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class AttendanceDtService  implements CustomService<AttendanceDtDto> {

    @Autowired private AttendanceHdService attendanceHdService;
    @Autowired private StudentDtService studentDtService;
    @Autowired private AttendanceDtRepository repository;
    @Autowired private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;

    @Override
    public AttendanceDtDto addnew(AttendanceDtDto attendanceDtDto) {
        Optional<AttendanceHd> hdOptional = attendanceHdService.findById(attendanceDtDto.getAttendanceHdId());
        if (!hdOptional.isPresent()){
            attendanceDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceDtDto.setErrDesc("Header ["+attendanceDtDto.getAttendanceHdId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return attendanceDtDto;
        }

        Optional<StudentDt> studentDtOpt = studentDtService.findByIdStudent(attendanceDtDto.getStudentId());
        if (!studentDtOpt.isPresent()){
            attendanceDtDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceDtDto.setErrDesc("Student ["+attendanceDtDto.getStudentId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return attendanceDtDto;
        }

        User user = userService.getCurUserAsObj();

        attendanceDtDto.setId(null);
        AttendanceDt newRec = new AttendanceDt();
        newRec.setStudentDt(studentDtOpt.get());
        newRec.setAttendanceHd(hdOptional.get());
        newRec.setTimeAttend(attendanceDtDto.getTimeAttend());
        newRec.setTimeHome(attendanceDtDto.getTimeHome());
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        attendanceDtDto.setId(newRec.getId());
        attendanceDtDto.setErrCode(ERR_CODE_SUCCESS);
        attendanceDtDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return attendanceDtDto;
    }

    @Override
    public AttendanceDtDto update(AttendanceDtDto attendanceDtDto, Long idOld) {
        return null;
    }

    @Override
    public AttendanceDtDto update(AttendanceDtDto attendanceDtDto) {
        return null;
    }
}
