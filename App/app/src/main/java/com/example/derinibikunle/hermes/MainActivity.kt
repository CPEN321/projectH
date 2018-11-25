package com.example.derinibikunle.hermes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.functions.FirebaseFunctions

class MainActivity : AbstractAppActivity() {

    private var SIGN_IN_REQUEST_CODE = 124

    private lateinit var functions: FirebaseFunctions

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

        val settingsBtn = findViewById(R.id.settings_button) as Button
        settingsBtn.setOnClickListener {
            textFun()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Redirect the User once we get a response
        if(requestCode == SIGN_IN_REQUEST_CODE){
            val mDataBase = FirebaseDatabase.getInstance().reference.child("users")
            val loginListener = object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //if the User does not exist add them
                    if(!snapshot.hasChild(FirebaseAuth.getInstance().currentUser?.uid!!)){
                        val newUser = User(FirebaseAuth.getInstance().currentUser?.email!!, FirebaseAuth.getInstance().currentUser?.uid!!)
                        mDataBase.child(newUser.key).setValue(newUser)
                    }

                }
                override fun onCancelled(p0: DatabaseError) {

                }
            }

            mDataBase.addValueEventListener(loginListener)
        }
            //if (resultCode == Activity.RESULT_OK) showChat()
    }

    private fun showChat() {
        ActivityLauncher.launch(this, GroupChatListActivity::class.java)
    }

    private fun showCalendar() {
        ActivityLauncher.launch(this, CalendarActivity::class.java)


    }

    private fun textFun(){
        val testStr = "9GRUYZD3a7Ogvt3zW1RZpzaNOt03"
        getChats(testStr)
    }

    private fun getChats(id: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
                "id" to id,
                "push" to true
        )

        return functions
                .getHttpsCallable("getChats")
                .call(data)
                .continueWith { task ->
                    // This continuation runs on either success or failure, but if the task
                    // has failed then result will throw an Exception which will be
                    // propagated down.
                    val result = task.result?.data as String
                    result
                }
    }

}
