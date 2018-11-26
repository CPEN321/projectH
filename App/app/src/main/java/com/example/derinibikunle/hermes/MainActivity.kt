package com.example.derinibikunle.hermes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AbstractAppActivity() {

    private var SIGN_IN_REQUEST_CODE = 124

    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        val d = FirebaseInstanceId.getInstance()
        d.instanceId.addOnSuccessListener {
            it -> Log.i("myTag", it.token)
        }

        if(FirebaseAuth.getInstance().currentUser == null){
            startActivityForResult(
                    AuthUI
                            .getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE)
        }
        else {
            showChat()
        }

        val chatBtn = findViewById<Button>(R.id.chat_btn)
        chatBtn.setOnClickListener {
            showChat()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Redirect the User once we get a response
        if(requestCode == SIGN_IN_REQUEST_CODE){
            /* Creates a new entry to the database if we have a new user */
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

            /* Collect the result */
            if (resultCode == Activity.RESULT_OK) {
                showChat()
            }
            else {
                val response = IdpResponse.fromResultIntent(data)!!
                retry(response)
            }
        }
    }

    private fun showChat() {
        ActivityLauncher.launch(this, GroupChatListActivity::class.java)
    }

    private fun retry(response: IdpResponse) {
        Toast.makeText(applicationContext,response.error!!.errorCode,Toast.LENGTH_SHORT).show()
    }

}
