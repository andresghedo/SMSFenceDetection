package com.sm.app.wifi.sghedoni.andrea.wifism;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

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

    /** cerca l'istanza singleton, se la trova la torna altrimenti la crea */
    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();
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

}