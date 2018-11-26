package com.example.derinibikunle.hermes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.derinibikunle.hermes.DatabaseQuery.getuid;

public class AdapterEventObjects extends ArrayAdapter<EventObjects> {
    private Activity activity;
    private ArrayList<EventObjects> lEvents;
    private static LayoutInflater inflater = null;
    private Context context = this.getContext();

    public AdapterEventObjects (Activity activity, int textViewResourceId, ArrayList<EventObjects> _lEvents) {
        super(activity, textViewResourceId, _lEvents);
        try {
            this.activity = activity;
            this.lEvents = _lEvents;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lEvents.size();
    }

//    public Product getItem(Product position) {
//        return position;
//    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_title;
        public TextView display_start_date;
        public TextView display_end_date;
        public Button delete_button;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.activity_event_objects, null);
                holder = new ViewHolder();

                holder.display_title = (TextView) vi.findViewById(R.id.event_title);
                holder.display_start_date = (TextView) vi.findViewById(R.id.event_start_date);
                holder.display_end_date = (TextView) vi.findViewById(R.id.event_end_date);
                holder.delete_button = (Button) vi.findViewById(R.id.delete_button);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_title.setText(lEvents.get(position).getName());
            holder.display_start_date.setText(lEvents.get(position).getStartDate().toString());
            holder.display_end_date.setText(lEvents.get(position).getEndDate().toString());

            Toast.makeText(context, "numberrrr is: " + position, Toast.LENGTH_LONG).show();

            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    DatabaseQuery.removeFromDb(lEvents.get(position));
//                    lEvents.remove(position);


                    final DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference().child("users").child(getuid()).child("calendar_info");
                    mDataBase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<EventObjects> list = new ArrayList<EventObjects>();
                            for(DataSnapshot data : dataSnapshot.getChildren()) {
                                if (lEvents.get(position).getName().equals(data.getValue(EventObjects.class).getName()) &&
                                lEvents.get(position).getStartDate().equals(data.getValue(EventObjects.class).getStartDate()) &&
                                lEvents.get(position).getEndDate().equals(data.getValue(EventObjects.class).getEndDate())) {
                                    Toast.makeText(context, "Position is: " + position, Toast.LENGTH_LONG).show();
                                    String key = data.getKey();
                                    mDataBase.child(key).removeValue();
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    Toast.makeText(context, "Event deleted!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(context, CustomCalendarActivity.class);
                    context.startActivity(intent);
                }
            });


        } catch (Exception e) {


        }
        return vi;
    }
}