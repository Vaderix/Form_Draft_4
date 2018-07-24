package com.example.dell.form_draft_4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.security.NetworkSecurityPolicy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Login_Screen extends AppCompatActivity {

    private static final String TAG = "Login_Screen";

    private static final String SOAP_ACTION = "http://tempuri.org/CheckLoginCredendials";
    private static final String METHOD_NAME = "CheckLoginCredendials";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://205.147.110.128:2122/CpServices.asmx";

    EditText username;
    EditText password;
    CheckBox remMe;
    Button signIn;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private String x_username;
    private String x_password;
    Button logInB;
    int error_flag = 0;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        signIn = (Button) findViewById(R.id.signIn);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        remMe = (CheckBox) findViewById(R.id.remMe);
        logInB = (Button) findViewById(R.id.logInB);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);

        mProgressBar.setVisibility(View.GONE);
        logInB.setEnabled(false);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);                   //Remember to change key to unique key
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);                           //Remember to change key to unique key
        if (saveLogin == true) {
            username.setText(loginPreferences.getString("Login_Rem_Username", ""));                     //Remember to change key to unique key
            password.setText(loginPreferences.getString("Login_Rem_Password", ""));                     //Remember to change key to unique key
            remMe.setChecked(true);
        }
    }

    public void onSignIn(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

        x_username = username.getText().toString();
        x_password = password.getText().toString();

        mProgressBar.setVisibility(View.VISIBLE);

        myAsyncTask loginTask = new myAsyncTask();
        loginTask.execute();
    }

    private void setLoginPreferences(String username, String password) {

        if (remMe.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);                                     //Remember to change key to unique key
            loginPrefsEditor.putString("Login_Rem_Username", username);                                     //Remember to change key to unique key
            loginPrefsEditor.putString("Login_Rem_Password", password);                                     //Remember to change key to unique key
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
    }

    //Will set it up to call on successful login Later (After fixing the login issue)

    private void seSessionParams(int serialNum, String accID, String userType) {
        SharedPreferences sessionPreferences = PreferenceManager.getDefaultSharedPreferences(Login_Screen.this);
        SharedPreferences.Editor sessionEditor = sessionPreferences.edit();

        Log.d(TAG, "setSessionParams: Storing Session Variables: \n" +
                "Serial Number: " + serialNum + "\n" +
                "Account ID: " + accID + "\n" +
                "User Type: " + userType + "\n");

        sessionEditor.putInt("Session_serialNum", serialNum);
        sessionEditor.commit();
        sessionEditor.putString("Session_accID", accID);
        sessionEditor.commit();
        sessionEditor.putString("Session_userType", userType);
        sessionEditor.commit();
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean logSuccess = false;
            Log.d(TAG, "doInBackground: Started");

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Pass value for userID variable of the web service
            PropertyInfo unameProp = new PropertyInfo();
            unameProp.setName("userId");//Define the variable name in the web service method
            unameProp.setValue(x_username);//set value for userName variable
            Log.d(TAG, "doInBackground: CHECK USERNAME: "+ x_username);
            unameProp.setType(String.class);//Define the type of the variable
            request.addProperty(unameProp);//Pass properties to the variable

            //Pass value for Password variable of the web service
            PropertyInfo passwordProp = new PropertyInfo();
            passwordProp.setName("password");
            passwordProp.setValue(x_password);
            Log.d(TAG, "doInBackground: CHECK PASSWORD: "+x_password);
            passwordProp.setType(String.class);
            request.addProperty(passwordProp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            httpTransport.debug = true;

            try {
                httpTransport.call(SOAP_ACTION, envelope);
                Log.d(TAG, "After HTTP Transport Call: Is this message showing up?");

            } catch (HttpResponseException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "HTTPLOG: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "IOLOG: " + e.getMessage());
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "XMLLOG: " + e.getMessage());
                e.printStackTrace();
            } //send request

            Object result;
            try {
                result = (Object) envelope.getResponse();
                Log.d(TAG, "Response: " + String.valueOf(result));

                logSuccess = !(String.valueOf(result).equals("failed") || String.valueOf(result).equals("null"));

            } catch (SoapFault e) {
                // TODO Auto-generated catch block
                Log.e("SOAPLOG", e.getMessage());
                e.printStackTrace();
            }

            return logSuccess;
        }


        @Override
        protected void onPostExecute(Boolean logSuccess) {

            mProgressBar.setVisibility(View.GONE);

            if (logSuccess) {
                Log.d(TAG, "onPostExecute: Login Successful!");
                Toast.makeText(Login_Screen.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                setLoginPreferences(x_username, x_password);
                ////----------------------
                // Set Session Params Here (after fixing login)
                ////----------------------

                Intent i = new Intent(Login_Screen.this, Dashboard.class);
                startActivity(i);

            } else {
                Log.d(TAG, "onPostExecute: Login Failed!");
                Toast.makeText(Login_Screen.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
