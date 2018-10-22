package com.example.derinibikunle.hermes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val chatText = findViewById(R.id.chat_input_text) as EditText

        val sendMsgBtn = findViewById(R.id.send_msg_button) as FloatingActionButton
        sendMsgBtn.setOnClickListener {
            FirebaseDatabase.getInstance()
                    .reference
                    .push()
                    .setValue(UserMessage(FirebaseAuth.getInstance().currentUser.toString(),
                            chatText.getText().toString(), currentDate)
                    )
            chatText.setText("")
        }
    }
}
