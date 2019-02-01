package com.test.project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasehelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Calendar.db";
    public static final String TABLE_NAME="calendar_table";
    public static final String COL_1="date";
    public static final String COL_2="temperature";
    public static final String COL_3="bphigh";
    public static final String COL_4="bplow";
    public static final String COL_5="sysm";
    public static final String COL_6="other";


    public databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME + " (date text PRIMARY KEY,temperature text,bphigh text,bplow text,sysm text,other text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertdata(String date,String temperatre , String bphigh,String bplow,String sysm,String other){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,date);
        contentValues.put(COL_2,temperatre);
        contentValues.put(COL_3,bphigh);
        contentValues.put(COL_4,bplow);
        contentValues.put(COL_5,sysm);
        contentValues.put(COL_6,other);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }
    public boolean updatedata(String date,String temperatre , String bphigh,String bplow,String sysm,String other){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,date);
        contentValues.put(COL_2,temperatre);
        contentValues.put(COL_3,bphigh);
        contentValues.put(COL_4,bplow);
        contentValues.put(COL_5,sysm);
        contentValues.put(COL_6,other);
        long result = db.update(TABLE_NAME,contentValues,"date = ?",new String [] {date});

        if (result == -1)
            return false;
        else
            return true;

    }
    public Cursor getalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    public Cursor checkdate(String dateee){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql=String.format("select * from calendar_table where date='%s'",dateee);
        Cursor res = db.rawQuery(sql,null);
        return res;

    }
}
