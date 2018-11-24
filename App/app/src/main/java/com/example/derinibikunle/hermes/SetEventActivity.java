package com.example.derinibikunle.hermes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;

public class SetEventActivity extends AppCompatActivity {
    private TextView eventName;
    private TextView eventDate;
    private TextView startTime;
    private TextView endTime;
    private Button makeEvent;
    private String name;
    private Date startDate;
    private Date endDate;
    private Context context = this;

    //TODO:
    // error catching if user enters the date/time in wrong
    //parse event date and time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //do some stuff in here
        setContentView(R.layout.create_new_event);

        eventName = (TextView)findViewById(R.id.name_input_text);
        eventDate = (TextView)findViewById(R.id.date_input_text);
        startTime = (TextView)findViewById(R.id.start_time_input_text);
        endTime = (TextView)findViewById(R.id.end_time_input_text);
        makeEvent = (Button)findViewById(R.id.add_event);

        eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = eventName.toString();
            }
        });

        //parse the date and time to make a java.util.Dates to pass to firebase

        makeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseQuery.pushToDb(new EventObjects(name, startDate, endDate));
                Toast.makeText(context, "Event Added!", Toast.LENGTH_LONG).show();
            }
        });



    }
}