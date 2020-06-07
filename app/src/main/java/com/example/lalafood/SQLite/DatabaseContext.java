package com.example.lalafood.SQLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseContext extends SQLiteOpenHelper {
    //public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";
    public DatabaseContext(android.content.Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Truy vấn không kết quả trả về: Create, Update,...
    public void QueryData(String sql)
    {
        SQLiteDatabase testDataBase = getWritableDatabase(); //Ghi và đọc database
        testDataBase.execSQL(sql);
    }
    //Truy vấn có kết quả trả về: Select
    public Cursor GetData(String sql) //Cursor là con trỏ trong database
    {
        SQLiteDatabase testDatabase = getReadableDatabase(); //Chỉ đọc database
        return testDatabase.rawQuery(sql, null); //Lấy các toàn bộ giá trị tìm ra được
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}