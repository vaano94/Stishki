package com.stishki.iva.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ivan on 12/1/2015.
 */
public class DatabaseManager {
    Context context;
    private SQLiteDatabase db;
    private String DB_NAME = "poems.db";
    private final int DB_VERSION = 1;

    private final String TABLE_NAME = "UserPoems";
    private final String TABLE_NEWPOEMS = "NewPoems";
    private final String TABLE_POPULARPOEMS = "PopularPoems";
    private final String TABLE_SUBSCRIBEPOEMS = "SubscribePoems";


    private static final String SQL_CREATE_USERPOEMENTRIES = "CREATE TABLE " +
            PoemContract.UserPoemEntry.TABLE_NAME +
            "(" +
            PoemContract.UserPoemEntry.COLUMN_ID + " integer primary key autoincrement not null," +
            PoemContract.UserPoemEntry.COLUMN_GENRE + " text," +
            PoemContract.UserPoemEntry.COLUMN_CONTENT + " text," +
            PoemContract.UserPoemEntry.COLUMN_USER + " text," +
            PoemContract.UserPoemEntry.COLUMN_LIKES + " integer," +
            PoemContract.UserPoemEntry.COLUMN_DISLIKES + " integer" +
            ");";

    private static final String SQL_CREATE_POPULARPOEMENTRIES = "CREATE TABLE " +
            PoemContract.PopularPoemEntry.TABLE_NAME +
            "(" +
            PoemContract.PopularPoemEntry.COLUMN_ID + " integer primary key autoincrement not null," +
            PoemContract.PopularPoemEntry.COLUMN_GENRE + " text," +
            PoemContract.PopularPoemEntry.COLUMN_CONTENT + " text," +
            PoemContract.PopularPoemEntry.COLUMN_USER + " text," +
            PoemContract.PopularPoemEntry.COLUMN_LIKES + " integer," +
            PoemContract.PopularPoemEntry.COLUMN_DISLIKES + " integer" +
            ");";

    private static final String SQL_CREATE_NEWPOEMENTRIES = "CREATE TABLE " +
            PoemContract.NewPoemEntry.TABLE_NAME +
            "(" +
            PoemContract.NewPoemEntry.COLUMN_ID + " integer primary key autoincrement not null," +
            PoemContract.NewPoemEntry.COLUMN_GENRE + " text," +
            PoemContract.NewPoemEntry.COLUMN_CONTENT + " text," +
            PoemContract.NewPoemEntry.COLUMN_USER + " text," +
            PoemContract.NewPoemEntry.COLUMN_LIKES + " integer," +
            PoemContract.NewPoemEntry.COLUMN_DISLIKES + " integer" +
            ");";

    public DatabaseManager(Context context) {
        this.context = context;
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public void insert(String tableName, String genre, String content, String user, int likes, int dislikes) {
        ContentValues values = new ContentValues();
        values.put(PoemContract.UserPoemEntry.COLUMN_GENRE, genre);
        values.put(PoemContract.UserPoemEntry.COLUMN_CONTENT, content);
        values.put(PoemContract.UserPoemEntry.COLUMN_USER, user);
        values.put(PoemContract.UserPoemEntry.COLUMN_LIKES, likes);
        values.put(PoemContract.UserPoemEntry.COLUMN_LIKES, dislikes);
        try {
            db.insert(tableName, null, values);
        }
        catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void delete(String tableName, long id) {
        try {
            db.delete(tableName, PoemContract.UserPoemEntry.COLUMN_ID + "=" + id, null);
        }
        catch (Exception e) {
            Log.e("COULD NOT DELETE", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<Object> getRowAsArray(String tableName, long rowId) {
        ArrayList<Object> objects = new ArrayList<>();
        Cursor cursor;
        try {
            cursor = db.query
                    (
                      tableName,
                      new String[] {
                              PoemContract.UserPoemEntry.COLUMN_ID, PoemContract.UserPoemEntry.COLUMN_GENRE, PoemContract.UserPoemEntry.COLUMN_CONTENT,
                              PoemContract.UserPoemEntry.COLUMN_USER, PoemContract.UserPoemEntry.COLUMN_LIKES, PoemContract.UserPoemEntry.COLUMN_DISLIKES
                      },
                      null,
                      null,
                      null,
                      null,
                      null
                    );
        }
        catch (Exception e) {
            Log.e("COULD NOT QUERY ROW!!", e.toString());
            e.printStackTrace();
        }
        return objects;
    }

    public ArrayList<ArrayList<Object>> getAllRowsAsArrays(String tableName) {
        // create an ArrayList that will hold all of the data collected from
        // the database.
        ArrayList<ArrayList<Object>> dataArrays =
                new ArrayList<ArrayList<Object>>();
        // this is a database call that creates a "cursor" object.
        // the cursor object store the information collected from the
        // database and is used to iterate through the data.
        Cursor cursor;
        try {
            // ask the database object to create the cursor.
            cursor = db.query
                    (
                            tableName,
                            new String[]{
                                    PoemContract.UserPoemEntry.COLUMN_ID, PoemContract.UserPoemEntry.COLUMN_GENRE, PoemContract.UserPoemEntry.COLUMN_CONTENT,
                                    PoemContract.UserPoemEntry.COLUMN_USER, PoemContract.UserPoemEntry.COLUMN_LIKES, PoemContract.UserPoemEntry.COLUMN_DISLIKES
                            },
                            null,
                            null,
                            null,
                            null,
                            null
                    );
            // move the cursor's pointer to position zero.
            cursor.moveToFirst();

            // if there is data after the current cursor position, add it
            // to the ArrayList.
            if (!cursor.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cursor.getLong(0));
                    dataList.add(cursor.getString(1));
                    dataList.add(cursor.getString(2));
                    dataList.add(cursor.getString(3));
                    dataList.add(cursor.getInt(4));
                    dataList.add(cursor.getInt(5));

                    dataArrays.add(dataList);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("COULD NOT OBTAINALLROWS", e.toString());
            e.printStackTrace();
        }
    return dataArrays;
    }

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper
    {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERPOEMENTRIES);
            db.execSQL(SQL_CREATE_NEWPOEMENTRIES);
            db.execSQL(SQL_CREATE_POPULARPOEMENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }


}
