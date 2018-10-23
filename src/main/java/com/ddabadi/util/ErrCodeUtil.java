package com.ddabadi.util;

import com.ddabadi.service.impl.ErrCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ErrCodeUtil {

    @Autowired private ErrCodeServiceImpl errCodeService;

    public String findDesc(String errCode){
        return errCodeService.findByCode(errCode).getDescription();
    }
}
