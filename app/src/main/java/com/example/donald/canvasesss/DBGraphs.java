package com.example.donald.canvasesss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Donald on 16.05.2017.
 */
public class DBGraphs extends SQLiteOpenHelper implements BaseColumns{
    public  static  final String NAME_COLLUMN_X_START = "x1";
    public  static  final String NAME_COLLUMN_X_END = "x2";
    public  static  final String NAME_COLLUMN_Y_START = "y1";
    public  static  final String NAME_COLLUMN_Y_END = "y2";
    public  static  final String NAME_COLLUMN_GRAPH_ID = "IDG";

    public static final String NAME_DB = "graph.db";
    public static final String NAME_TABLE = "graph";

    public  static  final int DB_VERSION = 1;

    private static final String DBScript = "create table "
            + NAME_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " +
            NAME_COLLUMN_X_START+ " integer, " +
            NAME_COLLUMN_X_END + " integer, " +
            NAME_COLLUMN_Y_START + " integer, " +
            NAME_COLLUMN_Y_END + " integer, " +
            NAME_COLLUMN_GRAPH_ID + " integer);";

    public DBGraphs(Context context) {
        super(context, NAME_DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
