package com.example.derinibikunle.hermes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Groups {
    public List<UserMessage> messages;
    public List<String> members;
    public List<EventObjects> calendar_info;
    public String name;
    public String admin;
    public String preview;


    Groups(String admin_id){
        // What appears to users as the group name
        this.name = "";
        List<UserMessage> l = new ArrayList<UserMessage>();
        UserMessage u = new UserMessage("Hermes", "Welcome to your new Group", (new Date()).toString());
        l.add(u);
        this.messages = l;
        //firebase ids for the users
        this.members = new ArrayList<String>();
        this.calendar_info = new ArrayList<EventObjects>();
        //firebase id for group creator
        this.admin = admin_id;
        this.preview = "Welcome to your new group!";
    }
    //make firebase happy
    Groups(){
        // What appears to users as the group name
        this.name = "";
        this.messages = new ArrayList<UserMessage>();
        //firebase ids for the users
        this.members = new ArrayList<String>();
        this.calendar_info = new ArrayList<EventObjects>();
        //firebase id for group creator
        this.admin = "";
        this.preview = "";
    }

    //name should be the users firebase id, will have to get it from their name that they put in
    void add_member(String name){
        this.members.add(name);
    }

    void set_group_name(String name){
        this.name = name;
    }

    List<String> get_members(){
        return this.members;
    }
}