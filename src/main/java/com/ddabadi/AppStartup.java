package com.ddabadi;

import com.ddabadi.model.*;
import com.ddabadi.model.compositekey.RoleMenuId;
import com.ddabadi.model.compositekey.UserRoleId;
import com.ddabadi.model.dto.*;
import com.ddabadi.model.enu.CourseType;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.model.enu.PaymentStatus;
import com.ddabadi.model.enu.PaymentType;
import com.ddabadi.repository.RoleRepository;
import com.ddabadi.repository.UserRepository;
import com.ddabadi.service.impl.*;
import com.ddabadi.service.impl.master.*;
import com.ddabadi.service.impl.trans.*;
import com.ddabadi.util.Globals;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger= Logger.getLogger(AppStartup.class);

    @Autowired private UserServiceImpl userService;
    @Autowired private RoleRepository roleService;
    @Autowired private MenuServiceImpl menuService;
    @Autowired private RoleMenuServiceImpl roleMenuService;
    @Autowired private UserRoleServiceImpl userRoleService;
    @Autowired private UserRepository userRepository;

    @Autowired private ErrCodeServiceImpl errCodeService;

    @Autowired private OutletGroupService outletGroupService;
    @Autowired private OutletService outletService;
    @Autowired private ClassesService classesService;
    @Autowired private RoomService roomService;

    @Autowired private TeacherService teacherService;
    @Autowired private StudentService studentService;

    @Autowired private AttendanceHdService attendanceHdService;
    @Autowired private AttendanceDtService attendanceDtService;

    @Autowired private RegistrationService registrationService;

    @Autowired private PaymentHdService paymentHdService;
    @Autowired private PaymentDtService paymentDtService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        Optional<User> optionalUser = userRepository.findByName("deddy");

        if (optionalUser.isPresent() ){
            return;
        }

        Globals.devMode = 1;
        Globals.userDevMode ="deddy";

        this.userRoleMenu();
        this.errorcode();

        //  -----------------------------------------------------------------------------------------------------------
        OutletGroupDto gr1 = new OutletGroupDto();
        gr1.setName("Example Group 1");
        gr1 = outletGroupService.addnew(gr1);

        OutletGroupDto gr2 = new OutletGroupDto();
        gr2.setName("Example Group 1");
        gr2 = outletGroupService.addnew(gr1);

        //  -----------------------------------------------------------------------------------------------------------
        OutletDto outlet1 = new OutletDto();
        outlet1.setName("Outlet 1");
        outlet1.setGroupOutletId(gr1.getId());
        outlet1.setAddress1("Address 1");
        outlet1.setAddress2("Address 1 other");
        outlet1.setRegistrationFee(BigDecimal.valueOf(500000));
        outlet1 = outletService.addnew(outlet1);

        Optional<User> userOptionaluser = userRepository.findByName("deddy");
        User user = userOptionaluser.get();
        user.setOutletId(outlet1.getId());
        userService.save(user);

        OutletDto outlet2 = new OutletDto();
        outlet2.setName("Outlet 2");
        outlet2.setGroupOutletId(gr1.getId());
        outlet2.setAddress1("Address 2");
        outlet2.setAddress2("Address 2 other");
        outlet2.setRegistrationFee(BigDecimal.valueOf(600000));
        outlet2 = outletService.addnew(outlet2);

        OutletDto outletA = new OutletDto();
        outletA.setName("Outlet A");
        outletA.setGroupOutletId(gr2.getId());
        outletA.setAddress1("Address A");
        outletA.setAddress2("Address A other");
        outletA.setRegistrationFee(BigDecimal.valueOf(1000000));
        outletA = outletService.addnew(outlet1);

        OutletDto outletB = new OutletDto();
        outletB.setName("Outlet B");
        outletB.setGroupOutletId(gr2.getId());
        outletB.setAddress1("Address B");
        outletB.setAddress2("Address B other");
        outletB.setRegistrationFee(BigDecimal.valueOf(1200000));
        outletB = outletService.addnew(outletB);

        //  -----------------------------------------------------------------------------------------------------------
        ClassesDto classesSD = new ClassesDto();
        classesSD.setName("Mata pelajaran SD");
        classesSD.setMonthlyFee(BigDecimal.valueOf(700000L));
        classesSD.setStatus(EntityStatus.ACTIVE);
        classesSD.setClassCode("CSD");
        classesSD = classesService.addnew(classesSD);

        ClassesDto classesSMP = new ClassesDto();
        classesSMP.setName("Mata Pelajaran SMP");
        classesSMP.setMonthlyFee(BigDecimal.valueOf(750000L));
        classesSMP.setStatus(EntityStatus.ACTIVE);
        classesSMP.setClassCode("CSMP");
        classesSMP = classesService.addnew(classesSMP);

        ClassesDto classesSMU = new ClassesDto();
        classesSMU.setName("Mata Pelajaran SMU");
        classesSMU.setMonthlyFee(BigDecimal.valueOf(800000L));
        classesSMU.setStatus(EntityStatus.ACTIVE);
        classesSMU.setClassCode("CSMU");
        classesSMU = classesService.addnew(classesSMU);

        ClassesDto classesMiaSD = new ClassesDto();
        classesMiaSD.setName("MIA SD");
        classesMiaSD.setMonthlyFee(BigDecimal.valueOf(750000L));
        classesMiaSD.setStatus(EntityStatus.ACTIVE);
        classesMiaSD.setClassCode("MIASD");
        classesMiaSD = classesService.addnew(classesMiaSD);

        ClassesDto classesMiaSMP = new ClassesDto();
        classesMiaSMP.setName("MIA SMP");
        classesMiaSMP.setMonthlyFee(BigDecimal.valueOf(750000L));
        classesMiaSMP.setStatus(EntityStatus.ACTIVE);
        classesMiaSMP.setClassCode("MIASMP");
        classesMiaSMP = classesService.addnew(classesMiaSMP);

        ClassesDto classesIng = new ClassesDto();
        classesIng.setName("B. Ingg");
        classesIng.setMonthlyFee(BigDecimal.valueOf(375000L));
        classesIng.setStatus(EntityStatus.ACTIVE);
        classesIng.setClassCode("INGG");
        classesIng = classesService.addnew(classesIng);

        //  -----------------------------------------------------------------------------------------------------------
        RoomDto room01 = new RoomDto();
        room01.setName("Room no 1");
        room01.setStatus(EntityStatus.ACTIVE);
        room01.setOutlet(outlet1);
        room01 = roomService.addnew(room01);

        RoomDto room02 = new RoomDto();
        room02.setName("Room no 2");
        room02.setStatus(EntityStatus.ACTIVE);
        room02.setOutlet(outlet1);
        room02 = roomService.addnew(room02);

        RoomDto roomA = new RoomDto();
        roomA.setName("Room A");
        roomA.setStatus(EntityStatus.ACTIVE);
        roomA.setOutlet(outlet2);
        roomA = roomService.addnew(roomA);

        RoomDto roomB = new RoomDto();
        roomB.setName("Room B");
        roomB.setStatus(EntityStatus.ACTIVE);
        roomB.setOutlet(outlet2);
        roomB = roomService.addnew(roomB);

        //  -----------------------------------------------------------------------------------------------------------
        TeacherDto teacher01 = new TeacherDto();
        teacher01.setName("Andi");
        teacher01.setBaseSalary(BigDecimal.valueOf(1000000L));
        teacher01.setAllowance(BigDecimal.valueOf(500000L));
        teacher01.setPhone("08112234");
        teacher01.setAddress1("alamat1");
        teacher01.setAddress2("alamat2");
        teacher01 = teacherService.addnew(teacher01);

        TeacherDto teacher02 = new TeacherDto();
        teacher02.setName("Budi");
        teacher02.setBaseSalary(BigDecimal.valueOf(1000000L));
        teacher02.setAllowance(BigDecimal.valueOf(500000L));
        teacher02.setPhone("08123456789");
        teacher02.setAddress1("alamat22");
        teacher02.setAddress2("alamat2222");
        teacher02 = teacherService.addnew(teacher02);

        TeacherDto teacher03 = new TeacherDto();
        teacher03.setName("Anton");
        teacher03.setBaseSalary(BigDecimal.valueOf(1000000L));
        teacher03.setAllowance(BigDecimal.valueOf(500000L));
        teacher03.setPhone("081333333");
        teacher03.setAddress1("alamat33");
        teacher03.setAddress2("alamat3");
        teacher03 = teacherService.addnew(teacher03);

        //  -----------------------------------------------------------------------------------------------------------
        StudentDto student01 = new StudentDto();
        student01.setName("Joni");
        student01.setPhone("07112211");
        student01.setSchool("SMA XX1");
        student01.setAddress1("alamat joni1");
        student01.setAddress2("alamat joni2");
        student01.setClassesIds( new String[]{classesSMU.getId()});
        student01 = studentService.addnew(student01);

        StudentDto student02 = new StudentDto();
        student02.setName("Ani");
        student02.setPhone("021eqeq");
        student02.setSchool("SMP 1");
        student02.setAddress1("alamat ani1");
        student02.setAddress2("alamat ani2");
        student02.setClassesIds(new String[]{classesSMP.getId()});
        student02 = studentService.addnew(student02);

        StudentDto student03 = new StudentDto();
        student03.setName("Herman");
        student03.setPhone("023456");
        student03.setSchool("SD aa");
        student03.setAddress1("alamat Herman 1");
        student03.setAddress2("alamat Herman 2");
        student03.setClassesIds(new String[]{classesSD.getId()});
        student03 = studentService.addnew(student03 );


        this.registration(classesSD.getId());
        this.paymentMonthly("201810", student02.getId());
        this.transaksiAttendance(outlet1, room01, teacher01, student01, student02, student03);
        this.registrationFree(classesSMU.getId());
    }

    void userRoleMenu(){
        User userAdmin = new User();
        userAdmin.setFirstName("deddy");
        userAdmin.setLastName("syuhendra");
        userAdmin.setRememberToken("");
        userAdmin.setEmail("ddd.dd@co");
        userAdmin.setPassword("$2a$10$JO27HeXnPJrkPGBMVU40a.2x1ShX1YLKzSvKYxlRtvhHahtxfJIiu");
        userAdmin.setStatus(EntityStatus.ACTIVE);
        userAdmin.setName("deddy");
        userAdmin.setCreatedBy("system");
        userAdmin.setUpdatedBy("system");
//        userAdmin.setOutletId();
        userAdmin = userService.save(userAdmin);
        logger.info("user id = " + userAdmin.getId());

        Role roleAdmin = new Role();
        roleAdmin.setDescription("Administrator");
        roleAdmin.setName("admin");
        roleAdmin.setCreatedBy(userAdmin);
        roleAdmin.setUpdatedBy(userAdmin);
        roleAdmin = roleService.save(roleAdmin);
        System.out.println("role admin id = "+ roleAdmin.getId());

        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setRoleId(roleAdmin.getId());
        userRoleId.setUserId(userAdmin.getId());

        UserRole userRole = new UserRole();
        userRole.setUserRoleId(userRoleId);
        userRole.setStatus(EntityStatus.ACTIVE);
        userRoleService.addnew(userRole);

        //  -----------------------------------------------------------------------------------------------------------
        Menu menuMaster = new Menu();
        menuMaster.setName("master");
        menuMaster.setDescription("Master");
        menuMaster.setParentId(0L);
        menuMaster.setIcon("settings");
        menuMaster.setStatus(1);
        menuMaster.setCreatedBy(userAdmin);
        menuMaster.setUpdatedBy(userAdmin);
        menuMaster = menuService.addnew(menuMaster);
        logger.info("Menu master id = "+ menuMaster.getId());

        RoleMenuId roleMenuId = new RoleMenuId();
        roleMenuId.setMenuId(menuMaster.getId());
        roleMenuId.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuMaster = new RoleMenu();
        roleMenuMaster.setStatus(EntityStatus.ACTIVE);
        roleMenuMaster.setRoleMenuId(roleMenuId);
        roleMenuMaster.setCreatedBy(userAdmin);
        roleMenuMaster.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuMaster);


        Menu menuGrOutlet = new Menu();
        menuGrOutlet.setName("group-outlet");
        menuGrOutlet.setDescription("Group Outlet");
        menuGrOutlet.setLink("group-outlet");
        menuGrOutlet.setParentId(menuMaster.getId());
        menuGrOutlet.setIcon("person");
        menuGrOutlet.setStatus(1);
        menuGrOutlet.setCreatedBy(userAdmin);
        menuGrOutlet.setUpdatedBy(userAdmin);
        menuGrOutlet = menuService.addnew(menuGrOutlet);

        RoleMenuId roleMenuIdGrOutlet = new RoleMenuId();
        roleMenuIdGrOutlet.setMenuId(menuGrOutlet.getId());
        roleMenuIdGrOutlet.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuGrOutlet = new RoleMenu();
        roleMenuGrOutlet.setStatus(EntityStatus.ACTIVE);
        roleMenuGrOutlet.setRoleMenuId(roleMenuIdGrOutlet);
        roleMenuGrOutlet.setCreatedBy(userAdmin);
        roleMenuGrOutlet.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuGrOutlet);


        Menu menuOutlet = new Menu();
        menuOutlet.setName("outlet");
        menuOutlet.setDescription("Outlet");
        menuOutlet.setLink("outlet");
        menuOutlet.setParentId(menuMaster.getId());
        menuOutlet.setIcon("person");
        menuOutlet.setStatus(1);
        menuOutlet.setCreatedBy(userAdmin);
        menuOutlet.setUpdatedBy(userAdmin);
        menuOutlet = menuService.addnew(menuOutlet);

        RoleMenuId roleMenuIdOutlet = new RoleMenuId();
        roleMenuIdOutlet.setMenuId(menuOutlet.getId());
        roleMenuIdOutlet.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuOutlet = new RoleMenu();
        roleMenuOutlet.setStatus(EntityStatus.ACTIVE);
        roleMenuOutlet.setRoleMenuId(roleMenuIdOutlet);
        roleMenuOutlet.setCreatedBy(userAdmin);
        roleMenuOutlet.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuOutlet);



        Menu menuRoom = new Menu();
        menuRoom.setName("room");
        menuRoom.setDescription("Room");
        menuRoom.setLink("room");
        menuRoom.setParentId(menuMaster.getId());
        menuRoom.setIcon("person");
        menuRoom.setStatus(1);
        menuRoom.setCreatedBy(userAdmin);
        menuRoom.setUpdatedBy(userAdmin);
        menuRoom = menuService.addnew(menuRoom);

        RoleMenuId roleMenuIdRoom = new RoleMenuId();
        roleMenuIdRoom.setMenuId(menuRoom.getId());
        roleMenuIdRoom.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuRoom = new RoleMenu();
        roleMenuRoom.setStatus(EntityStatus.ACTIVE);
        roleMenuRoom.setRoleMenuId(roleMenuIdRoom);
        roleMenuRoom.setCreatedBy(userAdmin);
        roleMenuRoom.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuRoom);



        Menu menuClass = new Menu();
        menuClass.setName("classes");
        menuClass.setDescription("Class");
        menuClass.setLink("classes");
        menuClass.setParentId(menuMaster.getId());
        menuClass.setIcon("person");
        menuClass.setStatus(1);
        menuClass.setCreatedBy(userAdmin);
        menuClass.setUpdatedBy(userAdmin);
        menuClass = menuService.addnew(menuClass);

        RoleMenuId roleMenuIdClass = new RoleMenuId();
        roleMenuIdClass.setMenuId(menuClass.getId());
        roleMenuIdClass.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuClass = new RoleMenu();
        roleMenuClass.setStatus(EntityStatus.ACTIVE);
        roleMenuClass.setRoleMenuId(roleMenuIdClass);
        roleMenuClass.setCreatedBy(userAdmin);
        roleMenuClass.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuClass);



        Menu menuStudent = new Menu();
        menuStudent.setName("student");
        menuStudent.setDescription("Student");
        menuStudent.setLink("student");
        menuStudent.setParentId(menuMaster.getId());
        menuStudent.setIcon("person");
        menuStudent.setStatus(1);
        menuStudent.setCreatedBy(userAdmin);
        menuStudent.setUpdatedBy(userAdmin);
        menuStudent = menuService.addnew(menuStudent);

        RoleMenuId roleMenuIdStudent = new RoleMenuId();
        roleMenuIdStudent.setMenuId(menuStudent.getId());
        roleMenuIdStudent.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuStudent = new RoleMenu();
        roleMenuStudent.setStatus(EntityStatus.ACTIVE);
        roleMenuStudent.setRoleMenuId(roleMenuIdStudent);
        roleMenuStudent.setCreatedBy(userAdmin);
        roleMenuStudent.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuStudent);



        Menu menuTeacher = new Menu();
        menuTeacher.setName("teacher");
        menuTeacher.setDescription("Teacher");
        menuTeacher.setLink("teacher");
        menuTeacher.setParentId(menuMaster.getId());
        menuTeacher.setIcon("person");
        menuTeacher.setStatus(1);
        menuTeacher.setCreatedBy(userAdmin);
        menuTeacher.setUpdatedBy(userAdmin);
        menuTeacher = menuService.addnew(menuTeacher);

        RoleMenuId roleMenuIdTeacher = new RoleMenuId();
        roleMenuIdTeacher.setMenuId(menuTeacher.getId());
        roleMenuIdTeacher.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuTeacher = new RoleMenu();
        roleMenuTeacher.setStatus(EntityStatus.ACTIVE);
        roleMenuTeacher.setRoleMenuId(roleMenuIdTeacher);
        roleMenuTeacher.setCreatedBy(userAdmin);
        roleMenuTeacher.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuTeacher);


        //  -----------------------------------------------------------------------------------------------------------
        Menu menuTrx = new Menu();
        menuTrx.setName("transaksi");
        menuTrx.setDescription("Transaksi");
        menuTrx.setParentId(0L);
        menuTrx.setIcon("settings");
        menuTrx.setStatus(1);
        menuTrx.setCreatedBy(userAdmin);
        menuTrx.setUpdatedBy(userAdmin);
        menuTrx = menuService.addnew(menuTrx);

        RoleMenuId roleMenuIdTrx = new RoleMenuId();
        roleMenuIdTrx.setMenuId(menuTrx.getId());
        roleMenuIdTrx.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuTrx = new RoleMenu();
        roleMenuTrx.setStatus(EntityStatus.ACTIVE);
        roleMenuTrx.setRoleMenuId(roleMenuIdTrx);
        roleMenuTrx.setCreatedBy(userAdmin);
        roleMenuTrx.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuTrx);



        Menu menuReg = new Menu();
        menuReg.setName("registration");
        menuReg.setDescription("Registration");
        menuReg.setLink("registration");
        menuReg.setParentId(menuTrx.getId());
        menuReg.setIcon("person");
        menuReg.setStatus(1);
        menuReg.setCreatedBy(userAdmin);
        menuReg.setUpdatedBy(userAdmin);
        menuReg = menuService.addnew(menuReg);

        RoleMenuId roleMenuIdReg = new RoleMenuId();
        roleMenuIdReg.setMenuId(menuReg.getId());
        roleMenuIdReg.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuReg = new RoleMenu();
        roleMenuReg.setStatus(EntityStatus.ACTIVE);
        roleMenuReg.setRoleMenuId(roleMenuIdReg);
        roleMenuReg.setCreatedBy(userAdmin);
        roleMenuReg.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuReg);



        Menu menuPayment = new Menu();
        menuPayment.setName("payment");
        menuPayment.setDescription("Payment");
        menuPayment.setLink("payment");
        menuPayment.setParentId(menuTrx.getId());
        menuPayment.setIcon("person");
        menuPayment.setStatus(1);
        menuPayment.setCreatedBy(userAdmin);
        menuPayment.setUpdatedBy(userAdmin);
        menuPayment = menuService.addnew(menuPayment);

        RoleMenuId roleMenuIPay = new RoleMenuId();
        roleMenuIPay.setMenuId(menuPayment.getId());
        roleMenuIPay.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuPay = new RoleMenu();
        roleMenuPay.setStatus(EntityStatus.ACTIVE);
        roleMenuPay.setRoleMenuId(roleMenuIPay);
        roleMenuPay.setCreatedBy(userAdmin);
        roleMenuPay.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuPay);


        //  -----------------------------------------------------------------------------------------------------------
        Menu menuReport = new Menu();
        menuReport.setName("report");
        menuReport.setDescription("Report");
        menuReport.setParentId(0L);
        menuReport.setIcon("settings");
        menuReport.setStatus(1);
        menuReport.setCreatedBy(userAdmin);
        menuReport.setUpdatedBy(userAdmin);
        menuReport = menuService.addnew(menuReport);

        RoleMenuId roleMenuIdReport = new RoleMenuId();
        roleMenuIdReport.setMenuId(menuReport.getId());
        roleMenuIdReport.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuReport = new RoleMenu();
        roleMenuReport.setStatus(EntityStatus.ACTIVE);
        roleMenuReport.setRoleMenuId(roleMenuIdReport);
        roleMenuReport.setCreatedBy(userAdmin);
        roleMenuReport.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuReport);



        Menu menuReportForm = new Menu();
        menuReportForm.setName("report-form");
        menuReportForm.setDescription("Report");
        menuReportForm.setLink("report-form");
        menuReportForm.setParentId(menuReport.getId());
        menuReportForm.setIcon("person");
        menuReportForm.setStatus(1);
        menuReportForm.setCreatedBy(userAdmin);
        menuReportForm.setUpdatedBy(userAdmin);
        menuReportForm = menuService.addnew(menuReportForm);

        RoleMenuId roleMenuIdReportForm = new RoleMenuId();
        roleMenuIdReportForm.setMenuId(menuReportForm.getId());
        roleMenuIdReportForm.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuReportForm = new RoleMenu();
        roleMenuReportForm.setStatus(EntityStatus.ACTIVE);
        roleMenuReportForm.setRoleMenuId(roleMenuIdReportForm);
        roleMenuReportForm.setCreatedBy(userAdmin);
        roleMenuReportForm.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuReportForm);


        //  -----------------------------------------------------------------------------------------------------------
        Menu menuUtility = new Menu();
        menuUtility.setName("utility");
        menuUtility.setDescription("Utility");
        menuUtility.setParentId(0L);
        menuUtility.setIcon("settings");
        menuUtility.setStatus(1);
        menuUtility.setCreatedBy(userAdmin);
        menuUtility.setUpdatedBy(userAdmin);
        menuUtility = menuService.addnew(menuUtility);

        RoleMenuId roleMenuIdUtil = new RoleMenuId();
        roleMenuIdUtil.setMenuId(menuUtility.getId());
        roleMenuIdUtil.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuUtil = new RoleMenu();
        roleMenuUtil.setStatus(EntityStatus.ACTIVE);
        roleMenuUtil.setRoleMenuId(roleMenuIdUtil);
        roleMenuUtil.setCreatedBy(userAdmin);
        roleMenuUtil.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuUtil);



        Menu menuUser = new Menu();
        menuUser.setName("user");
        menuUser.setDescription("User");
        menuUser.setLink("user");
        menuUser.setParentId(menuUtility.getId());
        menuUser.setIcon("person");
        menuUser.setStatus(1);
        menuUser.setCreatedBy(userAdmin);
        menuUser.setUpdatedBy(userAdmin);
        menuUser = menuService.addnew(menuUser);

        RoleMenuId roleMenuIdUser = new RoleMenuId();
        roleMenuIdUser.setMenuId(menuUser.getId());
        roleMenuIdUser.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuUser = new RoleMenu();
        roleMenuUser.setStatus(EntityStatus.ACTIVE);
        roleMenuUser.setRoleMenuId(roleMenuIdUser);
        roleMenuUser.setCreatedBy(userAdmin);
        roleMenuUser.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuUser);



        Menu menuRole = new Menu();
        menuRole.setName("role");
        menuRole.setDescription("Role");
        menuRole.setLink("role");
        menuRole.setParentId(menuUtility.getId());
        menuRole.setIcon("person");
        menuRole.setStatus(1);
        menuRole.setCreatedBy(userAdmin);
        menuRole.setUpdatedBy(userAdmin);
        menuRole = menuService.addnew(menuRole);

        RoleMenuId roleMenuIdRole = new RoleMenuId();
        roleMenuIdRole.setMenuId(menuRole.getId());
        roleMenuIdRole.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuRole = new RoleMenu();
        roleMenuRole.setStatus(EntityStatus.ACTIVE);
        roleMenuRole.setRoleMenuId(roleMenuIdRole);
        roleMenuRole.setCreatedBy(userAdmin);
        roleMenuRole.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuRole);

        Menu menuAccMtx = new Menu();
        menuAccMtx.setName("access-matrix");
        menuAccMtx.setDescription("Access Matrix");
        menuAccMtx.setLink("access-matrix");
        menuAccMtx.setParentId(menuUtility.getId());
        menuAccMtx.setIcon("person");
        menuAccMtx.setStatus(1);
        menuAccMtx.setCreatedBy(userAdmin);
        menuAccMtx.setUpdatedBy(userAdmin);
        menuAccMtx = menuService.addnew(menuAccMtx);

        RoleMenuId roleMenuIdAccMtx = new RoleMenuId();
        roleMenuIdAccMtx.setMenuId(menuAccMtx.getId());
        roleMenuIdAccMtx.setRoleId(roleAdmin.getId());

        RoleMenu roleMenuAccMtx = new RoleMenu();
        roleMenuAccMtx.setStatus(EntityStatus.ACTIVE);
        roleMenuAccMtx.setRoleMenuId(roleMenuIdAccMtx);
        roleMenuAccMtx.setCreatedBy(userAdmin);
        roleMenuAccMtx.setUpdatedBy(userAdmin);
        roleMenuService.save(roleMenuAccMtx);
    }

    void transaksiAttendance(Outlet outlet, Room room, Teacher teacher, Student ... students){
        AttendanceHdDto hd = new AttendanceHdDto();

        hd.setStrAttendanceDate("2018-10-09");
        hd.setStrAttendanceTime("13:00");
        hd.setOutletId(outlet.getId());
        hd.setRoomId(room.getId());
        hd.setTeacherId(teacher.getId());
        hd = attendanceHdService.addnew(hd);

        hd.setStrAttendanceTime("17:00");
        hd = attendanceHdService.update(hd);

        for (Student student: students) {
            AttendanceDtDto attendanceDt = new AttendanceDtDto();
            attendanceDt.setStudentId(student.getId());
            attendanceDt.setAttendanceHdId(hd.getId());
            attendanceDt.setTimeAttend("17:00");
            attendanceDt.setTimeHome(null);
            attendanceDtService.addnew(attendanceDt);
        }

    }

    void errorcode(){
        //  -----------------------------------------------------------------------------------------------------------
        ErrCode err0 = new ErrCode();
        err0.setCode("00");
        err0.setDescription("Success");
        errCodeService.save(err0);

        ErrCode err1 = new ErrCode();
        err1.setCode("01");
        err1.setDescription("Id Not Found");
        errCodeService.save(err1);

        ErrCode err2 = new ErrCode();
        err2.setCode("02");
        err2.setDescription("Schedule cannot created");
        errCodeService.save(err2);

        ErrCode err3 = new ErrCode();
        err3.setCode("03");
        err3.setDescription("Registration cannot created");
        errCodeService.save(err3);

        ErrCode err4 = new ErrCode();
        err4.setCode("04");
        err4.setDescription("Registration cannot update");
        errCodeService.save(err4);

        ErrCode err5 = new ErrCode();
        err5.setCode("05");
        err5.setDescription("Payment Failed");
        errCodeService.save(err5);

    }

    void registration(String classId){
//        Optional<Classes> optionalClasses = classesService.findById(classId);

        RegistrationDto registrationDto = new RegistrationDto();
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Student reg");
        studentDto.setPhone("qqeqweq");
        studentDto.setSchool("SD ");
        studentDto.setAddress1("alamat  1");
        studentDto.setAddress2("alamat  2");
        studentDto.setClassesIds(new String[]{classId});

        registrationDto.setStudentDto(studentDto);
        registrationDto.setStrRegDate("2018-10-31");
        registrationDto.setCourseDate("Senen");
        registrationDto.setCourseTime("13:00");
        registrationDto.setOfficer("Ibu ABC");
        registrationDto.setTypeOfCourse(CourseType.MIA);
        registrationDto = registrationService.addnew(registrationDto);
        paymentReg(registrationDto.getId(), registrationDto.getStudent().getId());

        }

    void registrationFree(String classId){

        RegistrationDto registrationDto = new RegistrationDto();
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Student reg free");
        studentDto.setPhone("123");
        studentDto.setSchool("SMU ");
        studentDto.setAddress1("alamat  1");
        studentDto.setAddress2("alamat  2");
        studentDto.setClassesIds(new String[]{classId});

        registrationDto.setStudentDto(studentDto);
        registrationDto.setStrRegDate("2018-10-31");
        registrationDto.setCourseDate("Selasa");
        registrationDto.setCourseTime("15:00");
        registrationDto.setOfficer("Ibu ANC");
        registrationDto.setTypeOfCourse(CourseType.PELAJARAN_SEKOLAH);
        registrationDto = registrationService.addnew(registrationDto);

        paymenRegFree(registrationDto.getId(), registrationDto.getStudent().getId());

    }

    void paymentReg(String registrationId, String studentId){
        PaymentHdDto paymentHd = new PaymentHdDto();
        paymentHd.setDescription("Payment untuk blaaa");
        paymentHd.setStudentId(studentId);
        paymentHd = paymentHdService.addnew(paymentHd);

//        List<PaymentDtDto> listPay = new ArrayList<>();
//        listPay.add(paymentDtDto);
        PaymentDtDto paymentDtDto = new PaymentDtDto();
        paymentDtDto.setPaymentHdId(paymentHd.getId());
        paymentDtDto.setPaymentType(PaymentType.REGISTRATION);
        paymentDtDto.setRegistrationId(registrationId);
        paymentDtService.addnew(paymentDtDto);

        paymentHdService.changeStatus(paymentHd, PaymentStatus.APPROVED);
    }

    void paymentMonthly(String yearMonth, String studentId){
        PaymentHdDto paymentHd = new PaymentHdDto();
        paymentHd.setDescription("Payment untuk monthly");
        paymentHd.setStudentId(studentId);
        paymentHd = paymentHdService.addnew(paymentHd);

//        List<PaymentDtDto> listPay = new ArrayList<>();
//        listPay.add(paymentDtDto);
        PaymentDtDto paymentDtDto = new PaymentDtDto();
        paymentDtDto.setPaymentHdId(paymentHd.getId());
        paymentDtDto.setPaymentType(PaymentType.MONTHLY);
        paymentDtDto.setPaymentMonth(yearMonth);
        paymentDtService.addnew(paymentDtDto);

        paymentHdService.changeStatus(paymentHd, PaymentStatus.APPROVED);
    }

    void paymenRegFree(String registrationId, String studentId){
        PaymentHdDto paymentHd = new PaymentHdDto();
        paymentHd.setDescription("Payment untuk Free");
        paymentHd.setStudentId(studentId);
        paymentHd = paymentHdService.addnew(paymentHd);


        PaymentDtDto paymentDtDto = new PaymentDtDto();
        paymentDtDto.setPaymentHdId(paymentHd.getId());
        paymentDtDto.setPaymentType(PaymentType.MONTHLY_FREE_REG);
        paymentDtDto.setDateStartFreeReg("2018-10-01");
        paymentDtDto.setRegistrationId(registrationId);
        paymentDtService.addnew(paymentDtDto);

        paymentHdService.changeStatus(paymentHd, PaymentStatus.APPROVED);
    }

}
