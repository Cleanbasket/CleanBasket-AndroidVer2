package com.bridge4biz.laundry.cleanbasket_androidver2.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by gingeraebi on 2016. 8. 19..
 */
public class GsonUtil<T> {
    private Gson gson;

    public GsonUtil() {
        gson = new Gson();
    }

    public ArrayList<T> convertJsonToList(String jsonData) {
        ArrayList<T> resultList  = gson.fromJson(jsonData, new TypeToken<ArrayList<T>>() {
        }.getType());
        return resultList;
    }
}
