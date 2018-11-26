package com.example.derinibikunle.hermes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater



abstract class AbstractAppActivity: AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }

        R.id.action_calendar -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            ActivityLauncher.launch(this,CustomCalendarActivity::class.java)
            true
        }

        R.id.action_chats -> {
            ActivityLauncher.launch(this,GroupChatListActivity::class.java)
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
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


}