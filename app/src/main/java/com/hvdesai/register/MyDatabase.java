package com.hvdesai.register;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by SATISH BODAKE on 9/17/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDB.db";

    //List Of Tables
    public String TABLE_STUDENT = "student";

    //List Of Columns
    public String COL_ID = "id",
            COL_NAME = "name",
            COL_COUNTRY = "country",
            COL_EMAIL = "email",
            COL_PHONE = "phone",
            COL_USERNAME = "username",
            COL_PASSWORD = "password";

    public String LOG_TAG = "MyDatabase";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(LOG_TAG, "In MyDatabase Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG, "In MyDatabase onCreate");
        // Create Tables
        //Query->CREATE TABLE IF NOT EXISTS student(id INTEGER ,name TEXT,country TEXT,email TEXT,phone TEXT,username TEXT,password TEXT)
        String CREATE_QUERY_TABLE_STUDENT = "CREATE TABLE IF NOT" +
                " EXISTS " + TABLE_STUDENT + "(" + COL_ID + " INTEGER ," +
                COL_NAME + " TEXT," + COL_COUNTRY + " TEXT," +
                COL_EMAIL + " TEXT," + COL_PHONE + " TEXT," +
                COL_USERNAME + " TEXT," + COL_PASSWORD + " TEXT);";
        Log.i(LOG_TAG, "CREATE_QUERY_TABLE_STUDENT->"
                + CREATE_QUERY_TABLE_STUDENT);
        db.execSQL(CREATE_QUERY_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        Log.i(LOG_TAG, "In MyDatabase onUpgrade");
        Log.i(LOG_TAG, "oldVersion->" + oldVersion);
        Log.i(LOG_TAG, "newVersion->" + newVersion);
        //Your code here
    }

    //insert student data in student table
    public int insert_student_data(
            String str_name, String str_email,
            String str_country, String str_phone,
            String str_password
    ) {
        Log.i(LOG_TAG, "In insert_student_data");
        SQLiteDatabase db = this.getWritableDatabase();
        String sql_query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COL_EMAIL + " = '" + str_email + "'";
        Cursor c = db.rawQuery(sql_query, null);
        int numRecords = c.getCount();
        Log.i(LOG_TAG, "NumRecords->" + numRecords);
        if (numRecords == 0) {//If no email_id found
            int result = -1;//fail on -1 else success
            ContentValues cv = new ContentValues();
            cv.put(COL_NAME, str_name);
            cv.put(COL_EMAIL, str_email);
            cv.put(COL_COUNTRY, str_country);
            cv.put(COL_PHONE, str_phone);
            cv.put(COL_PASSWORD, str_password);
            Log.i(LOG_TAG, "ContentValues->" + cv);
            long rowID = db.insert(TABLE_STUDENT, null, cv);
            result = (int) rowID;
            Log.i(LOG_TAG, "Result->" + result);
        }//If ends
        c.close();
        return numRecords;
    }

    //Check user credentials
    //Call from LoginScreen
    public boolean check_user_credentials(String str_email,
                                          String str_password) {
        Log.i(LOG_TAG, "In check_user_credentials");
        SQLiteDatabase db = this.getWritableDatabase();
        String sql_query = "SELECT * FROM " + TABLE_STUDENT +
                " WHERE " + COL_EMAIL + "='" + str_email + "' AND " +
                COL_PASSWORD + "='" + str_password + "'";
        Log.i(LOG_TAG, "sql_query->" + sql_query);
        Cursor c = db.rawQuery(sql_query, null);
        int numRecords = c.getCount();
        Log.i(LOG_TAG, "numRecords->" + numRecords);
        c.close();
        if (numRecords > 0) {//User is valid
            return true;
        } else {//User is invalid
            return false;
        }
    }

    public HashMap<String, String> get_user_details(String str_email) {
        Log.i(LOG_TAG, "In get_user_details");
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> hm = new HashMap<>();
        String sql_query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COL_EMAIL + "='" + str_email + "'";
        Cursor c = db.rawQuery(sql_query, null);
        int numRecords = c.getCount();
        Log.i(LOG_TAG, "numRecords->" + numRecords);
        if (numRecords > 0) {
            c.moveToFirst();
            String str_name = c.getString(c.getColumnIndexOrThrow(COL_NAME));
            String str_country = c.getString(c.getColumnIndexOrThrow(COL_COUNTRY));
            String str_phone = c.getString(c.getColumnIndexOrThrow(COL_PHONE));
            String str_password = c.getString(c.getColumnIndexOrThrow(COL_PASSWORD));
            hm.put("name", str_name);
            hm.put("country", str_country);
            hm.put("phone", str_phone);
            hm.put("email", str_email);
            hm.put("password", str_password);
        }
        Log.i(LOG_TAG, "HashMap->" + hm);
        c.close();
        return hm;
    }


}
