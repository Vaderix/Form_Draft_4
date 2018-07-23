package com.example.dell.form_draft_4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDevice_Screen extends BaseActivity {

    Spinner selectAccSpinner;
    Spinner selectDeviceSpinner;
    EditText deviceID;
    EditText simPhoneNumber;
    EditText deviceIMEI;
    EditText simSerial;
    EditText vehicleNumber;
    Spinner vehicleTypeSpinner;
    EditText driverName;
    EditText driverNumber;
    Button addDeviceB;
    ArrayAdapter idAdapter;
    EditText subTill;
    EditText odoReading;

    int error_flag = 0;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    public void logoutClicked(View v) {
        Intent i = new Intent(this, Login_Screen.class);
        startActivity(i);
    }

    private void updateLabel() {
        subTill.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitClicked(View v) {
        if (odoReading.getText().toString().equals("")) {
            odoReading.requestFocus();
            odoReading.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (driverNumber.getText().toString().equals("")) {
            driverNumber.requestFocus();
            driverNumber.setError("This field cannot be empty.");
            error_flag = 1;
        }
        if (driverNumber.getText().toString().equals("")) {
            driverNumber.requestFocus();
            driverNumber.setError("This field cannot be empty.");
            error_flag = 1;
        } else if (driverNumber.getText().toString().length() != 10) {
            driverNumber.requestFocus();
            driverNumber.setError("Enter a valid phone number.");
            error_flag = 1;
        }

        if (driverName.getText().toString().equals("")) {
            driverName.requestFocus();
            driverName.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (vehicleNumber.getText().toString().equals("")) {
            vehicleNumber.requestFocus();
            vehicleNumber.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (simSerial.getText().toString().equals("")) {
            simSerial.requestFocus();
            simSerial.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (deviceIMEI.getText().toString().equals("")) {
            deviceIMEI.requestFocus();
            deviceIMEI.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (simPhoneNumber.getText().toString().equals("")) {
            simPhoneNumber.requestFocus();
            simPhoneNumber.setError("This field cannot be empty.");
            error_flag = 1;
        } else if (simPhoneNumber.getText().toString().length() != 10) {
            simPhoneNumber.requestFocus();
            simPhoneNumber.setError("Enter a valid phone number.");
            error_flag = 1;
        }

        if (deviceID.getText().toString().equals("")) {
            deviceID.requestFocus();
            deviceID.setError("This field cannot be empty.");
            error_flag = 1;
        }

        if (error_flag == 0) {
            Intent i = new Intent(this, TestScreen.class);
            startActivity(i);
        } else
            error_flag = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device__screen);
        navigationView.setCheckedItem(R.id.nav_addDevice);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        selectAccSpinner = (Spinner) findViewById(R.id.selectAccSpinner);
        selectDeviceSpinner = (Spinner) findViewById(R.id.selectDeviceSpinner);
        deviceID = (EditText) findViewById(R.id.deviceID);
        simPhoneNumber = (EditText) findViewById(R.id.simPhoneNumber);
        deviceIMEI = (EditText) findViewById(R.id.deviceIMEI);
        simSerial = (EditText) findViewById(R.id.simSerial);
        vehicleNumber = (EditText) findViewById(R.id.vehicleNumber);
        vehicleTypeSpinner = (Spinner) findViewById(R.id.vehicleTypeSpinner);
        driverName = (EditText) findViewById(R.id.driverName);
        driverNumber = (EditText) findViewById(R.id.driverNumber);
        addDeviceB = (Button) findViewById(R.id.addDeviceB);
        odoReading = (EditText) findViewById(R.id.odoReading);
        subTill = (EditText) findViewById(R.id.subTill);
        myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR) + 1);                             //Incrementing Calendar current Year by 1
        updateLabel();                                                                              //Setting Default input value
        ////////////////////////////////////////////////////////////////////////////////////////////
        final DatePickerDialog.OnDateSetListener select_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        subTill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddDevice_Screen.this, select_date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        addDeviceB.setEnabled(false);

        Resources selectDevRes = getResources();
        idAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectDevRes.getStringArray(R.array.deviceName));
        idAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        selectDeviceSpinner.setAdapter(idAdapter);

        deviceID.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deviceIMEI.setText(deviceID.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
