package com.example.derinibikunle.hermes

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var SIGN_IN_REQUEST_CODE = 124

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(FirebaseAuth.getInstance().currentUser != null){
            FirebaseAuth.getInstance().signOut()
        }
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE)

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
        if(requestCode == SIGN_IN_REQUEST_CODE){
            val mDataBase = FirebaseDatabase.getInstance().reference.child("users")
            val loginListener = object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //if the user does not exist add them
                    if(!snapshot.hasChild(FirebaseAuth.getInstance().currentUser?.uid!!)){
                        val newUser = user(FirebaseAuth.getInstance().currentUser?.email!!, FirebaseAuth.getInstance().uid!!)
                        Log.e("BIITCHH", "FUCK")
                        mDataBase.child(newUser.key).setValue(newUser)
                    }

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("mutherfucking", "FUCK")
                }
            }

            mDataBase.addValueEventListener(loginListener)
        }
            if (resultCode == Activity.RESULT_OK) showChat()
    }

    private fun showChat() {
        ActivityLauncher.launch(this, GroupChatListActivity::class.java)
    }

    private fun showCalendar() {
        ActivityLauncher.launch(this, CalendarActivity::class.java)
        val mDataBase = FirebaseDatabase.getInstance().reference.child("users")
        mDataBase.child("Bitch ass hoe").setValue("fuck")
    }



}
