package com.example.derinibikunle.hermes;
import java.util.ArrayList;
import java.util.List;

class user{
    //username is the name stored in the database, user_id is the email
    public String username;
    public String user_id;
    public List<String> group_ids;
    public List<EventObjects> calendar_info;
    public String key;

    //for firebase?
    user(){ }
    user(String user_id, String key){
        this.user_id = user_id;
        this.username = "";
        int i = 0;
        while(user_id.charAt(i)!='@' && i < user_id.length()){
            if(user_id.charAt(i) != '.') {
                username = username + user_id.charAt(i);
                i++;
            }
        }
        this.group_ids = new ArrayList<String>();
        this.calendar_info = new ArrayList<EventObjects>();
        this.key= key;
    }
//    user(String user_id){
//        this.user_id = user_id;
//        this.username = "";
//        int i = 0;
//        while(user_id.charAt(i)!='@' && i < user_id.length()){
//            username = username + user_id.charAt(i);
//            i++;
//        }
//        this.group_ids = new ArrayList<String>();
//        this.calendar_info = new ArrayList<EventObjects>();
//    }

    String getUserId(){
        return this.user_id;
    }
    List<String> getGroupIds(){
        return this . group_ids;
    }
    List<EventObjects> getCalendarInfo(){
        return this.calendar_info;
    }
    String getUsername(){
        return this.username;
    }
    void addGroupId(String Id){
        this.group_ids.add(Id);
    }



}