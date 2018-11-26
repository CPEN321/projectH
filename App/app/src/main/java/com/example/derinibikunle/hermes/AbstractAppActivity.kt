package com.example.derinibikunle.hermes

import android.app.Activity
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


abstract class AbstractAppActivity: AppCompatActivity() {

    protected var currentUserEmail : String? = try {
            FirebaseAuth.getInstance().currentUser?.email
    } catch(e:Error) {
        ""
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                ActivityLauncher.launch(this, MainActivity::class.java)
            }
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

    /* To be called by the child to activate the right icon */
    protected fun activateIconColor(menu: Menu, res: Int) {
        val menuItem: MenuItem = menu.findItem(res)
        val drawable = menuItem.icon
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate()
            drawable.setColorFilter(resources.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP)
            drawable.alpha = 255
        }
    }


}