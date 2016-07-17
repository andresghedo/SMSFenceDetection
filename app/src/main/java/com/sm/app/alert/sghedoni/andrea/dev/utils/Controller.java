package com.sm.app.alert.sghedoni.andrea.dev.utils;

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

import com.sm.app.alert.sghedoni.andrea.dev.R;
import com.sm.app.alert.sghedoni.andrea.dev.activity.MainActivity;
import com.sm.app.alert.sghedoni.andrea.dev.entity.Fence;
import com.sm.app.alert.sghedoni.andrea.dev.sqlitedb.SQLiteDBManager;

import java.util.ArrayList;

/**
 *  Class that implement SINGLETON Pattern.
 *  Controller provide all utils objects/functions at any point of the project.
 *  @author Andrea Sghedoni
 */
public class Controller {
    /* singleton istance */
    private static Controller instance;
    /* TAG for logger */
    private static String TAG = "[DebApp]Controller";
    /* SQLite db manager */
    private static SQLiteDBManager dbManager = null;
    /* All user fences */
    public static ArrayList<Fence> fences = null;

    /* get singleton istance */
    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /* Method for SMS sending */
    public static void sendSMS(String number, String msg, Context context) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, msg, null, null);
            Toast.makeText(context, "SEND SMS ALERT TO  " + number, Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(context, "SMS Fail, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /* Method that checks whether a particular process is active */
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

    /* Method that return the situation between a Fence and the current Location */
    public static String getStatusBetweenFenceAndCurrentLocation(Fence fence, Location currentLocation) {

        // IF Fence is not active
        if (!fence.isActive())
            return Constant.FENCE_DISACTIVE;

        // IF Current Location is in Fence range
        if (fence.isInRange(currentLocation)) {
            // In previous detection was NOT into Fence --> ENTER EVENT
            if (fence.isMatch() == false) {
                Log.d(TAG, "ENTERED WHIT FENCE: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_ENTER_EVENT;
            } else {// In previous detection was into Fence
                Log.d(TAG, "REMAINED IN: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_REMAINED_IN_EVENT;
            }
        } else {// Current Location is NOT in Fence range
            // In previous detection was into Fence --> EXIT EVENT
            if (fence.isMatch() == true) {
                Log.d(TAG, "EXITED WHIT FENCE: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_EXIT_EVENT;
            } else {// In previous detection was  NOT into Fence
                Log.d(TAG, "REMAINED OUT: " + fence.getName() + " , DISTANCE: " + fence.getLocation().distanceTo(currentLocation) + " , RANGE(m): " + fence.getRange() + " , STATUS: " + fence.isMatch());
                return Constant.FENCE_REMAINED_OUT_EVENT;
            }
        }
    }

    /* Return a fence position in ArrayList Controller.fences */
    public static int getPositionFenceInArrayById(int id) {
        for (int i=0; i<fences.size(); i++)
            if(fences.get(i).getId() == id)
                return i;
        return -1;
    }

    /* Method that detect a Fence Event and send an Alert SMS if a selected event is happened */
    public static void processFenceEvent(Fence fence, Location currentLocation, Context ctx) {
        // get situation between fence and current location
        switch (getStatusBetweenFenceAndCurrentLocation(fence, currentLocation)) {
            // if event ENTER
            case Constant.FENCE_ENTER_EVENT:
                updateFenceMatchOnSQLiteDB(fence.getId(), true);
                fence.setMatch(true);
                if ((fence.getEvent() != Constant.SPINNER_EVENT_ENTER) && (fence.getEvent() != Constant.SPINNER_EVENT_ENTER_AND_EXIT))
                    break;
                sendSMS(fence.getNumber(), fence.getTextSMS(), ctx);
                sendNotification("Entered in: " + fence.getName(), ctx);
                break;
            // elif event EXIT
            case Constant.FENCE_EXIT_EVENT:
                updateFenceMatchOnSQLiteDB(fence.getId(), false);
                fence.setMatch(false);
                if ((fence.getEvent() != Constant.SPINNER_EVENT_EXIT) && (fence.getEvent() != Constant.SPINNER_EVENT_ENTER_AND_EXIT))
                    break;
                sendSMS(fence.getNumber(), fence.getTextSMS(), ctx);
                sendNotification("Exited from: " + fence.getName(), ctx);
                break;
            case Constant.FENCE_REMAINED_IN_EVENT:
                break;
            case Constant.FENCE_REMAINED_OUT_EVENT:
                break;
            case Constant.FENCE_DISACTIVE:
                break;
        }
    }

    /* Method that posts a notification in the notification bar when a transition is detected.*/
    public static void sendNotification(String notificationDetails, Context ctx) {

        Intent notificationIntent = new Intent(ctx, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);

        builder.setSmallIcon(R.drawable.ic_map_fence)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),
                        R.drawable.ic_map_fence))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText(Constant.NOTIFICATION_TEXT)
                .setContentIntent(notificationPendingIntent);

        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, builder.build());
    }

    /* Simple Log of all fence in fences */
    public static void getLogFenceEntities() {
        Log.d(TAG, "Log of Fence Entities");
        for (int i=0; i<fences.size(); i++)
            Log.d(TAG, "FenceEntity name: " + fences.get(i).getName());
    }

    /***********************************************************************************************
    *                           Methods for SQLiteDB Management                                    *
    ***********************************************************************************************/

    /* Set DB Manager*/
    public static void setDbManager(Context ctx) {
        dbManager = new SQLiteDBManager(ctx);
    }

    /* Set fences from SQLiteDB */
    public static void resumeFencesFromDb() {
        fences = dbManager.resumeFencesFromDb();
    }

    /* Remove a Fence from SQLiteDB */
    public static void removeFenceFromDB(int id) { dbManager.delete(id); }

    /* Log of all DB/Fence entities */
    public static void getLogFenceOnSQLiteDB() { dbManager.getLogOfFenceTableSQLiteDB(); }

    /* Insert of a Fence Entity in SQLiteDB */
    public static int insertFenceOnSQLiteDB(String name, String address, String city, String province, String lat, String lng, String range, int active, String number, String textSMS, int event) {
        return dbManager.insert(name, address, city, province, lat, lng, range, active, number, textSMS, event);
    }

    /* Active/Disactive a Fence in SQLiteDB */
    public static void updateFenceStatusOnSQLiteDB(int id, boolean flag) {
        int value =  flag ? 1 : 0;
        dbManager.updateFenceStatus(id, value);
    }

    /* Match/No Match Fence in SQLiteDB */
    public static void updateFenceMatchOnSQLiteDB(int id, boolean match) {
        int value =  match ? 1 : 0;
        dbManager.updateMatchFence(id, value);
    }

    /* Update all data Fence in SQLiteDB */
    public static void updateAllAttributeOnSQLiteDB(int id, String name, String address, String city, String province, String lat, String lng, String range, String number, String textSMS, int event) {
        dbManager.updateAll(id, name, address, city, province, lat, lng, range, number, textSMS, event);
    }

    /* Set all match FEnce attribute FALSE in SQLiteDB */
    public static void setAllFencesMatchedFalse() {
        dbManager.setAllFencesMatchedFalse();
    }

}