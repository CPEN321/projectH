package com.example.derinibikunle.hermes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SetEventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //do some stuff in here
        setContentView(R.layout.create_new_event);
        //CalendarCustomView mView = (CalendarCustomView)findViewById(R.id.custom_calendar);
    }
}