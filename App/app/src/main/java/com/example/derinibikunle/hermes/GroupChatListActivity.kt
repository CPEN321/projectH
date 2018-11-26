package com.example.derinibikunle.hermes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseListAdapter



class GroupChatListActivity : AbstractAppActivity() {

    companion object {
        const val GROUP_REF:String = "groups"
    }

    private var adapter: FirebaseListAdapter<Groups>? = null
//    private var storageRef: StorageReference

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

    private fun createListAdapter(): FirebaseListAdapter<Groups> {
        return object : FirebaseListAdapter<Groups>(this, Groups::class.java,
                R.layout.item_group_chat_preview, FirebaseDatabase.getInstance().reference.child("groups")) {

            override fun populateView(v: View, group: Groups, position: Int) {
                // Get reference to the elements in GroupChatPreview.xml
                val nameComponent = v.findViewById<TextView>(R.id.group_name)
                val msgPreviewComponent = v.findViewById<TextView>(R.id.group_msg_preview)
                val iconComponent = v.findViewById<ImageView>(R.id.group_icon)

                nameComponent.text = group.name
                msgPreviewComponent.text = group
                                            .messages
                                            .last()
                                            .messageUser

                // TODO we should store the icon in the cloud storage
//                val url = URL(groupPreview.groupIconPath)
//                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//                iconComponent.setImageBitmap(bmp)
            }
        }
    }
}
