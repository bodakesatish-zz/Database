package com.hvdesai.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityRegister extends AppCompatActivity {

    private Spinner spinn_country = null;
    String arr_country[] = {"America", "Canada", "India"};
    EditText edit_name = null, edit_email = null, edit_phone = null, edit_password = null, edit_confirm_password = null;
    String str_name = "", str_email = "", str_country = "", str_phone = "", str_password = "", str_confirm_password = "", str_error_message = "";
    Button btn_submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_confirm_password = (EditText) findViewById(R.id.edit_confirm_password);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        spinn_country = (Spinner) findViewById(R.id.spinn_country);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, arr_country);
        spinn_country.setAdapter(arrayAdapter);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = edit_name.getText().toString();
                str_email = edit_email.getText().toString();
                str_password = edit_password.getText().toString();
                str_confirm_password = edit_confirm_password.getText().toString();
                str_country = spinn_country.getSelectedItem().toString();
                str_phone = edit_phone.getText().toString();

                if (str_name.length() == 0) {
                    str_error_message = "Name is empty please enter name";
                    show_error_message(str_error_message);
                    edit_name.requestFocus();
                }
                else if (str_email.length() == 0) {
                    str_error_message = "Email is empty please enter email";
                    show_error_message(str_error_message);
                    edit_email.requestFocus();
                }
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    str_error_message = "Please enter valid email address";
                    show_error_message(str_error_message);
                    edit_email.requestFocus();
                }
                else if (str_phone.length() < 7 || str_phone.length() > 13) {
                    str_error_message = "Invalid phone number";
                    show_error_message(str_error_message);
                    edit_phone.requestFocus();
                }
                else if (str_password.length() == 0) {
                    str_error_message = "Password is empty please enter password";
                    show_error_message(str_error_message);
                    edit_password.requestFocus();
                } else if (str_confirm_password.length() == 0) {
                    str_error_message = "please enter confirm password";
                    show_error_message(str_error_message);
                    edit_confirm_password.requestFocus();
                } else if (!str_password.equals(str_confirm_password)) {//if str_password do not match with str_confirm_password
                    edit_password.requestFocus();
                    str_error_message = "Your password and confirm password do not match";
                    show_error_message(str_error_message);
                } else {

                    //Store student data in database
                    int result = ((MyApplication) getApplication())
                            .dbObj.insert_student_data(
                                    str_name,
                                    str_email,
                                    str_country,
                                    str_phone,
                                    str_password);
                    if (result > 0) {
                        edit_email.requestFocus();
                        str_error_message = "EmailID is already exist." +"\nPlease enter different email address.";
                        show_error_message(str_error_message);
                    } else {
                        //Save variables in session
                        ((MyApplication) getApplication()).set_session("email", str_email);

                        //Assign values to application class variables
                        ((MyApplication) getApplication()).str_name = str_name;
                        ((MyApplication) getApplication()).str_email = str_email;
                        ((MyApplication) getApplication()).str_password = str_password;
                        ((MyApplication) getApplication()).str_country = str_country;
                        ((MyApplication) getApplication()).str_phone_number = str_phone;

                        Toast.makeText(ActivityRegister.this,"Congrats, You registered successfully",Toast.LENGTH_SHORT).show();

                        //Go to Login Activity
                        Intent intent = new Intent(ActivityRegister.this,ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    }


                }


            }
        });


    }

    public boolean is_valid_email(String email) {
        boolean flag = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return flag;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder objBuilder =
                new AlertDialog.Builder(ActivityRegister.this);
        objBuilder.setTitle("HVDesai");
        objBuilder.setMessage("Do you really want to exit ?");
        objBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        objBuilder.setNegativeButton("No",
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

    public void show_error_message(String str_error_message) {
        AlertDialog.Builder objBuilder =
                new AlertDialog.Builder(ActivityRegister.this);
        objBuilder.setTitle("Alert");
        objBuilder.setMessage(str_error_message);
        objBuilder.setPositiveButton("Ok",
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
