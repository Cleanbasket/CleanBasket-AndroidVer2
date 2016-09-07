package com.bridge4biz.laundry.cleanbasket_androidver2.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gingeraebi on 2016. 9. 7..
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //TODO Notification으로 띄우기 
        super.onMessageReceived(remoteMessage);
    }
}
