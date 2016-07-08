package com.sm.app.alert.sghedoni.andrea.dev.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sm.app.alert.sghedoni.andrea.dev.Fence;

import java.util.ArrayList;

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

    public int insert(String name, String address, String city, String province, String lat, String lng, String range, int active) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_NAME, name);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_ADDRESS, address);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_CITY, city);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_PROVINCE, province);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_LAT, lat);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_LNG, lng);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_RANGE, range);
        values.put(FenceEntrySQLiteDb.COLUMN_FENCE_ACTIVE, active);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FenceEntrySQLiteDb.TABLE_NAME,
                null,
                values);
        Log.d(TAG, "Insert new row in " + FenceEntrySQLiteDb.TABLE_NAME + " , id: " + newRowId);
        return (int)newRowId;
    }

    public void getLogOfFenceTableSQLiteDB() {

        Cursor c = db.rawQuery("SELECT * FROM " + FenceEntrySQLiteDb.TABLE_NAME + " WHERE 1;", null);
        Log.d(TAG, "In Database: ");

        if(c.moveToFirst()) {
            do{
                //assing values
                String id = c.getString(0);
                String name = c.getString(1);
                String address = c.getString(2);
                String city = c.getString(3);
                String province = c.getString(4);
                String lat = c.getString(5);
                String lng = c.getString(6);
                String range = c.getString(7);
                String active = c.getString(8);

                //Do something Here with values
                Log.d(TAG, "ID: " + id + "  NAME: " + name + "  ADDRESS: " + address + "  CITY: " + city + "  PROV: " + province + "  LAT: " + lat + "  LNG:" + lng + "  RANGE:" + range + " ACTIVE:" + active);
            }while(c.moveToNext());
        }

        c.close();
        //db.close();
    }

    public ArrayList<Fence> resumeFencesFromDb() {

        ArrayList<Fence> toReturn = new ArrayList<Fence>();
        Cursor c = db.rawQuery("SELECT * FROM " + FenceEntrySQLiteDb.TABLE_NAME + " WHERE 1;", null);
        Log.d(TAG, "Resuming of all fences...");

        if(c.moveToFirst()) {
            do{
                //assing values
                int id = c.getInt(0);
                String name = c.getString(1);
                String address = c.getString(2);
                String city = c.getString(3);
                String province = c.getString(4);
                Double lat = Double.parseDouble(c.getString(5));
                Double lng = Double.parseDouble(c.getString(6));
                Float range = Float.parseFloat(c.getString(7));
                boolean active = c.getInt(8) == 1;
                boolean match = c.getInt(9) == 1;

                Fence f = new Fence(id, name, address, city, province, lat, lng, range, active, match);
                toReturn.add(f);
            }while(c.moveToNext());
        }

        c.close();
        return toReturn;
    }

    public void delete(int id) {
        // Define 'where' part of query.
        String selection = FenceEntrySQLiteDb._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        db.delete(FenceEntrySQLiteDb.TABLE_NAME, selection, selectionArgs);
    }

    public void updateFenceStatus(int id, int flag) {
        String strFilter = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put(FenceEntrySQLiteDb.COLUMN_FENCE_ACTIVE, flag);
        db.update(FenceEntrySQLiteDb.TABLE_NAME, args, strFilter, null);
    }

    public void setAllFencesMatchedFalse() {
        String filterAll = "1";
        int falseMatch = 0;
        ContentValues args = new ContentValues();
        args.put(FenceEntrySQLiteDb.COLUMN_FENCE_MATCH, falseMatch);
        db.update(FenceEntrySQLiteDb.TABLE_NAME, args, filterAll, null);
    }

    public void updateMatchFence(int id, int match) {
        String strFilter = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put(FenceEntrySQLiteDb.COLUMN_FENCE_MATCH, match);
        db.update(FenceEntrySQLiteDb.TABLE_NAME, args, strFilter, null);
    }

}
