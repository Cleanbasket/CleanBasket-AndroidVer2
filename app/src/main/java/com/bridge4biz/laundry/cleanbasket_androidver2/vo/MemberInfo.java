package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gingeraebi on 2016. 8. 12..
 */
public class MemberInfo {
    public String auid;
    public String uid;
    public String code;
    public String email;
    public String phone;
    public int mileage;
    public int total;
    @SerializedName("user_class")
    public int userClass;
    public String agent;


}
