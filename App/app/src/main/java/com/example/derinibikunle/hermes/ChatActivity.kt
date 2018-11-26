package com.example.derinibikunle.hermes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import com.firebase.ui.database.FirebaseListAdapter
import android.widget.TextView
import java.util.*


class ChatActivity : AbstractAppActivity() {

    private companion object {
        /* Constants */
        const val GROUP_ID_KEY = "groupId"

        /* Sets up the path pointing to the messages of the group chat */
        var GROUP_PATH: String = ""

        fun setGroupPath(groupId: String) {
            GROUP_PATH = "groups/$groupId/messages"
        }
    }

    //show the menu with group calendar option
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.group_chat_menu, menu)
        activateIconColor(menu, R.id.action_chats)
        return true
    }


    private var adapter: FirebaseListAdapter<UserMessage>? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Log.i("myTag", intent.toString())
        setGroupPath(intent.getStringExtra(GROUP_ID_KEY))


        val sendMsgBtn = findViewById<FloatingActionButton>(R.id.send_msg_button)
        sendMsgBtn.setOnClickListener {
            /* Assign a date and text to every message sent */
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val chatText = findViewById<EditText>(R.id.chat_input_text)

            /* Update the database with the new message */
            sendMessage(chatText.text.toString(), currentDate)

            /* The User needs to have a clean input field after every event */
            chatText.setText("")
        }

        /* Set up the message list */
        val messageView = findViewById<ListView>(R.id.list_of_messages)
        adapter = createListAdapter()
        messageView.adapter = adapter
    }

    private fun sendMessage(chatText: String, currentDate: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null || currentUser.email == null) {
            Log.e("myTag", "Something went wrong when sending the message... " +
                    "It does not seem like the User is authenticated")
            return /* In this case do no send any query to the database */
        }

        val currMessage = UserMessage(
                currentUser.email!!,
                chatText,
                currentDate
        )

        /* Update the database message list */
        FirebaseDatabase.getInstance()
                .reference
                .child(GROUP_PATH)
                .push()
                .setValue(currMessage)
    }

    private fun createListAdapter(): FirebaseListAdapter<UserMessage> {
        return object : FirebaseListAdapter<UserMessage>(
                    this,
                    UserMessage::class.java,
                    R.layout.item_user_message,
                    FirebaseDatabase.getInstance().reference.child(GROUP_PATH)
            ) {
            override fun populateView(v: View, userMessage: UserMessage, position: Int) {
                // Get references to the views of item_user_message.xml
                val messageText = v.findViewById<TextView>(R.id.message_text)
                val messageUser = v.findViewById<TextView>(R.id.message_user)
                val messageTime = v.findViewById<TextView>(R.id.message_date)

                // Set their text
                messageText.text = userMessage.messageText
                messageUser.text = userMessage.messageUser
                messageTime.text = userMessage.messageDate

                // Log.i("myTag", userMessage.messageUser + " AND " + currentUserEmail)
                if(userMessage.messageUser.equals(AbstractAppActivity.currentUserEmail)) {
                        // Log.i("myTag", "Switching to the right")
                        messageText.gravity = Gravity.END
                        messageUser.gravity = Gravity.RIGHT
                        messageTime.gravity = Gravity.RIGHT
                }
            }
        }
    }
}
