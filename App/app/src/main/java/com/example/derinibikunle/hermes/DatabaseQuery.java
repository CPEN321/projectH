package com.example.derinibikunle.hermes;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseQuery {
    //in here we query firebase for events

    public java.util.ArrayList<EventObjects> getAllFutureEvents() {
        EventObjects event = new EventObjects("TestEvent", new Date(), new Date());
        java.util.ArrayList<EventObjects> list = new java.util.ArrayList<EventObjects>();
        list.add(event);

        return list;
    }

    public static void pushToDb(EventObjects event) {
        //do the firebase adding stuff
    }

    public static void removeFromDb(EventObjects event) {
        //do the firebase removal stuff
    }


}