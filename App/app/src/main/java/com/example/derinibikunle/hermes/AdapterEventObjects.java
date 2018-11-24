package com.example.derinibikunle.hermes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import android.content.Context;

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

    public View getView(int position, View convertView, ViewGroup parent) {
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


            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DeleteEventActivity.class);
                    context.startActivity(intent);
                }
            });


        } catch (Exception e) {


        }
        return vi;
    }
}