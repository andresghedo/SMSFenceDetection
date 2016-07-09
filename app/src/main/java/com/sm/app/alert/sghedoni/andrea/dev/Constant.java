package com.sm.app.alert.sghedoni.andrea.dev;

/**
 * Created by andrea on 08/07/16.
 */
public class Constant {
    public static final String FENCE_ENTER_EVENT                                = "ENTER";
    public static final String FENCE_EXIT_EVENT                                 = "EXIT";
    public static final String FENCE_REMAINED_IN_EVENT                          = "REMAINED_IN";
    public static final String FENCE_REMAINED_OUT_EVENT                         = "REMAINED_OUT";
    public static final String FENCE_DISACTIVE                                  = "FENCE_DISACTIVE";

public static final int DATABASE_VERSION                                        = 1;
    public static final String DATABASE_NAME                                    = "AlertApp.db";

    public static final String NUMER_PHONE_TESTING                              = "3337572709";

    public static final String TOAST_TEXT_POLLING_SERVICE_START                 = "Polling Startegy is starting...";
    public static final String TOAST_TEXT_POLLING_SERVICE_STOP                  = "Polling Startegy is stopped!";
    public static final String TOAST_TEXT_BETTER_APP_SERVICE_START              = "Better Approach Startegy is starting...";
    public static final String TOAST_TEXT_BETTER_APP_SERVICE_STOP               = "Better Approach Strategy is stopped!";

    public static final String TITLE_VIEW_APP                                   = "Alert My Location";
    public static final String TITLE_VIEW_MAP_GEOFENCES                         = "Map Geofences";
    public static final String TITLE_VIEW_LIST_GEOFENCES                        = "List Geofences";
    public static final String TITLE_VIEW_START_SERVICE                         = "Start Service";
    public static final String TITLE_VIEW_CREDITS                               = "Credits";

    public static final long POLLING_UPDATE_REQUEST_MILLIS                      = 5*1000; // 5sec
    public static final long UPDATE_REQUEST_MILLIS_5_SEC                        = 5*1000; // 5sec
    public static final long UPDATE_REQUEST_MILLIS_30_SEC                       = 30*1000; // 30sec
    public static final long UPDATE_REQUEST_MILLIS_1_MIN                        = 60*1000; // 1min
    public static final long UPDATE_REQUEST_MILLIS_3_MIN                        = 3*60*1000; // 3min
    public static final long UPDATE_REQUEST_MILLIS_15_MIN                       = 5*60*1000; // 15min
    public static final long UPDATE_REQUEST_MILLIS_25_MIN                       = 8*60*1000; // 25min

}
