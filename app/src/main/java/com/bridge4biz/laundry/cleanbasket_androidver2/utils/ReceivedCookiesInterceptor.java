package com.bridge4biz.laundry.cleanbasket_androidver2.utils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by gingeraebi on 2016. 7. 7..
 */
public class ReceivedCookiesInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferenceBase.putSharedPreference("Cookies",cookies);
        }

        return originalResponse;
    }
}