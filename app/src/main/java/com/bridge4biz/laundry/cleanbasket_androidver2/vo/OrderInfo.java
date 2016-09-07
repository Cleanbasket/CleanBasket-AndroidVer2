package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

import android.util.Log;

import com.bridge4biz.laundry.cleanbasket_androidver2.utils.TimeUtil;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderInfo {
    public Integer oid;
    public Integer uid;
    public String pickup_man;
    public String dropoff_man;
    public String order_number;
    public Integer state;
    public String phone;
    public String address;
    public String addr_number = "";
    public String addr_building = "";
    public String addr_remainder = "";
    public String note;
    public String memo;
    public Integer price;
    public Integer dropoff_price;
    public String pickup_date;
    public String dropoff_date;
    public String rdate;
    public Integer payment_method;
    @SerializedName("pickupInfo")
    public Deliverer pickupInfo;
    @SerializedName("dropoffInfo")
    public Deliverer dropoffInfo;
    public ArrayList<Item> item;
    public ArrayList<Coupon> coupon;

    public OrderInfo() {
    }


    public Integer getOid() {
        return oid;
    }

    public Integer getUid() {
        return uid;
    }

    public String getPickup_man() {
        return pickup_man;
    }

    public String getDropoff_man() {
        return dropoff_man;
    }

    public String getOrder_number() {
        return order_number;
    }

    public Integer getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAddr_number() {
        return addr_number;
    }

    public String getAddr_building() {
        return addr_building;
    }

    public String getAddr_remainder() {
        return addr_remainder;
    }

    public String getNote() {
        return note;
    }

    public String getMemo() {
        return memo;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDropoff_price() {
        return dropoff_price;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public String getDropoff_date() {
        return dropoff_date;
    }

    public String getPrettyPickUpDate() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        String today = sf.format(date);
        TimeUtil timeUtil = new TimeUtil();
        String prettyDate = timeUtil.dbFormatStringToPrettyFormatString(pickup_date);

        if (pickup_date.startsWith(today)) {
            return "오늘 " + prettyDate.split(" ")[1] + " " + prettyDate.split(" ")[2];
        }
        return prettyDate;
    }

    public String getPrettyDropOffDate() {

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        String today = sf.format(date);
        TimeUtil timeUtil = new TimeUtil();
        if (dropoff_date.startsWith(today)) {
            String prettyDate = timeUtil.dbFormatStringToPrettyFormatString(dropoff_date);

            return "오늘 " + prettyDate.split(" ")[1] + " " + prettyDate.split(" ")[2];
        }

        return timeUtil.dbFormatStringToPrettyFormatString(dropoff_date);
    }

    public String getRdate() {
        return rdate;
    }

    public Integer getPayment_method() {
        return payment_method;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public ArrayList<Coupon> getCoupon() {
        return coupon;
    }

    public Deliverer getPickupInfo() {
        return pickupInfo;
    }

    public Deliverer getDropoffInfo() {
        return dropoffInfo;
    }

    public String getPickupMan() {

        String name = "";

        if (this.pickupInfo == null) {
            return "크린파트너";
        } else {

            name = pickupInfo.name;

            if (name.isEmpty()) {
                return "크린파트너";
            } else {
                return name;
            }
        }

    }

    public String getDropoffMan() {

        String name = "";

        if (this.dropoffInfo == null) {
            return "크린파트너";
        } else {
            name = dropoffInfo.name;

            if (name.isEmpty()) {
                return "크린파트너";
            } else {
                return name;
            }
        }

    }

    public String getPriceStatus() {
        switch (this.payment_method) {
            case 0:
                return "현장 현금 결제";
            case 1:
                return "현장 카드 결제";
            case 2:
                return "계좌이체";
            case 3:
                return "인앱결제";
        }
        return "";
    }

    public String getStatus() {
        switch (this.state) {
            case 0:
                return "수거준비중";
            case 1:
                return "수거중";
            case 2:
                return "배달준비중";
            case 3:
                return "배달중";
            case 4:
                return "배달완료";
        }
        return "";
    }

    public String getFullAddress() {
        return this.address + this.addr_number + this.addr_building + this.addr_remainder;
    }

    //Item Not Found 추가
    public String makeItem() {

        List<Item> itmes = this.item;
        if (!itmes.isEmpty()) {
            String strItem = itmes.get(0).name + "(" + itmes.get(0).count + ")";
            for (int i = 1; i < this.item.size(); i++) {
                strItem += ", " + this.item.get(i).name + "(" + this.item.get(i).count + ")";
            }
            return strItem;
        }
        return "Item not found";
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", pickup_man='" + pickup_man + '\'' +
                ", dropoff_man='" + dropoff_man + '\'' +
                ", order_number='" + order_number + '\'' +
                ", state=" + state +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addr_number='" + addr_number + '\'' +
                ", addr_building='" + addr_building + '\'' +
                ", addr_remainder='" + addr_remainder + '\'' +
                ", note='" + note + '\'' +
                ", memo='" + memo + '\'' +
                ", price=" + price +
                ", dropoff_price=" + dropoff_price +
                ", pickup_date='" + pickup_date + '\'' +
                ", dropoff_date='" + dropoff_date + '\'' +
                ", rdate='" + rdate + '\'' +
                ", payment_method=" + payment_method +
                ", pickupInfo=" + pickupInfo +
                ", dropoffInfo=" + dropoffInfo +
                ", item=" + item +
                ", coupon=" + coupon +
                '}';
    }

    public void setPickupDateByDateAndTime(String date, int time) {
        Log.e("픽업 설정", "성공");

        TimeUtil timeUtil = new TimeUtil();
        String hour = String.format("%02d", time);
        dropoff_date = timeUtil.getThreeAfterStringFromTitleFormedString(date) + " " + hour + ":" + "00:00.0";
        date = timeUtil.titleFormedStringToDateFormedDateString(date) + " " + hour + ":" + "00:00.0";
        pickup_date = date;

    }

    public void setDropOffDateByDateAndTime(String date, int time) {
        Log.e("드랍 설정", "성공");
        TimeUtil timeUtil = new TimeUtil();
        String hour = String.format("%02d", time);
        date = timeUtil.titleFormedStringToDateFormedDateString(date) + " " + hour + ":" + "00:00.0";
        dropoff_date = date;
    }

}
