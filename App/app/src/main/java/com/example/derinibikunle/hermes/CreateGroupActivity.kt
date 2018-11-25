package com.example.derinibikunle.hermes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create the group with the current user as the admin
        val newGroup = groups(FirebaseAuth.getInstance().currentUser?.uid!!)
        setContentView(R.layout.activity_create_group)

        val createGroupBtn = findViewById<Button>(R.id.add_group_button)
        createGroupBtn.setOnClickListener {
           val group_name = group_name_input.text.toString()
            if(group_name != null){
                newGroup.set_group_name(group_name)
                val mDataBase = FirebaseDatabase.getInstance().reference

                val group_key = mDataBase.child("groups").push().key
                mDataBase.child("groups").child(group_key!!).setValue(newGroup)


                Log.e("the fucking key", group_key)
                mDataBase.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!).child("group_ids").push().setValue(group_key)
            }


        }
        val addUserButton = findViewById<Button>(R.id.add_user_button)
        addUserButton.setOnClickListener{
           // val mDataBase = FirebaseDatabase.getInstance().reference.child("users")

            val user_name = user_name_input.text.toString()
            newGroup.add_member(user_name)
//            val userListener = object: ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    //go through and find the user id, ad it to user list
//                   for(data in snapshot.children){
//                       if(data.hasChild(user_name))
//                       {
//                          val user_id = data.child("key").getValue()
//                           //newGroup.add_member(user_id.toString())
//                       }
//
//                   }
//
//                }
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//            }
//            mDataBase.addValueEventListener(userListener)

        }
    }
}
