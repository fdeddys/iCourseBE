package com.ddabadi.rest.security;

import com.ddabadi.config.JwtUtil;
import com.ddabadi.model.Role;
import com.ddabadi.model.User;
import com.ddabadi.model.dto.AccountDto;
import com.ddabadi.model.dto.UserDto;
import com.ddabadi.model.dto.UserLoginDto;
import com.ddabadi.service.impl.RoleServiceImpl;
import com.ddabadi.service.impl.UserRoleServiceImpl;
import com.ddabadi.service.impl.UserServiceImpl;
import com.ddabadi.util.PasswordEncode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
@Api(value = "auth", tags = "Auth for login")
public class AuthenticRestController {

    private static Logger log = LoggerFactory.getLogger(AuthenticRestController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired private UserRoleServiceImpl roleUserService;

    @Autowired private RoleServiceImpl roleService;

    @PostMapping(value = "token/authenticate")
    public ResponseEntity<Map<String, String >> generateToken(@RequestBody UserLoginDto user){
        Map<String, String > tokenMap = new HashMap<>();
        User existUser = userService.findOneByNameActive(user.getUsername());
        log.debug("Exsist {} ",existUser);
        if(existUser.getName() == null){
            tokenMap.put("id_token", "");
            return new ResponseEntity<>(tokenMap, HttpStatus.NOT_IMPLEMENTED);
        }

        log.debug("Login auth {} -- {} " , user.getUsername() , user.getPassword());

//        int totalRole = roleUserService.findByUserId(existUser.getId()).size();
//        if ( totalRole < 1 ) {
//            tokenMap.put("id_token", "");
//            return new ResponseEntity<>(tokenMap, HttpStatus.FORBIDDEN);
//        }

        String bCryptPass = PasswordEncode.getInstance().encode(user.getPassword());

        log.debug("Password encrypt {}",  bCryptPass);
        final Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.debug("POST context holder {} " , auth);
        log.debug("POST context holder - principal {} ",  auth.getPrincipal());

        User authUser = userService.findOneByName(user.getUsername());
        String token =  jwtUtil.generateToken(authUser);

        tokenMap.put("id_token", token);
        log.debug("token return to {} " , token);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }

    @GetMapping(value = "api/account")
    public ResponseEntity<AccountDto> apiAccount(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("GET context holder {} " , auth);
        log.debug("GET context holder - principal {} ", auth.getPrincipal());

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User userLogin = userService.findOneByName(userDetails.getUsername());

        List<Long> roleIdList = roleUserService.findByUserId(userLogin.getId());
        List<String> roleList = new ArrayList<>();

        AccountDto account = new AccountDto();
//        account.setActivated(userLogin.getActive()==1?true:false);
        account.setCreatedBy(userLogin.getCreatedBy());
        account.setCreatedDate(userLogin.getCreatedAt().toString());
        account.setEmail(userLogin.getEmail());
        account.setFirstName(userLogin.getName());
        account.setImageUrl("");
        account.setLastModifiedBy(userLogin.getUpdatedBy());
        account.setLastName("");
        account.setLangKey("EN");
        account.setLastModifiedDate(userLogin.getUpdatedAt().toString());

        roleIdList.forEach(idRole -> {
            Role role = roleService.findOne(idRole);
            if( role != null ){
                roleList.add(role.getName().toUpperCase());
            }
        });
        account.setRoleList(roleList);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @PostMapping(value = "adduser")
    private ResponseEntity<String> addUsr(@RequestBody UserDto userDto){

        // System.out.println("Login auth " + user.getName() + "--" + user.getPassword());

        // String bCryptPass = PasswordEncode.getInstance().encode(user.getPassword());

        // System.out.println("Password encrypt " + bCryptPass);
        userService.addnew(userDto);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
