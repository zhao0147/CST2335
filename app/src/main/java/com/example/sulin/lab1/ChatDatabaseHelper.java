package com.example.sulin.lab1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sulin on 2016-10-12.
 */
public class ChatDatabaseHelper extends SQLiteOpenHelper {
    protected static String DATABASE_NAME = "MyDatabase";
    public static String TABLE_NAME = "MyTable";
    protected static int VERSION_NUM = 1;
    public final static String KEY_ID = "ID";          //table column name ID
    public final static String KEY_MESSAGE = "MESSAGE";    //table column name MESSAGE

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " int AUTO_INCREMENT, " + KEY_MESSAGE + " String" + ");");

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }
}
