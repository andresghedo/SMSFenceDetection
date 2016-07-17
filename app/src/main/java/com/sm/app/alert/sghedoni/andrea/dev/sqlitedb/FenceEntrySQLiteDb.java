package com.sm.app.alert.sghedoni.andrea.dev.sqlitedb;

import android.provider.BaseColumns;

/**
 *  Class that provide TABLE and COLUMNS names
 *  @author Andrea Sghedoni
 */
public abstract class FenceEntrySQLiteDb implements BaseColumns {
    public static final String TABLE_NAME               = "fence";      // table name
    public static final String COLUMN_FENCE_NAME        = "name";       // name fence
    public static final String COLUMN_FENCE_ADDRESS     = "address";    // address
    public static final String COLUMN_FENCE_CITY        = "city";       // city
    public static final String COLUMN_FENCE_PROVINCE    = "province";   // province
    public static final String COLUMN_FENCE_LAT         = "lat";        // latitude
    public static final String COLUMN_FENCE_LNG         = "lng";        // longitude
    public static final String COLUMN_FENCE_RANGE       = "range";      // range of fence
    public static final String COLUMN_FENCE_ACTIVE      = "active";     // active/disabled
    public static final String COLUMN_FENCE_MATCH       = "match";      // is current location in Range?
    public static final String COLUMN_FENCE_NUMBER      = "number";     // number
    public static final String COLUMN_FENCE_SMS_TEXT    = "textsms";    // Content of SMS
    public static final String COLUMN_FENCE_EVENT       = "event";      // alert event: ENTER/EXIT/BOTH
}


