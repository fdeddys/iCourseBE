package com.ddabadi;

import com.ddabadi.model.*;
import com.ddabadi.model.compositekey.RoleMenuId;
import com.ddabadi.model.compositekey.UserRoleId;
import com.ddabadi.model.dto.UserRoleDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.RoleRepository;
import com.ddabadi.repository.UserRepository;
import com.ddabadi.service.impl.MenuServiceImpl;
import com.ddabadi.service.impl.RoleMenuServiceImpl;
import com.ddabadi.service.impl.UserRoleServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

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

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        Optional<User> optionalUser = userRepository.findByName("deddy");

//        User checkUser = userService.findOneByName("deddy");
//        logger.info("----------------------------------------> " +checkUser);
        if (optionalUser.isPresent() ){
            return;
        }

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
        menuOutlet.setLink("Outlet");
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
        menuClass.setName("classcourse");
        menuClass.setDescription("Class");
        menuClass.setLink("classcourse");
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
        roleMenuService.save(roleMenuClass);



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
}
