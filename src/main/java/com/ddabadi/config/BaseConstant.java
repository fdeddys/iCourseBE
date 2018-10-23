package com.ddabadi.config;

public class BaseConstant {

    public static final long ACCESS_TOKEN_VALID_IN_SECONDS = 8*60*60;
    public static final String SIGNING_KEY = "ddabadi_secret_key_xx";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";


    public static final String DATE_TIME_PATERN ="yyyy-MM-dd HH:mm:ss";

    public static final String PAYMENT_HEADER_EXTENTION = "F";

    public static final String PAYMENT_DESC_REGISTRATION = "Registration Payment";
    public static final String PAYMENT_DESC_MONTHLY = "Monthly Payment";
    public static final String PAYMENT_DESC_MONTHLY_REGISTRATION = "Monthly Payment from Registration";
    public static final String PAYMENT_DESC_FREE_REGISTRATION = "Monthly Free from Registration";

}
