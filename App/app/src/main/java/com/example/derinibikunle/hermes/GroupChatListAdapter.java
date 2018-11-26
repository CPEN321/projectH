package com.example.derinibikunle.hermes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GroupChatListAdapter extends ArrayAdapter<GroupChatPreview> {

    public GroupChatListAdapter(Context context, List<GroupChatPreview> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        GroupChatPreview prev = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group_chat_preview, parent, false);
        }

        TextView groupNameView = convertView.findViewById(R.id.group_name);
        TextView groupMsgView = convertView.findViewById(R.id.group_msg_preview);
        TextView groupIdView = convertView.findViewById(R.id.group_id);

        groupNameView.setText(prev.getName());
        groupMsgView.setText(prev.getPreview());
        groupIdView.setText(prev.getId());

        return convertView;
    }
}
