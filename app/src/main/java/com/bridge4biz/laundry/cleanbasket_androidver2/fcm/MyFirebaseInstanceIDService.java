package com.bridge4biz.laundry.cleanbasket_androidver2.fcm;

import com.bridge4biz.laundry.cleanbasket_androidver2.network.FirebaseManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by gingeraebi on 2016. 9. 7..
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        sendRefreshTokenToServer(refreshToken );
    }

    private void sendRefreshTokenToServer(String refreshToken) {
        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.sendRefreshTokenToServer(refreshToken);
    }
}
