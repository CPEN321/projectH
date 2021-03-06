package com.example.derinibikunle.hermes

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_create_group.*
import java.util.*

class CreateGroupActivity : AppCompatActivity() {
    private val newGroup = Groups(FirebaseAuth.getInstance().currentUser?.uid!!)
    private val eList = LinkedList<String>()

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_group -> {
            val groupName = group_name_input.text.toString()

            newGroup.set_group_name(groupName)
            val mDataBase = FirebaseDatabase.getInstance().reference
            //get the next key to add to the list
            val groupKey = mDataBase.child("groups").push().key
            //add the group to the key
            mDataBase.child("groups").child(groupKey!!).setValue(newGroup)
            //add the key to groups for the admin
            mDataBase.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!).child("group_ids").push().setValue(groupKey)
            //add the key to the groups for all the users
            for(data in newGroup.get_members()){
                mDataBase.child("users").child(data).child("group_ids").push().setValue(groupKey)
            }

            ActivityLauncher.launch(this, GroupChatListActivity::class.java)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.group_add_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create the group with the current user as the admin
        //val newGroup = Groups(FirebaseAuth.getInstance().currentUser?.uid!!)
        setContentView(R.layout.activity_create_group)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val addUserButton = findViewById<Button>(R.id.add_user_button)
        addUserButton.setOnClickListener {
            val username = user_name_input.text.toString().trim()
            eList.add(username)
            //get reference starting from child node
            val mDataBase = FirebaseDatabase.getInstance().reference.child("users")
            //required to read data
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //go through all the users(children of the reference which is the datasnap) and check if the email is the input
                    for (data in snapshot.children) {
                        if (data.child("user_id").getValue(String::class.java) == username) {
                            //get the key to user which has the email input
                            val key = data.key
                            //add the user key to the member list of the group
                            newGroup.add_member(key)
                            val toast = Toast.makeText(this@CreateGroupActivity, "User "+ username +" Added!", Toast.LENGTH_SHORT)
                            val v = toast.view
                            v.setBackgroundColor(Color.rgb(0xff, 0xb2, 0x5f))
                            toast.show()
                            return
                        }
                    }
                    //if you find nothing tell the user the email is invalid
                    val toast = Toast.makeText(this@CreateGroupActivity, "Invalid User Email", Toast.LENGTH_SHORT)
                    val v = toast.view
                    v.setBackgroundColor(Color.rgb(0xff, 0xb2, 0x5f))
                    toast.show()
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            }
            mDataBase.addListenerForSingleValueEvent(listener)
            user_name_input.setText("")
        }

        /* Set the list view items */
        val lv = findViewById<ListView>(R.id.list_view_temp_users)
        lv.adapter = AddUserListAdapter(this, eList)

        lv.onItemClickListener = AdapterView.OnItemClickListener {
            adapterView, view, position, id ->
            run {
                Log.i("myTag", "Opening group chat at position $position")
                eList.removeAt(position)
            }
        }

    }
}
