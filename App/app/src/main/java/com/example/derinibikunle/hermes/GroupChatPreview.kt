package com.example.derinibikunle.hermes

import android.util.Log
import org.json.JSONObject

class GroupChatPreview {

    companion object {
        const val JSON_NAME_KEY = "groupName"
        const val JSON_PREV_KEY = "groupMsgPreview"
        const val JSON_ID_KEY = "groupId"
    }

     var groupName: String = ""
     var groupMsgPreview: String = ""
     var groupIconPath: String = "" /* TODO Whatever this is supposed to be */
     var groupId: String = ""

    constructor(groupName:String, groupMsgPreview:String, groupIconPath:String, groupId: String) {
        this.groupName = groupName
        this.groupMsgPreview = groupMsgPreview
        this.groupIconPath = groupIconPath
        this.groupId = groupId
    }

    // FirebaseListAdapter needs an empty constructor
    constructor()

    constructor(json: JSONObject) {
        try {
            this.groupName= json.getString(JSON_NAME_KEY)
            this.groupMsgPreview = json.getString(JSON_PREV_KEY)
            this.groupIconPath = ""
            this.groupId = json.getString(JSON_ID_KEY)
        }
        catch(err:Exception) {
            Log.e("myTag", "Could not parse JSONNNNN")
        }
    }

    override fun toString():String {
        return "[groupName:$groupName, groupMsgPreview:$groupMsgPreview, groupId:$groupId]"
    }

    fun getName():String {
        return this.groupName
    }

    fun getId():String {
        return this.groupId
    }

    fun getPreview(): String {
        return this.groupMsgPreview
    }


}