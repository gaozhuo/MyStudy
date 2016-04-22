package com.example.appkeepalive;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final int NOTIFICATION_ID = 25;
    //Timer mTimer = new Timer();
    private int mCount;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("gaozhuo", "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("gaozhuo", "onStartCommand flags=" + flags);
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.d("gaozhuo", "count=" + mCount++);
//            }
//        }, 5000, 2000);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, SecondActivity.class), 0);
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        builder.setTicker("ticker");
//        builder.setContentIntent(pendingIntent);
//        builder.setContentTitle("i am title");
//        builder.setContentText("hello world");
//        Notification notification = builder.getNotification();

        if(Build.VERSION.SDK_INT < 18){
            startForeground(NOTIFICATION_ID, new Notification());
        }else {
            Intent innerIntent = new Intent(this, InnerService.class);
            startService(innerIntent);
            startForeground(NOTIFICATION_ID, new Notification());
        }



        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("gaozhuo", "onDestroy");
        //mTimer.cancel();
    }

    public static class InnerService extends Service{

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(NOTIFICATION_ID, new Notification());
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
