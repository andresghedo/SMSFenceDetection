package com.sm.app.alert.sghedoni.andrea.dev;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

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
    /** testing number */
    public static String NUMER_PHONE_TESTING= "3337572709";

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
            Log.d(TAG, "Send SMS TO NUMER_PHONE_TESTING:  " + Controller.NUMER_PHONE_TESTING);
            Toast.makeText(context, "SMS INVIATO A  " + Controller.NUMER_PHONE_TESTING, Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /***********************************************************************************************
    *                                                                                              *
    *                           Method for SQLiteDB Managment                                      *
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

    public static int insertFenceOnSQLiteDB(String name, String lat, String lng, String range) {
        return dbManager.insert(name, lat, lng, range);
    }

    public static void getLogFenceEntities() {
        Log.d(TAG, "Log of Fence Entities");
        for (int i=0; i<fences.size(); i++)
            Log.d(TAG, "FenceEntity name: " + fences.get(i).getName());
    }

}