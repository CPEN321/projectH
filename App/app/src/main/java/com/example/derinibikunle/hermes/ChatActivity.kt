package com.example.derinibikunle.hermes

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import com.firebase.ui.database.FirebaseListAdapter
import android.widget.TextView
import java.util.*


class ChatActivity : AppCompatActivity() {

    private var adapter: FirebaseListAdapter<UserMessage>? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val messageView = findViewById<ListView>(R.id.list_of_messages)

        /* Every time the send message button gets pressed it adds it to the database */
        val chatText = findViewById<EditText>(R.id.chat_input_text)
        val sendMsgBtn = findViewById<FloatingActionButton>(R.id.send_msg_button)
        sendMsgBtn.setOnClickListener {
            sendMessage(chatText.text.toString(), currentDate)

            // The user needs to have a clean input field
            chatText.setText("")
        }

        adapter = createListAdapter()
        messageView.adapter = adapter
    }

    private fun sendMessage(chatText: String, currentDate: String) {
        val currMessage = UserMessage()
        currMessage.messageUser = FirebaseAuth.getInstance().currentUser?.email!!
        currMessage.messageText = chatText
        currMessage.messageDate = currentDate

        FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(currMessage)
    }

    private fun createListAdapter(): FirebaseListAdapter<UserMessage> {
        return object : FirebaseListAdapter<UserMessage>(this, UserMessage::class.java,
                R.layout.item_user_message, FirebaseDatabase.getInstance().reference) {
            override fun populateView(v: View, userMessage: UserMessage, position: Int) {
                // Get references to the views of item_user_message.xml
                val messageText = v.findViewById(R.id.message_text) as TextView
                val messageUser = v.findViewById(R.id.message_user) as TextView
                val messageTime = v.findViewById(R.id.message_date) as TextView

                // Set their text
                messageText.text = userMessage.messageText
                messageUser.text = userMessage.messageUser
                messageTime.text = userMessage.messageDate
            }
        }
    }
}
