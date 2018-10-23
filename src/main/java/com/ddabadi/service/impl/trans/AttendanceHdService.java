package com.ddabadi.service.impl.trans;

import com.ddabadi.model.AttendanceHd;
import com.ddabadi.model.Room;
import com.ddabadi.model.Teacher;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.AttendanceHdDto;

import com.ddabadi.repository.AttendanceHdRepository;
import com.ddabadi.service.CustomService;
import com.ddabadi.service.impl.ErrCodeServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.service.impl.master.RoomService;
import com.ddabadi.service.impl.master.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ddabadi.config.BaseErrorCode.ERR_CODE_ID_NOT_FOUND;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SCHEDULE_NOT_CREATED;
import static com.ddabadi.config.BaseErrorCode.ERR_CODE_SUCCESS;

@Service
public class AttendanceHdService implements CustomService<AttendanceHdDto> {

    @Autowired private AttendanceHdRepository repository;
    @Autowired private UserServiceImpl userService;
    @Autowired private ErrCodeServiceImpl errorCodeService;
    @Autowired private RoomService roomService;
    @Autowired private TeacherService teacherService;

    public String checkIfScheduleAlreadyExsist(AttendanceHdDto attendanceHdDto) {

        String result = "Schedule already exist";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
        try {
            Date dateAttendace = null;
            Date timeAttendace = null;
            dateAttendace = sdf1.parse(attendanceHdDto.getStrAttendanceDate());
            timeAttendace = sdf2.parse(attendanceHdDto.getStrAttendanceTime());

            User user = userService.getCurUserAsObj();

            List<AttendanceHd> list = repository.findAttendance(attendanceHdDto.getTeacherId(),
                    attendanceHdDto.getRoomId(),
                    attendanceHdDto.getOutletId(),
                    dateAttendace,
                    timeAttendace);
            list.stream()
                    .filter(attendanceHd -> !attendanceHd.getId().equals(attendanceHdDto.getId()))
                    .collect(Collectors.toList());

            if (list.isEmpty()) result ="OK";

        } catch (ParseException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    @Override
    public AttendanceHdDto addnew(AttendanceHdDto attendanceHdDto) {

        String resultCheck = checkIfScheduleAlreadyExsist(attendanceHdDto);
        if (resultCheck != "OK"){
            attendanceHdDto.setErrCode(ERR_CODE_SCHEDULE_NOT_CREATED);
            attendanceHdDto.setErrDesc("[" + resultCheck +"]" + errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        }

        Optional<Room> roomOptional = roomService.findById(attendanceHdDto.getRoomId());
        if (!roomOptional.isPresent()){
            attendanceHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceHdDto.setErrDesc("Room ["+attendanceHdDto.getRoomId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return attendanceHdDto;
        }

        Optional<Teacher> teacherOpt = teacherService.findById(attendanceHdDto.getTeacherId());
        if (!teacherOpt.isPresent()){
            attendanceHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceHdDto.setErrDesc("Teacher ["+attendanceHdDto.getTeacherId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return attendanceHdDto;
        }

        User user = userService.getCurUserAsObj();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
        Date dateAttendace = null;
        Date timeAttendace = null;
        try {
            dateAttendace = sdf1.parse(attendanceHdDto.getStrAttendanceDate());
            timeAttendace = sdf2.parse(attendanceHdDto.getStrAttendanceTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        attendanceHdDto.setId(null);
        AttendanceHd newRec = new AttendanceHd();
        newRec.setTeacher(teacherOpt.get());
        newRec.setAttendanceDate(dateAttendace);
        newRec.setAttendanceTime(timeAttendace);
        newRec.setRoom(roomOptional.get());
        newRec.setOutlet(userService.getCurOutlet());
        newRec.setUpdatedBy(user);
        newRec.setCreatedBy(user);
        newRec = repository.save(newRec);

        attendanceHdDto.setId(newRec.getId());
        attendanceHdDto.setErrCode(ERR_CODE_SUCCESS);
        attendanceHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        return attendanceHdDto;
    }

    @Override
    public AttendanceHdDto update(AttendanceHdDto attendanceHdDto, Long idOld) {
        return null;
    }

    @Override
    public AttendanceHdDto update(AttendanceHdDto attendanceHdDto) {
        String resultCheck = checkIfScheduleAlreadyExsist(attendanceHdDto);
        if (resultCheck != "OK"){
            attendanceHdDto.setErrCode(ERR_CODE_SCHEDULE_NOT_CREATED);
            attendanceHdDto.setErrDesc("[" + resultCheck +"]" + errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        }

        Optional<Room> roomOptional = roomService.findById(attendanceHdDto.getRoomId());
        if (!roomOptional.isPresent()){
            attendanceHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceHdDto.setErrDesc("Room ["+attendanceHdDto.getRoomId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return attendanceHdDto;
        }

        Optional<Teacher> teacherOpt = teacherService.findById(attendanceHdDto.getTeacherId());
        if (!teacherOpt.isPresent()){
            attendanceHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceHdDto.setErrDesc("Teacher ["+attendanceHdDto.getTeacherId()+"] "+errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
            return attendanceHdDto;
        }

        User user = userService.getCurUserAsObj();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
        Date dateAttendace = null;
        Date timeAttendace = null;
        try {
            dateAttendace = sdf1.parse(attendanceHdDto.getStrAttendanceDate());
            timeAttendace = sdf2.parse(attendanceHdDto.getStrAttendanceTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Optional<AttendanceHd> rec = repository.findById(attendanceHdDto.getId());
        if (rec.isPresent()) {
            AttendanceHd newRec = rec.get();
            newRec.setTeacher(teacherOpt.get());
            newRec.setAttendanceDate(dateAttendace);
            newRec.setAttendanceTime(timeAttendace);
            newRec.setRoom(roomOptional.get());
            newRec.setOutlet(userService.getCurOutlet());
            newRec.setUpdatedBy(user);
            newRec.setCreatedBy(user);
            newRec = repository.save(newRec);

            attendanceHdDto.setId(newRec.getId());
            attendanceHdDto.setErrCode(ERR_CODE_SUCCESS);
            attendanceHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_SUCCESS).getDescription());
        }
        else {
            attendanceHdDto.setErrCode(ERR_CODE_ID_NOT_FOUND);
            attendanceHdDto.setErrDesc(errorCodeService.findByCode(ERR_CODE_ID_NOT_FOUND).getDescription());
        }


        return attendanceHdDto;
    }

    public Optional<AttendanceHd> findById(String attendanceHdId) {

        return repository.findById(attendanceHdId);
    }
}
