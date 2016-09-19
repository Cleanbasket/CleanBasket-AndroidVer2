package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

public class Review {
    public static int PICKUP_REVIEW = 1;
    public static int DROPOFF_REVIEW = 2;

    public int fid;
    public int oid;
    public int uid;
    public int kindness;
    public int rate;
    public String memo;

    public Review(int oid, int kindness, int rate, String memo) {
        this.oid = oid;
        this.kindness = kindness;
        this.rate = rate;
        this.memo = memo;
    }
}
