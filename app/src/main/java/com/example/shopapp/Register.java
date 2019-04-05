package com.example.shopapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    Context c = this;
    private static Button register_button;
    public  static EditText user;
    public  static EditText password;
    public  static EditText firstName;
    public  static EditText lastName;
    public  static EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterButton();
    }

    private void RegisterButton() {

        register_button  = (Button)  findViewById(R.id.button_register);
        user             = (EditText)findViewById(R.id.editText_user);
        password         = (EditText)findViewById(R.id.editText_password);
        firstName        = (EditText)findViewById(R.id.editText_fName);
        lastName         = (EditText)findViewById(R.id.editText_lName);
        email            = (EditText)findViewById(R.id.editText_email);



        //Creating an onclick listener for register page
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (user.length() == 0  || password.length() == 0 || firstName.length() == 0
                      || lastName.length() == 0  || email.length() == 0){
                    Toast.makeText(Register.this,"Please fill in all the fields.",
                            Toast.LENGTH_LONG).show();

                }else {

                    GoNetworking n = new GoNetworking("Register",user.getText().toString(), password.getText().toString()
                                    ,firstName.getText().toString(),lastName.getText().toString(),email.getText().toString());
                    n.execute();

                    return;
                }
            }


        });

    }
}
