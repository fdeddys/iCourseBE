package com.ddabadi.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateNumber {

    public String randomize(){
        String random = UUID.randomUUID().toString();
        return random.substring(0, 8);
    }

}
