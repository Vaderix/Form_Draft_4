package com.example.dell.form_draft_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends BaseActivity {

    Button totalButton;
    Button notWorkingButton;
    Button inMotionButton;
    Button idleButton;
    Button stoppedButton;

    public void goto_NotWork(View v) {
        Intent i = new Intent(this, NotWorking_Screen.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navigationView.setCheckedItem(R.id.nav_Dashboard);

        /*Setting up all the buttons and disabling totalButton*/
        totalButton = findViewById(R.id.totalButton);
        notWorkingButton = findViewById(R.id.notWorkingButton);
        inMotionButton = findViewById(R.id.inMotionButton);
        idleButton = findViewById(R.id.idleButton);
        stoppedButton = findViewById(R.id.stoppedButton);

        totalButton.setEnabled(false);
    }
}