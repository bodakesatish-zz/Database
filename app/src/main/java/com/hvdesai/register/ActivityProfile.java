package com.hvdesai.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class ActivityProfile extends AppCompatActivity {

    String str_name = "", str_email = "", str_password = "", str_phone = "", str_country = "";
    TextView txt_name = null, txt_email = null, txt_phone = null, txt_password = null, txt_country = null;
    HashMap<String, String> hm = null;
    String str_session_email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        str_session_email = ((MyApplication) getApplication()).get_session("email");

        hm = ((MyApplication) getApplication()).dbObj.get_user_details(str_session_email);

        str_name = hm.get("name");
        str_email = hm.get("email");
        str_country = hm.get("country");
        str_phone = hm.get("phone");
        str_password = hm.get("password");

        //Initialize the components
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_password = (TextView) findViewById(R.id.txt_password);
        txt_country = (TextView) findViewById(R.id.txt_country);

        //Bind the data to components
        txt_name.setText(str_name);
        txt_email.setText(str_email);
        txt_password.setText(str_password);
        txt_phone.setText(str_phone);
        txt_country.setText(str_country);
    }
}
