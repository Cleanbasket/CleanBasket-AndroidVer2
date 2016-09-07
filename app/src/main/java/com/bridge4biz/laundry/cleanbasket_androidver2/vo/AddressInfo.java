package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

/**
 * Created by gingeraebi on 2016. 7. 18..
 */
public class AddressInfo {
    private int adrid;
    private int uid;
    private int type;
    private String address;
    private String addr_number;
    private String addr_remainder = "";
    private String addr_building = "";
    private String rdate;

    public AddressInfo() {
    }

    public AddressInfo(String address, String addr_remainder) {
        this.address = address;
        this.addr_remainder = addr_remainder;
    }

    public AddressInfo(int adrid) {
        this.adrid = adrid;
    }

    public int getAdrid() {
        return adrid;
    }

    public void setAdrid(int adrid) {
        this.adrid = adrid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddr_number() {
        return addr_number;
    }

    public void setAddr_number(String addr_number) {
        this.addr_number = addr_number;
    }

    public String getAddr_remainder() {
        return addr_remainder;
    }

    public void setAddr_remainder(String addr_remainder) {
        this.addr_remainder = addr_remainder;
    }

    public String getAddr_building() {
        return addr_building;
    }

    public void setAddr_building(String addr_building) {
        this.addr_building = addr_building;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getPrettyAddress() {
        return this.address + this.addr_number + this.addr_building + this.addr_remainder;
    }

    @Override
    public String toString() {
        return this.address + this.addr_number + this.addr_building + this.addr_remainder;
    }
}
