package com.example.dell.form_draft_4;

import android.os.Bundle;

public class TestScreen extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        navigationView.setCheckedItem(R.id.nav_addUser);
    }

}
