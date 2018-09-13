package com.ddabadi.util;

import org.springframework.beans.BeanUtils;

public class SmStockUtil {

    public static void copyBean(Object source, Object target ){
        BeanUtils.copyProperties(source, target);
    }
}
