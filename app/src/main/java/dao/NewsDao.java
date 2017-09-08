package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bean.NetNews;
import sqlite.MySqliteOpenHelper;

/**
 * Created by DELL on 2017/9/5.
 */

public class NewsDao {

    private   MySqliteOpenHelper helper;
    private String result;
    private NetNews netNews;

    public NewsDao(Context context) {
        helper = new MySqliteOpenHelper(context);
    }


    public void add(String type, String result){
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql="insert into news(type,result) values(?,?)";
        db.execSQL(sql, new Object[]{type,result});
        db.close();
    }

    public NetNews select(String type){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="select * from news where type=?";
        Cursor cursor = db.rawQuery(sql, new String[]{type});
        if(cursor.moveToNext())
        {
            result = cursor.getString(cursor.getColumnIndex("result"));
            String type1 = cursor.getString(cursor.getColumnIndex("type"));

            netNews = new NetNews(result,type1);
        }

        return netNews;

    }
}
