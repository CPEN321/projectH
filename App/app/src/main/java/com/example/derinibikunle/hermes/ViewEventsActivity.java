package com.example.derinibikunle.hermes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.derinibikunle.hermes.DatabaseQuery.getuid;


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
        final Date clickDate = CustomCalendarActivity.dayValueInCells.get(position);
        Toast.makeText(context, "Viewing events for: " +clickDate, Toast.LENGTH_LONG).show();

        //List<EventObjects> events = db.getAllFutureEvents();

        //find events for today

        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference().child("users").child(getuid()).child("calendar_info");
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<EventObjects> list = new ArrayList<EventObjects>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    list.add(data.getValue(EventObjects.class));
                }

                Calendar today = Calendar.getInstance();
                today.setTime(clickDate);

                Calendar otherDay = Calendar.getInstance();

                for(EventObjects event:list) {
                    otherDay.setTime(event.getStartDate());
                    if((otherDay.get(Calendar.DAY_OF_MONTH) != today.get(Calendar.DAY_OF_MONTH)) ||
                            (otherDay.get(Calendar.MONTH) != today.get(Calendar.MONTH)) ||
                            (otherDay.get(Calendar.YEAR) != today.get(Calendar.YEAR)))
                        list.remove(event);
                }
                if(list.isEmpty()) {
                    //show that you have no events
                    eventsToday.setText("You have no events today.");
                }
                else {
                    eventsToday.setText("Your events today are: ");
                    AdapterEventObjects adapter;

                    adapter = new AdapterEventObjects(ViewEventsActivity.this, 0, (ArrayList<EventObjects>) list);
                    eventList.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomCalendarActivity.class);
                context.startActivity(intent);
            }
        });
    }
}