package com.hvdesai.register;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SATISH BODAKE on 9/10/2016.
 */
public class MyApplication extends Application {

    String str_name = "",
            str_email = "",
            str_country = "",
            str_phone_number = "",
            str_password = "";

    public MyDatabase dbObj = null;

    public SharedPreferences sharedPreferences = null;
    public SharedPreferences.Editor editor = null;
    public String sharedFileName = "hvdesaiFile";
    Context context = null;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

        dbObj = new MyDatabase(context);
        dbObj.getWritableDatabase();

        sharedPreferences = getSharedPreferences(
                sharedFileName,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void set_session(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }
    public String get_session(String key){
        String value = sharedPreferences.getString(key,"");
        return value;
    }

}
