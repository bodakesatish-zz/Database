package com.hvdesai.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    String str_email = "", str_password = "", str_error_message = "";
    EditText edit_email = null, edit_password = null;
    Button btn_login = null, btn_register = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_email = (EditText) findViewById(R.id.edit_username);
        edit_password = (EditText) findViewById(R.id.edit_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                str_email = edit_email.getText().toString();
                str_password = edit_password.getText().toString();

                if (TextUtils.isEmpty(str_email)) {
                    str_error_message = "Please enter email";
                    show_error_message(str_error_message);
                    edit_email.requestFocus();
                }
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    str_error_message = "Please enter valid email address";
                    show_error_message(str_error_message);
                    edit_email.requestFocus();
                }
                else if (TextUtils.isEmpty(str_password)) {
                    str_error_message = "Please enter password";
                    show_error_message(str_error_message);
                    edit_password.requestFocus();
                }
                else {
                    boolean flag = ((MyApplication) getApplication()).dbObj.check_user_credentials(str_email, str_password);
                    //flag is true if user enters valid credentials or false if invalid
                    if (flag) {
                        Toast.makeText(ActivityLogin.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                        ((MyApplication) getApplication()).set_session("email", str_email);

                        Intent intentLogin = new Intent(ActivityLogin.this, ActivityProfile.class);
                        startActivity(intentLogin);
                        finish();
                    } else {
                        edit_email.requestFocus();
                        str_error_message = "Invalid user credentials";
                        show_error_message(str_error_message);

                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intentRegister);
                finish();
            }
        });


    }// End of onCreate

    public void show_error_message(String str_error_message)
    {
        AlertDialog.Builder objBuilder =
                new AlertDialog.Builder(ActivityLogin.this);
        objBuilder.setTitle("Alert");
        objBuilder.setMessage(str_error_message);
        objBuilder.setPositiveButton("Ok" ,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = objBuilder.create();
        alertDialog.show();
    }
}
