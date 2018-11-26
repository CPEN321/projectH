package com.example.derinibikunle.hermes

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import com.example.derinibikunle.hermes.functions.GroupListFunction
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseListAdapter


class GroupChatListActivity : AbstractAppActivity() {

    companion object {
        const val GROUP_REF:String = "groups"
    }

    private var adapter: ArrayAdapter<GroupChatPreview>? = null
//    private var storageRef: StorageReference

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        activateIconColor(menu, R.id.action_chats)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat_list)
        setupAddGrpButton()
        setupGroupListView()
    }

    /*
        HELPER FUNCTIONS FOR SETTING UP THE VIEWS
     */

    private fun setupAddGrpButton() {
        val addGroupBtn = findViewById<ImageButton>(R.id.button_add_group)
        addGroupBtn.setOnClickListener {
            launchCreateGroupActivity()
        }
    }

    private fun setupGroupListView() {
        val groupListView = findViewById<ListView>(R.id.list_of_groups)
        adapter = createListAdapter()
        groupListView.adapter = adapter

        groupListView.onItemClickListener = AdapterView.OnItemClickListener {
            /* TODO find out what to send to ChatActivity */
            adapterView, view, position, id ->
            run {
                Log.i("myTag", "Opening group chat at position $position")
                ActivityLauncher
                        .addParam("groupId", "1")
                        .launch(this, ChatActivity::class.java)
            }
        }
    }

    private fun launchCreateGroupActivity() {
        ActivityLauncher.launch(this, CreateGroupActivity::class.java)
    }

    private fun createListAdapter(): ArrayAdapter<GroupChatPreview> {


        val f = if(AbstractAppActivity.currentUserId == null) {
            Log.e("myTag", "User is not authenticated... It should not get to GroupChatListActivity")
            GroupListFunction().getGroupPreviews("")
        }
        else GroupListFunction().getGroupPreviews(AbstractAppActivity.currentUserId!!)

        Log.i("myTag", f.toString())

        return GroupChatListAdapter(this, f)

    }
}
