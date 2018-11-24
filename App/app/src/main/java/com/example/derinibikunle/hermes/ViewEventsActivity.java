package com.example.derinibikunle.hermes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Date;
import java.util.List;


public class ViewEventsActivity extends AppCompatActivity {
    private ListView eventList;
    private Button doneButton;
    private Context context = this;


    //TODO:
    //pass in actual date ID in a bundle

    //TODO:
    //add a delete button on each event and enable clicking on it to delete said event
    //is there some kind of refresh screen setting so that once event is deleted it disappears from list?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        DatabaseQuery db = new DatabaseQuery();
        Date clickDate = new Date();
        List<EventObjects> events = db.getAllFutureEvents();

        //find events for today
        for(EventObjects event:events) {
            if(event.getStartDate() != clickDate)
                events.remove(event);
        }

        eventList = (ListView)findViewById(R.id.list_of_events);
        doneButton = (Button)findViewById(R.id.done_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CustomCalendarActivity.class);
                    context.startActivity(intent);
                }
            });

        //display all events
        //make a list adapter to iterate through all the events in the list and
        //display them each
    }
}
