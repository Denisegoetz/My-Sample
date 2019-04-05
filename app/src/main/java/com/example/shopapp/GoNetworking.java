package com.example.shopapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


//////////////////////////////////////////////////////
//
// Go NETWORKING - To the server we go...
//
/////////////////////////////////////////////////////

public class GoNetworking extends AsyncTask<String, Void, Void> {

    //local variables I use to work
    private String command;
    private String username;
    private String password;
    private String svrResp;
    private String firstName;
    private String lastName;
    private String usrEmail;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    StringBuffer stringBuffer = new StringBuffer();
    String comd   = "";
    String userN  = "";
    String passW  = "";
    String rtnR   = "";
    String fname  = "";
    String lname  = "";
    String email  = "";
    //use this method to get the context from anywhere in the app
    Context context = GlobalApplication.getAppContext();

    // This constructor takes two parameters from the mainActivity, username and password.
    public GoNetworking(String cmd, String user, String password) {
        this.command  = cmd;
        this.username = user;
        this.password = password;
    }

    // This constructor takes five parameters from the register, username, password
    public GoNetworking(String cmd, String user, String password, String firstName, String lastName, String usrEmail) {
        this.command    = cmd;
        this.username   = user;
        this.password   = password;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.usrEmail   = usrEmail;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //This method formats data to prepare for the (PostJSON) which sends a request to the server via JSON and getting back the results from server.
    // 1) Format your JSON to send.  IT ISN't REALLY JSON DATA AT this stage yet... NOT until it goes thru the mapper in postJSON.
    // 2) Call postJSON Object method, passing the data you formatted here.
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Void doInBackground(String... params) {


        //Create a HashMap to put data in that will "Become" Json in the folloing methods.
        HashMap<String, Object> jsonData = new HashMap<>();
        if (command.equals("Login")) {

            jsonData.put("command",   "Login");
            jsonData.put("uname",    username);
            jsonData.put("pword",    password);
            jsonData.put("rtnResp",   svrResp);
            jsonData.put("Fname",   firstName);
            jsonData.put("Lname",    lastName);
            jsonData.put("email",    usrEmail);

        }

        if (command.equals("Register")) {

            jsonData.put("command","Register");
            jsonData.put("uname",    username);
            jsonData.put("pword",    password);
            jsonData.put("rtnResp",   svrResp);
            jsonData.put("Fname",   firstName);
            jsonData.put("Lname",    lastName);
            jsonData.put("email",    usrEmail);

        }


        postJSON is = new postJSON();
        is.postJSONObject(jsonData);

        store_login();

        return null;
    }



    //////////////////////////////////////////////////////////////////////////////
    // onPostExecute happens after your doInBackground.  Here set the fields on
    //   the screen with whatever I got back from the server.
    /////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onPostExecute(Void aVoid) {

        if (command.equals("Login")) {
            if (rtnR.equals("OKreturn")){
                MainActivity.user.setText(this.userN );
                MainActivity.password.setText(this.passW);
                toast("You Solved the TOAST problem! Thank you Lord! LOGIN SUCCESSFUL");


            }
            if (rtnR.equals("BADreturn")){
                MainActivity.user.setText(this.userN );
                MainActivity.password.setText(this.passW);
//                MainActivity.attempts.setText(attempt_counter--);
//                attempts.setText(Integer.toString(atte/mpt_counter));
//                if (attempt_counter == 0) {
//                    login_button.setEnabled(false);


                toast("Incorrect Login or Password. Please try again. Are you are REGISTERED?");
            }
        }
        if (command.equals("Register")) {
                if (rtnR.equals("alreadyExistReturn")) {
                    Register.user.setText(this.userN);
                    Register.password.setText(this.passW);
                    Register.firstName.setText(this.fname);
                    toast("This User Name is already REGISTERED! Choose another User Name.");
                }
                if (rtnR.equals("okInsertreturn")) {
                    MainActivity.user.setText(this.userN);
                    MainActivity.password.setText(this.passW);
                    toast("You are Registered! The return was good from the REGISTER process.");
                    Register.user.setText("");
                    Register.password.setText("");
                    Register.firstName.setText("");
                    Register.lastName.setText("");
                    Register.email.setText("");
                }
            }
        }

