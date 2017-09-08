package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import sqlite.Mysqlite;

/**
 * Created by DELL on 2017/9/6.
 */

public class TitleDao {


    private final Mysqlite helper;
    private String json;

    public TitleDao(Context context) {
        helper = new Mysqlite(context);

    }

    public void add(String json){
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql="insert into title(json) values(?)";
        db.execSQL(sql, new Object[]{json});
        db.close();

    }
    public String select(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="select * from title";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext())
        {
            json = cursor.getString(cursor.getColumnIndex("json"));
        }

        return json;
    }
}
