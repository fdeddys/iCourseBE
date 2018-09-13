package com.ddabadi;

import com.ddabadi.model.User;
import com.ddabadi.model.enu.EntityStatus;
import com.ddabadi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

@Controller
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired private UserServiceImpl userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        User user = new User();
        user.setFirstName("deddy");
        user.setLastName("syuhendra");
        user.setRememberToken("");
        user.setEmail("ddd.dd@co");
        user.setPassword("$2a$10$JO27HeXnPJrkPGBMVU40a.2x1ShX1YLKzSvKYxlRtvhHahtxfJIiu");
        user.setStatus(EntityStatus.ACTIVE);
        user.setName("deddy");
        userService.save(user);
    }
}
