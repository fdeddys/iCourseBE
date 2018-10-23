package com.ddabadi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.ddabadi.config.BaseConstant.DATE_TIME_PATERN;

public class DateUtil {



    public static LocalDateTime toLocalDateTime(String strDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
        return dateTime;
    }

    public static LocalDateTime stringToLocalDate(String tgl) throws ParseException {

//        String tgl1 =  "2018-08-07 01:02:03 ";
//        String tgl1 =  "2018-08-01 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATERN);

        Date tglHasil = sdf.parse(tgl);
        System.out.println(tglHasil);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tglHasil);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minuts = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        LocalDateTime localDateTime = LocalDateTime.of(year,month,day,hour,minuts,sec);

        return (localDateTime);


    }

}
