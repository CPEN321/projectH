package com.example.derinibikunle.hermes

import android.app.Activity
import android.content.Context
import android.content.Intent

class ActivityLauncher<T> {
    companion object {
        /**
         * Given the context and the activity class, opens the cls's UI
         */
        fun launch(packageContext: Context, cls: Class<*>) {
            val intent = Intent(packageContext, cls)
            packageContext.startActivity(intent)
        }
    }
}