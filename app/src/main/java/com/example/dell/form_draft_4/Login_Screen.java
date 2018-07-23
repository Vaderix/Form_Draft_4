package com.example.dell.form_draft_4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Login_Screen extends AppCompatActivity {

    private static final String TAG = "Login_Screen";
    private static final String LOGIN_URL = "https://www.reddit.com/api/login/";
    private static final String CP_LOGIN_URL = "http://205.147.110.128:2122/";

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

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//What the hell is this?
        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);                                //What the hell is this?

        x_username = username.getText().toString();
        x_password = password.getText().toString();

        mProgressBar.setVisibility(View.VISIBLE);

        login(x_username, x_password);
    }

    private void login(final String username, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CP_LOGIN_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        ServiceAPI loginAPI = retrofit.create(ServiceAPI.class);

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");

        Call<String> call = loginAPI.userLogin(headerMap, username, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    String testSTR = response.body();

                    Log.d(TAG, "onResponse: Server feed: " + response.body());
                    Log.d(TAG, "onResponse: Server Response: " + response.toString());

                    if (testSTR.equals("failed")) {

                        Log.d(TAG, "-----------------IS THIS SHOWING UP?!-----------------");
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(Login_Screen.this, "Incorrect credentials!", Toast.LENGTH_SHORT).show();

                    } else {

                        mProgressBar.setVisibility(View.GONE);

                        setLoginPreferences(x_username, x_password);
                        Toast.makeText(Login_Screen.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login_Screen.this, Dashboard.class);
                        startActivity(i);

                    }

                } catch (
                        NullPointerException e)

                {
                    Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);

                Log.e(TAG, "\nonFailure: Something went wrong! " + t.getMessage());
                Toast.makeText(Login_Screen.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
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

    private void seSessionParams(int serialNum, String accID, String userType){
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


    /*

    //For Reddit Login:

    private void login(final String username, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceAPI loginAPI = retrofit.create(ServiceAPI.class);

        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        Call<CheckLogin> call = loginAPI.signIn(headerMap, username, username, password, "json");

        call.enqueue(new Callback<CheckLogin>() {
            @Override
            public void onResponse(Call<CheckLogin> call, Response<CheckLogin> response) {

                try {
                    String testSTR = response.body().toString();
                    String[] verifyLogin = testSTR.split("data=");

                    Log.d(TAG, "onResponse: Server feed: " + response.body().toString());
                    Log.d(TAG, "onResponse: Server Response: " + response.toString());
                    Log.d(TAG, "onResponse: testSTR Value: " + testSTR);
                    Log.d(TAG, "onResponse: verifyLogin Value: " + verifyLogin[1]);

                    String modhash = response.body().getJson().getData().getModhash();
                    String cookie = response.body().getJson().getData().getCookie();

                    Log.d(TAG, "onResponse: ModHash: " + modhash);
                    Log.d(TAG, "onResponse: Cookie: " + cookie);

                    if (verifyLogin[1].equals("null}}")) {

                        Log.d(TAG, "-----------------IS THIS SHOWING UP?!-----------------");
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(Login_Screen.this, "Incorrect credentials!", Toast.LENGTH_SHORT).show();

                    } else {

                        setSessionParams(username, modhash, cookie);
                        mProgressBar.setVisibility(View.GONE);

                        setLoginPreferences(x_username, x_password);
                        Toast.makeText(Login_Screen.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login_Screen.this, Dashboard.class);
                        startActivity(i);

                    }

                } catch (
                        NullPointerException e)

                {
                    Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckLogin> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);

                Log.e(TAG, "\nonFailure: Something went wrong! " + t.getMessage());
                Toast.makeText(Login_Screen.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }*/


    /*

    //For reddit login, saving Modhash and Cookie:

    private void setSessionParams(String username, String modhash, String cookie) {

        SharedPreferences sessionPreferences = PreferenceManager.getDefaultSharedPreferences(Login_Screen.this);
        SharedPreferences.Editor sessionEditor = sessionPreferences.edit();

        Log.d(TAG, "setSessionParams: Storing Session Variables: \n" +
                "username: " + username + "\n" +
                "modhash: " + modhash + "\n" +
                "cookie: " + cookie + "\n");

        sessionEditor.putString("Session_User", username);
        sessionEditor.commit();
        sessionEditor.putString("Session_ModHash", modhash);
        sessionEditor.commit();
        sessionEditor.putString("Session_Cookie", cookie);
        sessionEditor.commit();
    }*/
}
