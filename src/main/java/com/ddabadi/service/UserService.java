package com.ddabadi.service;

import com.ddabadi.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDetails loadUserByUsername(String name) throws UsernameNotFoundException;
    User findOneByName(String name);
}
