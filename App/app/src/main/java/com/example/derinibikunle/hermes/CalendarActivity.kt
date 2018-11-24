package com.example.derinibikunle.hermes
//package kotlin.collections

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.time.Month
import java.time.Year

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val msg =
                    "You have no events on: " + dayOfMonth + "/" + (month + 1) + "/" + year

            Toast.makeText(this@CalendarActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
