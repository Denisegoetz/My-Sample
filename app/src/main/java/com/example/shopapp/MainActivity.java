package com.example.shopapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    public  static EditText user;
    public  static EditText password;
    private static Button login_button;
    private static TextView attempts;
    int attempt_counter = 5;
    Context c = this;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Activity.MODE_PRIVATE);

        if (sharedPreferences.contains(TEXT)) {
            text = sharedPreferences.getString(TEXT,text);

            if (text.equals("OKreturn")) {
                Intent intent = new Intent("com.example.shopapp.ShopList");
                startActivity(intent);
            }else {
                LoginButton();
            }

        }else {
            LoginButton();
        }
    }


    public void register_me (View view){

        Toast.makeText(MainActivity.this,"So you want to register, OK",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent("com.example.shopapp.Register" );
        startActivity(intent);
    }

    public void LoginButton() {

        user          = (EditText)findViewById(R.id.editText_user);
        password      = (EditText)findViewById(R.id.editText_password);
        attempts      = (TextView)findViewById(R.id.textView_attempt_counter);
        login_button  = (Button)  findViewById(R.id.button_login);

        attempts.setText(Integer.toString(attempt_counter));

        //Creating an onclick listener for login page
        login_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        L("Login button Pressed!");

                        if (user.length() == 0  || password.length() == 0){
                            Toast.makeText(MainActivity.this,"Please fill in Username and Password.",
                                    Toast.LENGTH_LONG).show();

                        } else {

                         //   GoNetworking n = new GoNetworking("Login",user.getText().toString(), password.getText().toString());
                         //   n.execute();

                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Activity.MODE_PRIVATE);
                            if (sharedPreferences.contains(TEXT)) {
                                text = sharedPreferences.getString(TEXT,text);

                                if (text.equals("OKreturn")) {

                                    Intent intent = new Intent("com.example.shopapp.ShopList");
                                    startActivity(intent);
                                }
                                GoNetworking n = new GoNetworking("Login",user.getText().toString(), password.getText().toString());
                                n.execute();

                            }else{
                                GoNetworking n = new GoNetworking("Login",user.getText().toString(), password.getText().toString());
                                n.execute();
                            }
                        }


    //                    Intent intent = new Intent("com.example.shopapp.ShopList" );
    //                    startActivity(intent);

/*                        if (user.getText().toString().equals("Denise")&&
                            password.getText().toString().equals("pass") ) {

                             Toast.makeText(MainActivity.this,"User and Password is correct",
                                     Toast.LENGTH_SHORT).show();
                             //redirect to User Screen
                             Intent intent = new Intent("com.example.shopapp.User" );
                             startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this, "User or Password is NOT correct",
                                    Toast.LENGTH_SHORT).show();

                            attempt_counter--;
                            attempts.setText(Integer.toString(attempt_counter));
                            if (attempt_counter == 0) {
                                login_button.setEnabled(false);
                            }
                          }*/
                        }

                    });


        }


    @Override
    protected void onResume() {
        super.onResume();

//            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Activity.MODE_PRIVATE);
//         //   sharedpreferences = getSharedPreferences(AppPREFERENCES, Context.MODE_PRIVATE);
//            if (sharedPreferences.contains(TEXT)) {
//                text = sharedPreferences.getString(TEXT,"");
//           //     String sessionId = sharedPreferences.getString("TEXT", null);
//
//                if (text.equals("OKreturn")) {
//
//                    Intent intent = new Intent("com.example.shopapp.ShopList");
//                    //    intent.putExtra("SessionId", sessionId);
//                    startActivity(intent);
//                }
//            }

    }

    ///////////////////////////////////////////////////////////////////////////////
    //wrapper so you don't have to type this in everytime. This makes nice output messages to console, so you can see what you are doing.
    //////////////////////////////////////////////////////////////////////////////
    private void L(String s) {

        Log.d("MyApp", "LoginActivity" + "#######" + s);
    }

 }

