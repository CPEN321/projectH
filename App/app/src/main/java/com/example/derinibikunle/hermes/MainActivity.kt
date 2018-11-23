package com.example.derinibikunle.hermes

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var SIGN_IN_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         if(FirebaseAuth.getInstance().currentUser == null)
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE)

        val chatBtn = findViewById<Button>(R.id.chat_btn)
        chatBtn.setOnClickListener {
            showChat()
        }

        val calendarBtn = findViewById<Button>(R.id.calendar_btn)
            calendarBtn.setOnClickListener {
            showCalendar()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Redirect the user once we get a response
        if(requestCode == SIGN_IN_REQUEST_CODE)
            if (resultCode == Activity.RESULT_OK) showChat()
    }

    private fun showChat() {
        ActivityLauncher.launch(this, GroupChatListActivity::class.java)
    }

    private fun showCalendar(){
        ActivityLauncher.launch(this, CalendarActivity::class.java)
    }



}
