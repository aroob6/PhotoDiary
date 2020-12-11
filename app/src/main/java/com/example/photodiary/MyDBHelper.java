package com.example.photodiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public MyDBHelper(@Nullable Context context) {
        super(context, "mydb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String testSQL = "create table myPhotoDiary(" +
                "_id integer primary key autoincrement," +
                "content text," +
                "img BLOB," +
                "weather BLOB," +
                "date datetime default CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(testSQL);
        /*testSQL = "insert into accountBook(title,rating,visited)values('더프라이팬',4,'2020-11-18')";
        sqLiteDatabase.execSQL(testSQL);
        testSQL = "insert into accountBook(title,rating,visited)values('긴자료코',5,'2020-10-10')";
        sqLiteDatabase.execSQL(testSQL);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1 == DATABASE_VERSION){
            sqLiteDatabase.execSQL("drop table myPhotoDiary");
            onCreate(sqLiteDatabase);
        }

    }
}
