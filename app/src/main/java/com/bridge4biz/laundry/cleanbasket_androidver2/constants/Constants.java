package com.bridge4biz.laundry.cleanbasket_androidver2.constants;

/**
 * Created by gingeraebi on 2016. 6. 17..
 */
public class Constants {
    public static final String DAUM_API_KEY = "c635735e83c17d5a00ce69beca8fae17";

    //Server Response 값
    public static final int SESSION_EXPIRED = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int EMAIL_ERROR = 3;
    public static final int PASSWORD_ERROR = 4;
    public static final int ACCOUNT_VALID = 5;
    public static final int ACCOUNT_INVALID = 6;
    public static final int ACCOUNT_ENABLED = 8;
    public static final int ACCOUNT_DISABLED = 9;
    public static final int ROLE_ADMIN = 10;
    public static final int ROLE_DELIVERER = 11;
    public static final int ROLE_MEMBER = 12;
    public static final int ROLE_INVALID = 13;
    public static final int IMAGE_WRITE_ERROR = 14;
    public static final int IMPOSSIBLE = 15;
    public static final int ACCOUNT_DUPLICATION = 16;
    public static final int SESSION_VALID = 17;
    public static final int AREA_UNAVAILABLE = 18;
    public static final int DATE_UNAVAILABLE = 19;
    public static final int AUTH_CODE_INVALID = 20;
    public static final int AUTH_CODE_TIME = 21;
    public static final int DUPLICATION_FEEDBACK = 22;
    public static final int TOO_EARLY_TIME = 23;
    public static final int TOO_LATE_TIME = 24;

    //MainActivity FragmentState값
    public static final int FRAGMENT_ORDER = 0;
    public static final int FRAGMENT_PICKUP_TIME = 1;
    public static final int FRAGMENT_DROPOFF_TIME = 2;



}
