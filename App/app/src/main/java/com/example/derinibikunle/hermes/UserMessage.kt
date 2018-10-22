package com.example.derinibikunle.hermes

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UserMessage(){
    var messageUser: String = ""
    var messageText: String = ""
    var messageDate: String = ""

    fun getUser(): String{
        return messageUser
    }

    fun setUser(user: String){
        this.messageUser = user
    }

    fun getText(): String{
        return messageText
    }

    fun setText(text: String){
        this.messageText = text
    }

    fun getDate(): String{
        return messageDate
    }

    fun setDate(date: String){
        this.messageDate = date
    }
}