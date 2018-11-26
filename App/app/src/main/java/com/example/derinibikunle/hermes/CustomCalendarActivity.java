package com.example.derinibikunle.hermes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.derinibikunle.hermes.DatabaseQuery;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;


public class CustomCalendarActivity extends AbstractAppActivity {
    private static final String TAG = CustomCalendarActivity.class.getSimpleName();
    private Button previousButton, nextButton;
    private TextView thisDate;
    private GridView calendarGridView;
    private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context = this;
    private GridAdapter mAdapter;
    private DatabaseQuery mQuery;
    public static List<Date> dayValueInCells;

    /* Constants */
    static String GROUP_ID_KEY = "groupId";

    /* Sets up the path pointing to the messages of the group chat */
    static String GROUP_PATH = "";

    static void setGroupPath(String groupId) {
        if(groupId != null)
            GROUP_PATH = "groups/"+groupId+"/calendar_info";
        else
            GROUP_PATH = "users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() +"/calendar_info";
    }

    static String GROUP_ID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        activateIconColor(menu, R.id.action_calendar);
        GROUP_ID = getIntent().getStringExtra(GROUP_ID_KEY);
        setGroupPath(GROUP_ID);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_calendar);
        GROUP_ID = getIntent().getStringExtra(GROUP_ID_KEY);
        setGroupPath(GROUP_ID);

        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setAddEventButtonClickEvent();
        setGridCellClickEvents();
    }

    private void initializeUILayout() {
        previousButton = (Button) findViewById(R.id.previous_month);
        nextButton = (Button) findViewById(R.id.next_month);
        thisDate = (TextView) findViewById(R.id.display_current_date);
        addEventButton = (Button) findViewById(R.id.add_calendar_event);
        calendarGridView = (GridView) findViewById(R.id.calendar_grid);
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setAddEventButtonClickEvent() {
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("groupId", GROUP_ID);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    private void setGridCellClickEvents() {
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //takes you to events page showing all your events on this day
                //with the delete events button to delete certain events
                Intent intent = new Intent(context, ViewEventsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("groupId", GROUP_ID);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    private void setUpCalendarAdapter() {
        dayValueInCells = new ArrayList<Date>();
        mQuery = new DatabaseQuery();
        List<EventObjects> mEvents = mQuery.getAllFutureEvents();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        thisDate.setText(sDate);
        mAdapter = new GridAdapter(context, dayValueInCells, cal, mEvents, GROUP_PATH);
        calendarGridView.setAdapter(mAdapter);
    }
}