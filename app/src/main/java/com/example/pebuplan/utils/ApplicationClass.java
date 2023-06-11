package com.example.pebuplan.utils;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {

    // Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "c803c1d8-6b5e-4f37-a6a5-604399e347ab";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();
    }
}
