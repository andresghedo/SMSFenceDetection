package com.sm.app.wifi.sghedoni.andrea.wifism.com.sm.app.wifi.sghedoni.andrea.db.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by andrea on 02/07/16.
 */
public class SQLiteDBManager {

    private GeofenceSQLiteHelper geofenceDbHelper;
    private SQLiteDatabase db;
    private String TAG="[DebApp]SQLiteDBManager";

    public SQLiteDBManager(Context ctx) {
        Log.d(TAG, "Opening new connection db...");
        geofenceDbHelper = new GeofenceSQLiteHelper(ctx);
        db = geofenceDbHelper.getWritableDatabase();
    }

    public void insert(String name, String lat, String lng, String range) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_NAME, name);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_LAT, lat);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_LNG, lng);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_RANGE, range);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FenceEntrySQLiteDb.TABLE_NAME,
                null,
                values);
        Log.d(TAG, "Insert new row in " + FenceEntrySQLiteDb.TABLE_NAME + " , id: " + newRowId);
    }

    public void selectAll() {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FenceEntrySQLiteDb._ID,
                FenceEntrySQLiteDb.COLUMN_FENCE_NAME,
                FenceEntrySQLiteDb.COLUMN_FENCE_LAT,
                FenceEntrySQLiteDb.COLUMN_FENCE_LNG,
                FenceEntrySQLiteDb.COLUMN_FENCE_RANGE
        };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder = FeedEntry.COLUMN_NAME_UPDATED + " DESC";
        /*
        Cursor c = db.query(
                FenceEntrySQLiteDb.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        */

        Cursor c = db.rawQuery("SELECT * FROM " + FenceEntrySQLiteDb.TABLE_NAME + " WHERE 1;", null);
        Log.d(TAG, "In Database: ");

        if(c.moveToFirst()) {
            do{
                //assing values
                String id = c.getString(0);
                String name = c.getString(1);
                String lat = c.getString(2);
                String lng = c.getString(3);
                String range = c.getString(4);

                //Do something Here with values
                Log.d(TAG, "id: " + id + "name: " + name + "lat: " + lat + "lng: " + lng + "range: " + range);
            }while(c.moveToNext());
        }

        c.close();
        //db.close();
    }

}
