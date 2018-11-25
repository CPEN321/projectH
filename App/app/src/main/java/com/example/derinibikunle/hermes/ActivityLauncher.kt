package com.example.derinibikunle.hermes

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.*

class ActivityLauncher {
    companion object {
        private val params = ArrayDeque<Pair<String,String>>()
        /**
         * Given the context and the activity class, opens the cls's UI
         */
        fun launch(packageContext: Context, cls: Class<*>) {
            val intent = Intent(packageContext, cls)
            for(param in params)
                intent.putExtra(param.first, param.second)
            packageContext.startActivity(intent)
        }

        fun addParam(key: String, value: String):ActivityLauncher.Companion {
            params.push(Pair(key,value))
            return this
        }
    }
}