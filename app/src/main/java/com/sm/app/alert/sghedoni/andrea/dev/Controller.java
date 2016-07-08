package com.sm.app.alert.sghedoni.andrea.dev;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.sm.app.alert.sghedoni.andrea.dev.activity.MainActivity;
import com.sm.app.alert.sghedoni.andrea.dev.sqlitedb.SQLiteDBManager;

import java.util.ArrayList;

/**
 *  Classe Controller, implementa il pattern SINGLETON. .
 *  @author andreasd
 */

public class Controller {
    /** istanza singleton */
    private static Controller instance;
    /** istanza singleton */
    private static String TAG = "[DebApp]Controller";

    private static SQLiteDBManager dbManager = null;

    public static ArrayList<Fence> fences = null;

    /** cerca l'istanza singleton, se la trova la torna altrimenti la crea */
    public static Controller getInstance() {
        if(instance == null) {
            Log.d(TAG, "New Istance of Controller is created!!");
            instance = new Controller();
        }
        return instance;
    }

    public static void sendSMS(String number, String msg, Context context) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, msg, null, null);
            Log.d(TAG, "Send SMS TO NUMER_PHONE_TESTING:  " + Constant.NUMER_PHONE_TESTING);
            Toast.makeText(context, "SMS INVIATO A  " + Constant.NUMER_PHONE_TESTING, Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context ctx) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d(TAG, "PROCESS " + serviceClass.getName() + " IS ACTIVE!!!");
                return true;
            }
        }
        return false;
    }

    public static String getStatusBetweenFenceAndCurrentLocation(Fence fence, Location currentLocation) {
        if (fence.isInRange(currentLocation)) {// I'M IN RANGE
            if (fence.isMatch() == false) { // IF BEFORE NOT MATCHED...
                Log.d(TAG, "ENTERED WHIT FENCE: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_ENTER_EVENT;
            } else {
                Log.d(TAG, "REMAINED IN: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_REMAINED_IN_EVENT;
            }
        } else {
            if (fence.isMatch() == true) { // I'M NOT IN RANGE AND BEFORE MATCH EXITED
                Log.d(TAG, "EXITED WHIT FENCE: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_EXIT_EVENT;
            } else {
                Log.d(TAG, "REMAINED OUT: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_REMAINED_OUT_EVENT;
            }
        }
    }

    /***********************************************************************************************
    *                                                                                              *
    *                           Method for SQLiteDB Management                                     *
    *                                                                                              *
    ***********************************************************************************************/
    public static void setDbManager(Context ctx) {
        dbManager = new SQLiteDBManager(ctx);
        Log.d(TAG, "New Istance of SQLiteDBManager!!");
    }

    public static void resumeFencesFromDb() {
        fences = dbManager.resumeFencesFromDb();
    }

    public static void removeFenceFromDB(int id) { dbManager.delete(id); }

    public static void getLogFenceOnSQLiteDB() { dbManager.getLogOfFenceTableSQLiteDB(); }

    public static int insertFenceOnSQLiteDB(String name, String address, String city, String province, String lat, String lng, String range, int active) {
        return dbManager.insert(name, address, city, province, lat, lng, range, active);
    }

    public static void updateFenceStatusOnSQLiteDB(int id, boolean flag) {
        int value =  flag ? 1 : 0;
        dbManager.updateFenceStatus(id, value);
    }

    public static void updateFenceMatchOnSQLiteDB(int id, boolean match) {
        int value =  match ? 1 : 0;
        dbManager.updateMatchFence(id, value);
    }

    public static void setAllFencesMatchedFalse() {
        dbManager.setAllFencesMatchedFalse();
    }

    public static void getLogFenceEntities() {
        Log.d(TAG, "Log of Fence Entities");
        for (int i=0; i<fences.size(); i++)
            Log.d(TAG, "FenceEntity name: " + fences.get(i).getName());
    }

    /*
    *       Notification
    * */

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    public static void sendNotification(String notificationDetails, Context ctx) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(ctx, MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_map_fence)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),
                        R.drawable.ic_map_fence))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("Clicke here to open AlertApp!!")
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }
}