package com.example.derinibikunle.hermes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AddUserListAdapter extends ArrayAdapter<String> {

    public AddUserListAdapter(Context context, List<String> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String prev = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_adding_user, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.user_email)).setText(prev);

        return convertView;
    }
}
