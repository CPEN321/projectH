package com.example.derinibikunle.hermes

class GroupChatPreview {
    var groupName: String = ""
    var groupMsgPreview: String = ""
    var groupIconPath: String = "" /* TODO Whatever this is supposed to be */

    constructor(groupName:String, groupMsgPreview:String, groupIconPath:String) {
        this.groupName = groupName
        this.groupMsgPreview = groupMsgPreview
        this.groupIconPath = groupIconPath
    }

    // FirebaseListAdapter needs an empty constructor
    constructor()

}