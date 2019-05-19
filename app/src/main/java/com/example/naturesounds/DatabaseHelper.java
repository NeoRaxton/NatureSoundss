package com.example.naturesounds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.sql.Blob;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sounds.db";
    public static final String TABLE_NAME = "sounds_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("+ COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_2 + " TEXT "+")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP THE OLDER TABLE IF EXISTS
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        //CREATES THE TABLE AGAIN
        onCreate(db);
    }

    //ADDING NEW SOUNDS
    public boolean insertData(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,title);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return  false;
        else
            return true;
    }

    public Cursor getSoundData(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(sql,null);
    }

    public Cursor getASoundData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COL_1+ " =?",new String[]{String.valueOf(id)});
        return cursor;
    }

    /*public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }*/
}
