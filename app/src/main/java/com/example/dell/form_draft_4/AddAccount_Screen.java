package com.example.dell.form_draft_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


public class AddAccount_Screen extends BaseActivity {

    Button createAccB;
    ArrayAdapter idAdapter;
    EditText accName;
    EditText accPass;
    EditText accHolderName;
    EditText accHolderEmail;
    EditText accPhone;

    int error_flag = 0;

    public void logoutClicked(View v) {
        Intent i = new Intent(this, Login_Screen.class);
        startActivity(i);
    }

    public void submitClicked(View v) {

        if (accPass.getText().toString().equals("")) {
            accPass.requestFocus();
            accPass.setError("This field cannot be empty");
            error_flag = 1;
        }

        if ((!accPhone.getText().toString().equals(""))&&(accPhone.getText().toString().length()!=10)) {
            accPhone.requestFocus();
            accPhone.setError("Enter a valid phone number.");
            error_flag = 1;
        }

        if (accName.getText().toString().equals("")) {
            accName.requestFocus();
            accName.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (error_flag == 0) {
            Intent i = new Intent(this, AddDevice_Screen.class);
            startActivity(i);
        } else
            error_flag = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account__screen);
        navigationView.setCheckedItem(R.id.nav_addAccount);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        accName = (EditText) findViewById(R.id.accName);
        accPass = (EditText) findViewById(R.id.accPass);
        accHolderEmail = (EditText) findViewById(R.id.accHolderEmail);
        accHolderName = (EditText) findViewById(R.id.accHolderName);
        accPhone = (EditText) findViewById(R.id.accPhone);
        createAccB = (Button) findViewById(R.id.addDeviceB);
        createAccB.setEnabled(false);
    }
}