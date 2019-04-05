package com.example.shopapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//////////////////////////////////////////////////////
// NETWORKING
/////////////////////////////////////////////////////
public class Networking extends AsyncTask<Void,Void,Void> {


    StringBuffer stringBuffer = new StringBuffer("");
    String firstname;
    String lastname;
//    String uname;
//    String pword;
//    String id;

    @Override
    protected Void doInBackground(Void... voids) {

        L("doInBackground");

        try {
            URL url = new URL("https://api.myjson.com/bins/1fz6be");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();   //Connection formed here
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while (line != null) {
                line = bufferedReader.readLine();
                stringBuffer.append(line + LineSeparator);
            }
            System.out.print("/////Output from JSON/////" + this.stringBuffer);

            JSONObject jo = new JSONObject(String.valueOf(this.stringBuffer));

            firstname = jo.getString("firstname");
            lastname = jo.getString("lastname");

            L("Decoded Json to name: " + firstname);
            L("Decoded Json to lastname: " + lastname);

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.user.setText(this.firstname + this.lastname);

    }

    ///////////////////////////////////////////////////////////////////////////////
    //wrapper so you don't have to type this in everytime. This makes nice output messages to console, so you can see what you are doing.
    //////////////////////////////////////////////////////////////////////////////
    private void L(String s) {

        Log.d("MyApp", "Networking" + "#######" + s);
    }


}