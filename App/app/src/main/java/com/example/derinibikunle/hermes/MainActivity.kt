package com.example.derinibikunle.hermes

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        else
            // Start the chat activity
            showDashboard();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        // Redirect the user once we get a response
        if(requestCode == SIGN_IN_REQUEST_CODE)
            if (resultCode == Activity.RESULT_OK) showDashboard()
    }

    private fun showDashboard() {
        //val intent = Intent(this, ChatActivity::class.java)
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }



}
