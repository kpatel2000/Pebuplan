package com.example.pebuplan.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getBundleExtra("data");

        // Handle the received notification
        if (data != null) {
            String title = data.getString("title");
            String message = data.getString("message");

            // Do something with the notification data
            Log.d("NotificationReceiver", "Received notification: " + title + " - " + message);
        }
    }
}