    private void toast(final String text) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                    }

                }
        );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // 1) Post the JSON
    // 2) Read in the string that came back from the server if it isn't empty.
    // 3) Get what you want out of the JSON that was sent back to you.
    ////////////////////////////////////////////////////////////////////////////////////////////
    private class postJSON {


        public void postJSONObject(Object jsonData) {


            InputStream is = null;
            HashMap<String, Object> responseMap = null;

            //////////////////////////////////////////////////////////////////////////////////
            // 1) Prepare to send out a POST message to the Server
            // 2) Serialize raw data into JSON
            // 3) Connect to the Server
            /////////////////////////////////////////////////////////////////////////////////

            try {
                // URL url = new URL("http://10.0.2.2:8080/ServletDemo_war_exploded/ServletTester");
                URL url = new URL("http://10.0.2.2:8080/ListServlet/MainServlet");
                HttpURLConnection connect = null;   //Connection formed here
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("POST");                                       // Using post vs. get
                connect.setRequestProperty("Content-Type", "application/json");         // We are sending JSON
                connect.setRequestProperty("User-Agent", "Mozilla/5.0");                // Here we Fake that you are on the internet not phone


                //Raw data being converted and written out here via JACKSON --> JSON
                ObjectMapper mapper = new ObjectMapper();
                PrintWriter out = new PrintWriter(connect.getOutputStream());
                mapper.writeValue(out, jsonData);   //Post "mapped" or data to be converted->(Json) to Server here. The data becomes JSON here.

                //connect and get the response code.
                int responseCode = connect.getResponseCode();

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 1) Here check for response code.  If 200 then read the input from the Server line by line via Buffered Reader.
                // 2) Close reader
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                if (responseCode == 200) {
                    L("response received from Server");
                    BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String inputLine;                               //inputLine = holding area of incoming data
                    StringBuffer response = new StringBuffer();     //response = Will be the entire accumulated buffer of what is sent over.
                    while ((inputLine = in.readLine()) != null) {   //Copy the next line read in to the holding area and check if it's null
                        response.append(inputLine);                 //if not null, append to the accumulated lines in the string buffer(response).
                    }

                    in.close();    // close buffered reader

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 1) Take in the  JSON HashMap from the Server and deSerialize/Decode it.
                // 2) Get a few of the elements from the HashMap and put them into variables.  In onPostExecute, put them to the screen
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    responseMap = mapper.readValue(response.toString(), HashMap.class);   //Take in the JSON HashMap & decode it
                    comd   = (String) responseMap.get("command");
                    userN  = (String) responseMap.get("uname");     //getting back from server out of the HashMap.
                    passW  = (String) responseMap.get("pword");     //getting back from server out of the HashMap.
                    rtnR   = (String) responseMap.get("rtnResp");   //getting back from server out of the HashMap.
                    fname  = (String) responseMap.get("Fname");   //getting back from server out of the HashMap.
                    lname  = (String) responseMap.get("Lname");   //getting back from server out of the HashMap.
                    email  = (String) responseMap.get("email");   //getting back from server out of the HashMap.
                   //   id = response.toString();  // I had it like this before I had the HashMap done.  Not really needed now.

                }

                connect.disconnect();   //close HttpUrl connection
                L("disconnect from Server");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    ///////////////////////////////////////////////////////////////////////////////
    //wrapper so you don't have to type this in everytime. This makes nice output messages to console, so you can see what you are doing.
    //////////////////////////////////////////////////////////////////////////////
    private void L(String s) {

        Log.d("MyApp", "GoNetworking" + "#######" + s);
    }
    ///////////////////////////////////////////////////////////////////////////////
    // SHARED PREFERENCES
    //////////////////////////////////////////////////////////////////////////////
    private void store_login() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, rtnR);
        editor.apply();
        editor.commit();

    }
}
