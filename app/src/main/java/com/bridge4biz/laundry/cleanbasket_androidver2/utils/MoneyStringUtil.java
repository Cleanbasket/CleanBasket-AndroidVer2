package com.bridge4biz.laundry.cleanbasket_androidver2.utils;

import java.text.DecimalFormat;

/**
 * Created by gingeraebi on 2016. 8. 29..
 */
public class MoneyStringUtil {
    public static String makeMoneyString(String money) {
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(money);
    }

    public static String makeMoneyString(int money) {
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(money);
    }
}
