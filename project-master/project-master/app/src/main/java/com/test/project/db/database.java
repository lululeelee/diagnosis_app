package com.test.project.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class database {
    private SQLiteDatabase data;

    public void setData(SQLiteDatabase data){
        this.data = data;
    }

    public List<disease> query(String cmd){
        List<disease> DList = new ArrayList<>();
        Cursor cur = data.rawQuery(cmd,null);

        if (cur.moveToFirst()){
            do{
                disease d = new disease();
                d.setId(Integer.parseInt(cur.getString(0)));
                d.setName(cur.getString(1));

                DList.add(d);
            }while(cur.moveToNext());
        }

        cur.close();

        return DList;
    }

    public List<String> symptom(String table){
        List<String> list = new ArrayList<>();
        Cursor cur = data.rawQuery("SELECT * FROM " + table,null);

        list.add("");

        if (cur.moveToFirst()){
            do{
                String s;
                s = cur.getString(0);

                list.add(s);
            }while(cur.moveToNext());
        }

        cur.close();

        return list;
    }
}
