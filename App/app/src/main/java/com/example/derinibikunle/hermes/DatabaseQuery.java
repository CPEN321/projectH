package com.example.derinibikunle.hermes;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseQuery {
    //in here we query firebase for events

    public List<EventObjects> getAllFutureEvents() {
        //this is just a stub - should actually get all events from db here
        //EventObjects event = new EventObjects("TestEvent", new Date(), new Date());
//        final java.util.ArrayList<EventObjects> list = new java.util.ArrayList<EventObjects>();
//        list.add(event);
        final List<EventObjects> list = new ArrayList<EventObjects>();

        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference().child("users").child(getuid()).child("calendar_info");
//        mDataBase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot data : dataSnapshot.getChildren()){
//                   list.add(data.getValue(EventObjects.class));
////                   DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("users");
////                   d.push().setValue(data.getValue(EventObjects.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return list;
    }

    public static String getuid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static void pushToDb(EventObjects event) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child(getuid()).child("calendar_info").push().setValue(event);
    }

    public static void removeFromDb(final EventObjects event) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(getuid()).child("calendar_info");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getValue(EventObjects.class) == event){
                        String key =  data.getKey();
                        mDatabase.child(key).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}