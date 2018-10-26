package com.example.derinibikunle.hermes

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
import com.firebase.ui.auth.data.model.User
import com.google.firebase.database.DataSnapshot
import java.util.*


class ChatActivity : AppCompatActivity() {

    private var adapter: FirebaseListAdapter<UserMessage>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val messageView = findViewById(R.id.list_of_messages) as ListView

        val chatText = findViewById(R.id.chat_input_text) as EditText

        val sendMsgBtn = findViewById(R.id.send_msg_button) as FloatingActionButton

        val currMessage = UserMessage()
        sendMsgBtn.setOnClickListener {
            sendMessage(currMessage, chatText, currentDate)
        }

        adapter = createListAdapter()
        messageView.adapter = adapter
    }

    private fun sendMessage(currMessage: UserMessage, chatText: EditText, currentDate: String) {
        currMessage.messageUser = FirebaseAuth.getInstance().currentUser?.email!!
        currMessage.messageText = chatText.text.toString()
        currMessage.messageDate = currentDate

        FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(currMessage)
        chatText.setText("")
    }

    private fun createListAdapter(): FirebaseListAdapter<UserMessage> {
        return object : FirebaseListAdapter<UserMessage>(this, UserMessage::class.java,
                R.layout.activity_user_message, FirebaseDatabase.getInstance().reference) {
            override fun populateView(v: View, userMessage: UserMessage, position: Int) {
                // Get references to the views of message.xml
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
