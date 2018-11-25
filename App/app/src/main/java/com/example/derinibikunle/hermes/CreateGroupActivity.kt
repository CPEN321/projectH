package com.example.derinibikunle.hermes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create the group with the current user as the admin
        val newGroup = Groups(FirebaseAuth.getInstance().currentUser?.uid!!)
        setContentView(R.layout.activity_create_group)

        val createGroupBtn = findViewById<Button>(R.id.add_group_button)
        createGroupBtn.setOnClickListener {
           val group_name = group_name_input.text.toString()
            if(group_name != null){
                newGroup.set_group_name(group_name)
                val mDataBase = FirebaseDatabase.getInstance().reference

                val group_key = mDataBase.child("groups").push().key
                mDataBase.child("groups").child(group_key!!).setValue(newGroup)
                //add the admin
                mDataBase.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!).child("group_ids").push().setValue(group_key)
                //add the group to the members
                for(data in newGroup.get_members()){
                    mDataBase.child("users").child(data).child("group_ids").push().setValue(group_key)

                }
            }
            ActivityLauncher.launch(this, GroupChatListActivity::class.java)
        }
        val addUserButton = findViewById<Button>(R.id.add_user_button)
        addUserButton.setOnClickListener {
            val user_name = user_name_input.text.toString()
            val mDataBase = FirebaseDatabase.getInstance().reference.child("users")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        if (data.child("user_id").getValue(String::class.java) == user_name) {
                            val key = data.key
                            newGroup.add_member(key)
                            break
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            }
            mDataBase.addValueEventListener(listener)
            user_name_input.setText("")
        }
    }
}
