package com.example.derinibikunle.hermes;

import android.content.Context;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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



public class GridAdapter extends ArrayAdapter {


    /* Constants */
    static String GROUP_ID_KEY = "groupId";

    /* Sets up the path pointing to the messages of the group chat */
    static String GROUP_PATH = "";

    static void setGroupPath(String groupId) {
        if(groupId!=null)
            GROUP_PATH = "groups/"+groupId+"calendar_info";
        else
            GROUP_PATH = "users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() +"/calendar_info";
    }



    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<EventObjects> allEvents;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<EventObjects> allEvents) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        final int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
       final int displayMonth = dateCal.get(Calendar.MONTH) + 1;
       final int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(Color.parseColor("#9ff3fc"));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        //Add events to the calendar
        final TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);
       final Calendar eventCalendar = Calendar.getInstance();
//        for(int i = 0; i < allEvents.size(); i++){
//            eventCalendar.setTime(allEvents.get(i).getStartDate());
//            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
//                    && displayYear == eventCalendar.get(Calendar.YEAR)){
//                eventIndicator.setBackgroundColor(Color.parseColor("#000000"));
//            }
//        }

        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference().child(GROUP_PATH);
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<EventObjects> list = new ArrayList<EventObjects>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    list.add(data.getValue(EventObjects.class));
                }
//

               // EventObjects e = new EventObjects();
                for(int i = 0; i < list.size(); i++){
                    eventCalendar.setTime(list.get(i).getStartDate());
                    if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                            && displayYear == eventCalendar.get(Calendar.YEAR)){
                        eventIndicator.setBackgroundColor(Color.parseColor("#000000"));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        return view;

    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}