package com.example.derinibikunle.hermes

class UserMessage {
    var messageUser: String = ""
    var messageText: String = ""
    var messageDate: String = ""

    constructor(messageUser: String, messageText: String, messageDate: String){
        this.messageUser = messageUser
        this.messageText = messageText
        this.messageDate = messageDate
    }

    // Make Firebase Happy
    constructor()

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