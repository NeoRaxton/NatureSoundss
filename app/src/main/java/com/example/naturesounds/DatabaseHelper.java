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
    public static final String COL_3 = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_1 + "INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_2 + " TEXT,"+ COL_3 + " BLOB" +")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP THE OLDER TABLE IF EXISTS
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        //CREATES THE TABLE AGAIN
        onCreate(db);
    }

    //ADDING NEW SOUNDS
    public void insertData(String title,String name,byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO SOUNDS VALUES (NULL,?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,title);
        statement.bindString(2,name);
        statement.bindBlob(3,image);

        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(sql,null);
    }


    /*public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }*/

}
