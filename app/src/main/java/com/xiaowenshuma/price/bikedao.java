package com.xiaowenshuma.price;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 17/8/10.
 */

public class bikedao implements bikeinterface<bike>{
    private Context context;
    private String table = "bikes";

    public bikedao(Context context){
       setContext(context);
    }

    private void setContext(Context context) {
        this.context=context;
    }

    @Override
    public long insert(bike bike) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String nullColumnHack = "_id";
        ContentValues values = new ContentValues();
        values.put("_number", bike.getNumber());
        values.put("_password", bike.getPassword());
        long id = db.insert(table, nullColumnHack, values);
        db.close();
        db=null;
        return id;
    }

    @Override
    public List<bike> query(String whereClause, String[] whereArgs) {
        List<bike> bikes = new ArrayList<bike>();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String[] columns = { "_number", "_password" };
        Cursor c = db.query(table, columns, whereClause, whereArgs, null, null,
                null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            bike p = new bike();

            p.setNumber(c.getString(0));
            p.setPassword(c.getString(1));

            bikes.add(p);
        }
        // 释放资源
        db.close();
        db = null;
        // 返回

        return bikes;


    }

    @Override
    public int query() {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor c = db.query(table, null, null, null, null, null,
                null);
        int cursorcount=c.getCount();
        return cursorcount;
    }
}
