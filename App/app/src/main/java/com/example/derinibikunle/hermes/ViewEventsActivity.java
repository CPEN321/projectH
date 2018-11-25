package com.example.derinibikunle.hermes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewEventsActivity extends AppCompatActivity {
    private ListView eventList;
    private Button doneButton;
    private TextView eventsToday;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");

        eventList = (ListView)findViewById(R.id.list_of_events);
        doneButton = (Button)findViewById(R.id.done_button);
        eventsToday = (TextView)findViewById(R.id.events_today);
        DatabaseQuery db = new DatabaseQuery();
        Date clickDate = CustomCalendarActivity.dayValueInCells.get(position);
        Toast.makeText(context, "Viewing events for: " +clickDate, Toast.LENGTH_LONG).show();

        List<EventObjects> events = db.getAllFutureEvents();

        //find events for today
        for(EventObjects event:events) {
            if((event.getStartDate().getDay() != clickDate.getDay()) ||
                    (event.getStartDate().getMonth() != clickDate.getMonth()) ||
                    (event.getStartDate().getYear() != clickDate.getYear()))
                events.remove(event);
        }

        if(events.isEmpty()) {
            //show that you have no events
            eventsToday.setText("You have no events today.");
        }
        else {
            eventsToday.setText("Your events today are: ");
            AdapterEventObjects adapter;

            adapter = new AdapterEventObjects(ViewEventsActivity.this, 0, (ArrayList<EventObjects>) events);
            eventList.setAdapter(adapter);
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CustomCalendarActivity.class);
                    context.startActivity(intent);
                }
            });
    }
}
