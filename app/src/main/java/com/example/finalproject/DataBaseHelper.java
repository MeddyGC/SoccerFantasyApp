package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Player.db";
    public static final String TABLE_NAME="Players";

//COLS

    public static final String COLS_1="ID";
    public static final String COLS_2="PlayerName";
    public static final String COLS_3="PlayerNumber";
    public static final String COLS_4="Position";
    public static final String COLS_5="Age";
    public static final String COLS_6 ="Height";
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, PlayerName TEXT, PlayerNumber TEXT, Position TEXT, Age TEXT,Height TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+"");
        onCreate(db);

    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getDataByName(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Players ORDER BY PlayerName;";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getDataByAge(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Players ORDER BY Age;";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getDataByHeight(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Players ORDER BY Height;";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Boolean deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.COLS_2 + "=?", new String[]{name})>0;
    }
}
