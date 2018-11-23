package com.example.derinibikunle.hermes;
import java.util.ArrayList;
import java.util.List;

class groups{
    public List<UserMessage> messages;
    public List<String> members;
    public List<EventObjects> calendar_info;
    public String name;


    groups(String name){
        this.name = name;
        this.messages = new ArrayList<UserMessage>();
        this.members = new ArrayList<String>();
        this.calendar_info = new ArrayList<EventObjects>();
    }
}