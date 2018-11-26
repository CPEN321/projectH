package com.example.derinibikunle.hermes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SimpleCursorTreeAdapter;
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
    private TextView repeatNum;
    private Button makeEvent;
    private CheckBox repeatCheck;
    private boolean repeat = false;

    private int timeSize = 2;
    private int dateSize = 3;

    private String name;

    private Context context = this;

    private String[] dateStrings;

    private Date startDate;
    private String[] startTimeStrings;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMinute;

    private Date endDate;
    private String[] endTimeStrings;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int endHour;
    private int endMinute;

    private int numRepeats;

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
        repeatCheck = (CheckBox)findViewById(R.id.checkbox_repeat);
        repeatNum = (EditText)findViewById(R.id.number_of_repeats);

        repeatCheck.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   repeat = true;
               }
           }
        );

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

                    //more error checking
                    if (dateStrings.length != dateSize) {
                        Toast.makeText(context, "Please fill in all date fields.", Toast.LENGTH_LONG).show();
                    }
                    else if(startTimeStrings.length != timeSize) {
                        Toast.makeText(context, "Please fill in all start time fields.", Toast.LENGTH_LONG).show();
                    }
                    else if(endTimeStrings.length != timeSize) {
                        Toast.makeText(context, "Please fill in all end time fields.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            startYear = Integer.parseInt(dateStrings[0]);
                            startMonth = Integer.parseInt(dateStrings[1]);
                            startDay = Integer.parseInt(dateStrings[2]);
                            startHour = Integer.parseInt(startTimeStrings[0]);
                            startMinute = Integer.parseInt(startTimeStrings[1]);
                            endHour = Integer.parseInt(endTimeStrings[0]);
                            endMinute = Integer.parseInt(endTimeStrings[1]);
                            endYear = startYear;
                            endMonth = startMonth;
                            endDay = startDay;
                            if(startYear < 0 ) {
                                Toast.makeText(context, "Year must be positive.", Toast.LENGTH_LONG).show();
                            }
                            else if(startMonth > 12 || startMonth < 1) {
                                Toast.makeText(context, "Please enter a valid month.", Toast.LENGTH_LONG).show();
                            }
                            else if(startDay > 31 || startDay < 1) {
                                Toast.makeText(context, "Please enter a valid day.", Toast.LENGTH_LONG).show();
                            }
                            else if((endHour < startHour) || ((endHour == startHour) && (endMinute < startMinute))) {
                                Toast.makeText(context, "End time must be after start time.", Toast.LENGTH_LONG).show();
                            }
                            else if(endHour > 23 || endHour < 0) {
                                Toast.makeText(context, "End hour must be between 0 and 23.", Toast.LENGTH_LONG).show();
                            }
                            else if(startHour > 23 || startHour < 0) {
                                Toast.makeText(context, "Start hour must be between 0 and 23.", Toast.LENGTH_LONG).show();
                            }
                            else if(endMinute > 59 || endMinute < 0) {
                                Toast.makeText(context, "End minute must be between 0 and 59.", Toast.LENGTH_LONG).show();
                            }
                            else if(startMinute > 59 || startMinute < 0) {
                                Toast.makeText(context, "Start minute must be between 0 and 59.", Toast.LENGTH_LONG).show();
                            }
                            else {
                                if(repeat) {
                                    //do the reapeating
                                    try {
                                        numRepeats = Integer.parseInt(repeatNum.getText().toString());
                                        if(numRepeats < 1) {
                                            Toast.makeText(context, "Please enter valid number of repeat weeks!", Toast.LENGTH_LONG).show();
                                        }
                                        else if(numRepeats > 4)
                                        {
                                            Toast.makeText(context, "Please only repeat events for a month!", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            int noOfDays = 7; //i.e one week

                                            startDate = getDate(startYear, startMonth, startDay, startHour, startMinute);
                                            endDate = getDate(endYear, endMonth, endDay, endHour, endMinute);

                                            DatabaseQuery.pushToDb(new EventObjects(name, startDate, endDate));
                                            Toast.makeText(context, "Event Added!", Toast.LENGTH_LONG).show();

                                            for (int i = 0; i < (numRepeats - 1); i++) {

                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(startDate);
                                                calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                                                Date newStartDate = calendar.getTime();
                                                calendar.setTime(endDate);
                                                calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                                                Date newEndDate = calendar.getTime();

                                                startDate = newStartDate;
                                                endDate = newEndDate;

                                                DatabaseQuery.pushToDb(new EventObjects(name, startDate, endDate));
                                                Toast.makeText(context, "Event Added!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(context, "Please enter valid number of repeat weeks!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    startDate = getDate(startYear, startMonth, startDay, startHour, startMinute);
                                    endDate = getDate(endYear, endMonth, endDay, endHour, endMinute);

                                    DatabaseQuery.pushToDb(new EventObjects(name, startDate, endDate));
                                    Toast.makeText(context, "Event Added!", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, "Please enter date and times in specified format.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    //separate date into separate fields
    public static String[] splitDate(String date){
        String[] strings = date.split("/");
        return strings;
    }

    //separate time into separate fields
    public static String[] splitTime(String time) {
        String[] strings = time.split(":");
        return strings;
    }

    //turn the date/time ints into a java Date
    public static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}