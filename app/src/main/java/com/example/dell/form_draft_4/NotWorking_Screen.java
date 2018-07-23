package com.example.dell.form_draft_4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotWorking_Screen extends AppCompatActivity {

    ArrayList<String> listItems = new ArrayList<>();
    NotWorking_Adapter adapter;
    StringBuilder not_working_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_working__screen);
        not_working_key=new StringBuilder("");

        /////////////////////Replace with Taking String values from server and putting in ArrayList
        for (int i = 1; i <= 10; i++) {
            listItems.add("Vehicle "+i);
        }
        /////////////////////

        adapter = new NotWorking_Adapter(this,R.layout.not_working_row, listItems);
        ListView notWorking_List = (ListView) findViewById(R.id.notWorking_List);
        notWorking_List.setAdapter(adapter);

        notWorking_List.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        not_working_key=new StringBuilder(String.valueOf(parent.getItemAtPosition(pos)));
                        Toast.makeText(NotWorking_Screen.this, not_working_key.toString(), Toast.LENGTH_LONG).show();

                        NotWorking_Dialog testDialog= new NotWorking_Dialog();
                        testDialog.show(getSupportFragmentManager(),"Not_Working_Dialog");

                    }
                }
        );
    }
}
