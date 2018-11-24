package com.example.derinibikunle.hermes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Date;

public class SetEventActivity extends AppCompatActivity {
    private TextView eventName;
    private TextView eventDate;
    private TextView startTime;
    private TextView endTime;
    private Button makeEvent;

    private String name;

    private Context context = this;

    private String[] dateStrings;

    private Date startDate;
    private String[] startTimeStrings;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startSecond;

    private Date endDate;
    private String[] endTimeStrings;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int endHour;
    private int endSecond;

    //TODO:
    // error catching if user enters the date/time in wrong

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //do some stuff in here
        setContentView(R.layout.activity_set_event);

        eventName = (EditText)findViewById(R.id.event_input_name);
        eventDate = (EditText)findViewById(R.id.event_input_date);
        startTime = (EditText)findViewById(R.id.event_input_start_time);
        endTime = (EditText)findViewById(R.id.event_input_end_time);
        makeEvent = (Button)findViewById(R.id.add_event);

        makeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //parse the date and time to make a java.util.Dates to pass to firebase
                //this for error checking
                if(eventName.getText().equals("") || eventDate.getText().equals("")|| startTime.getText().equals("")|| endTime.getText().equals("")) {
                    Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_LONG).show();
                }
                else {
                    name = eventName.getText().toString();
                    dateStrings = splitDate(eventDate.getText().toString());
                    startTimeStrings = splitTime(startTime.getText().toString());
                    endTimeStrings = splitTime(endTime.getText().toString());

                    startDate = getDate(Integer.parseInt(dateStrings[0]), Integer.parseInt(dateStrings[1]),
                            Integer.parseInt(dateStrings[2]), Integer.parseInt(startTimeStrings[0]),
                            Integer.parseInt(startTimeStrings[1]));

                    endDate = getDate(Integer.parseInt(dateStrings[0]), Integer.parseInt(dateStrings[1]),
                            Integer.parseInt(dateStrings[2]), Integer.parseInt(endTimeStrings[0]),
                            Integer.parseInt(endTimeStrings[1]));

                    DatabaseQuery.pushToDb(new EventObjects(name, startDate, endDate));
                    Toast.makeText(context, "Event Added!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //separate date into separate fields
    public static String[] splitDate(String date){
        return date.split("/");
    }

    //separate time into separate fields
    public static String[] splitTime(String time) {
        return time.split(":");
    }

    //turn the date/time ints into a java Date
    public static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}