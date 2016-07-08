package com.sm.app.alert.sghedoni.andrea.dev.sqlitedb;

import android.provider.BaseColumns;

/**
 * Created by andrea on 02/07/16.
 */
public abstract class FenceEntrySQLiteDb implements BaseColumns {
    public static final String TABLE_NAME               = "fence";
    public static final String COLUMN_FENCE_NAME        = "name";
    public static final String COLUMN_FENCE_ADDRESS     = "address";
    public static final String COLUMN_FENCE_CITY        = "city";
    public static final String COLUMN_FENCE_PROVINCE    = "province";
    public static final String COLUMN_FENCE_LAT         = "lat";
    public static final String COLUMN_FENCE_LNG         = "lng";
    public static final String COLUMN_FENCE_RANGE       = "range";
    public static final String COLUMN_FENCE_ACTIVE      = "active";
    public static final String COLUMN_FENCE_MATCH       = "match";
}


