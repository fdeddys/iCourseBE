package com.ddabadi.service.impl;


import com.ddabadi.model.User;
import com.ddabadi.model.UserRole;
import com.ddabadi.model.compositekey.UserRoleId;
import com.ddabadi.model.dto.FilterDto;
import com.ddabadi.model.dto.UserDto;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.repository.UserRepository;
import com.ddabadi.service.UserService;
import com.ddabadi.util.GenerateNumber;
import com.ddabadi.util.PasswordEncode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserServiceImpl implements  UserService, UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(readOnly = true)
    public User findOne(Long id) {
        log.debug("Find one by Id  {}", id );

        Optional<User> userOptional = repository.findById(id);
        return userOptional.orElseGet(User::new);
    }

    @Transactional(readOnly = true)
    public Page<User> findAllByPage(Integer page, Integer total) {
        log.debug("Find Pageable page {} , total {}", page, total);
        Sort sort = new Sort(Sort.Direction.ASC,"id").and(new Sort(Sort.Direction.ASC,"name"));
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        return repository.findAll(pageRequest) ;
    }

    public UserDto addnew(UserDto userDto) {
        try {

            String password = new GenerateNumber().randomize();
            PasswordEncode passwordEncode = PasswordEncode.getInstance();

            Optional<User> userCek = repository.findByName(userDto.getName());
            if (userCek.isPresent()) {
                userDto.setErrMsg("user name already exsist ! ");
                return userDto;
            }

            String username = this.getCurrentUser();
            log.debug("New Record ");
            User newRec = new User();
            newRec.setName(userDto.getName());
            newRec.setFirstName(userDto.getFirstName());
            newRec.setLastName(userDto.getLastName());
            newRec.setEmail(userDto.getEmail());
            newRec.setPassword(passwordEncode.encodeWithHash(password));
            newRec.setStatus(userDto.getStatus());
            newRec.setRememberToken("");
            newRec.setUpdatedBy(username);
            newRec.setCreatedBy(username);
            newRec = repository.save(newRec);

            byte[] asOldBytes = Base64.getEncoder().encode(password.getBytes());
            String curPassInString = new String(asOldBytes, "utf-8");
            userDto.setPassword(curPassInString);
            userDto.setId(newRec.getId());
            userDto.setErrMsg(null);
        } catch (Exception e) {
            log.debug("Error {}", e.getMessage());
            userDto.setErrMsg(e.getMessage());
        }

        return userDto;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto update(UserDto userDto, Long idOld) {

        UserDto result = new UserDto();
        try {
            Optional<User> optionalUser = repository.findById(idOld);
            if (optionalUser.isPresent()){

                String username = this.getCurrentUser();
                User oldMember = optionalUser.get();
                oldMember.setFirstName(userDto.getFirstName());
                oldMember.setLastName(userDto.getLastName());
                oldMember.setEmail(userDto.getEmail());
                oldMember.setStatus(userDto.getStatus());
                oldMember.setRememberToken(userDto.getRememberToken());
                oldMember.setUpdatedAt(new Date());
                oldMember.setUpdatedBy(username);
                oldMember = repository.save(oldMember);

                result.setId(oldMember.getId());
                result.setEmail(oldMember.getEmail());
                result.setStatus(oldMember.getStatus());
                result.setName(oldMember.getName());
            }else {
                result.setErrMsg("User not found");
            }
        } catch (Exception ex) {
            result.setErrMsg("Error " + ex.getMessage());
        }
        return result;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByName(username);
        if (!(optionalUser.isPresent())) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthority());
    }

    @Override
    public User findOneByName(String name) {
        Optional<User> optionalUser = repository.findByName(name);
        return optionalUser.orElseGet(User::new) ;
    }

    public User findOneByNameActive(String name) {
        Optional<User> optionalUser = repository.findByNameAndStatus(name, EntityStatus.ACTIVE);
        return optionalUser.orElseGet(User::new);
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto addRole( Long userId, Long roleId) {
        UserDto userDto = new UserDto();
        // cek apakah user dan role ini sudah ada?
        UserRole userRoleCheck = userRoleService.findByRoleIdAndUserId(roleId, userId);
        log.debug("Checkk {} ", userRoleCheck.getStatus());
        if   (userRoleCheck.getStatus() == null){

            User username = this.getCurUserAsObj();

            // user role tidak ada
            UserRoleId userRoleId = new UserRoleId();
            userRoleId.setRoleId(roleId);
            userRoleId.setUserId(userId);

            UserRole userRole = new UserRole();
            userRole.setUserRoleId(userRoleId);
            userRole.setStatus(EntityStatus.ACTIVE);
            userRole.setCreatedAt(new Date());
            userRole.setUpdatedAt(new Date());
            userRole.setCreatedBy(username);
            userRole.setUpdatedBy(username);
            userRoleService.addnew(userRole);
        } else{
            userDto.setErrMsg("Role has been registred");
            return userDto;
        }
        return userDto;
    }

    public UserDto changeStatusRole(UserDto userDto) {

        Optional<User> userOpt = repository.findById(userDto.getUserId());
        if (userOpt.isPresent()) {
            userDto.setErrMsg("user not found ");
        }

        UserRole userRoleCheck = userRoleService.findByRoleIdAndUserId(userDto.getRoleId(), userDto.getUserId());
        if   (userRoleCheck.getUserRoleId().getRoleId() == 0 ){
            // user role tidak ada
            userDto.setErrMsg("Role for this user not found ");
            return userDto;
        } else{
            User username = this.getCurUserAsObj();
            userRoleCheck.setUpdatedAt(new Date());
            userRoleCheck.setUpdatedBy(username);
            userRoleCheck.setStatus( userRoleCheck.getStatus().equals(EntityStatus.ACTIVE) ? EntityStatus.INACTIVE : EntityStatus.ACTIVE );
            userRoleService.addnew(userRoleCheck);
            return userDto;
        }
    }


    public String getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public User getCurUserAsObj(){

        String username = this.getCurrentUser();
        return this.findOneByName(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto changePassword(UserDto userDto) {

        UserDto resultUser = new UserDto();

        try {
            byte[] asOldBytes = Base64.getDecoder().decode(userDto.getOldPass());
            String curPassInString = new String(asOldBytes, "utf-8");
            log.debug("cur pass {} " , curPassInString);

            byte[] asNewBytes = Base64.getDecoder().decode(userDto.getPassword());
            String newPassInString = new String(asNewBytes, "utf-8");
            log.debug("new pass {} ", newPassInString);

            Optional<User> userOp = repository.findById(userDto.getId());
            if (userOp.isPresent() ) {
                User user = userOp.get();
                PasswordEncode passwordEncode = PasswordEncode.getInstance();
                boolean isvalid = passwordEncode.checkValid(curPassInString, user.getPassword());
                log.debug("user pass = {} check valid = {} ",  user.getPassword() , isvalid);
                if ( isvalid ) {
                    // user.getPassword().equals(currPass)
                    String newPass = passwordEncode.encode( newPassInString);
                    user.setPassword(newPass);
                    user.setUpdatedBy(user.getName());
                    user = repository.save(user);
                    resultUser.setName(user.getName());
                    resultUser.setId(user.getId());
                } else {
                    resultUser.setErrMsg("invalid old pass");
                }
            } else {
                resultUser.setErrMsg("user not found");
            }

        } catch (UnsupportedEncodingException e) {
            resultUser.setErrMsg("failed encoded from request");
        }

        return resultUser ;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto resetPassword(Long userId) {

        String randomPass = new GenerateNumber().randomize();
        UserDto resultUser = new UserDto();
        Optional<User> userOp = repository.findById(userId);
        if (userOp.isPresent() ) {
            User user = userOp.get();
            PasswordEncode passwordEncode = PasswordEncode.getInstance();

            String newPass = passwordEncode.encodeWithHash( randomPass );
            user.setPassword(newPass);
            user.setUpdatedAt(new Date());
            user.setUpdatedBy(user.getName());
            repository.save(user);
            byte[] asOldBytes = Base64.getEncoder().encode(randomPass.getBytes());
            try {
                String curPassInString = new String(asOldBytes, "utf-8");
                resultUser.setOldPass(curPassInString);
                log.debug("Random pass == {} ", randomPass);
            } catch (UnsupportedEncodingException e) {
                log.debug("err {} ", e.getMessage());
                resultUser.setErrMsg(e.getMessage());
            }
        } else {
            resultUser.setErrMsg("user not found");
        }
        return resultUser ;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> searchByFilter(FilterDto filterDto, int page, int total) {

        Sort sort = new Sort(Sort.Direction.ASC,"name").and(new Sort(Sort.Direction.ASC,"firstName"));
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        filterDto.setName(filterDto.getName() == null ? "%" : "%" + filterDto.getName().trim() + "%");
        filterDto.setEmail(filterDto.getEmail() == null ? "%" : "%" + filterDto.getEmail().trim() + "%");
        filterDto.setFirstName(filterDto.getName() == null ? "%" : "%" + filterDto.getFirstName().trim() + "%");
        filterDto.setLastName(filterDto.getEmail() == null ? "%" : "%" + filterDto.getLastName().trim() + "%");

        log.debug("filter {}", filterDto);

        return repository.findByFilter(filterDto, pageRequest) ;

    }

    public User save(User user) {
        return repository.save(user);
    }

    public User removeRole(Long userId, Long roleId) {
        UserDto userDto = new UserDto();
        Optional<User> userOpt = repository.findById(userId);
        if (userOpt.isPresent()) userDto.setErrMsg("user not found ");

        UserRole userRoleCheck = userRoleService.findByRoleIdAndUserId(roleId, userId);
        if   (userRoleCheck.getUserRoleId().getRoleId() == 0 ){
            userDto.setErrMsg("Role for this user not found ");
            return userDto;
        } else{
            userRoleService.delete(userRoleCheck);
            return new UserDto();
        }
    }

}
