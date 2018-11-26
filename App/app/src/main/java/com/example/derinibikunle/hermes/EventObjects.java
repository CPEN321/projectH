package com.example.derinibikunle.hermes;

import java.util.Date;

public class EventObjects {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;

    public EventObjects() {
        this.name = "";
        this.startDate = new Date();
        this.endDate = new Date ();
        this.id = 0;
    }
    public EventObjects(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public EventObjects(int id, String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }


}