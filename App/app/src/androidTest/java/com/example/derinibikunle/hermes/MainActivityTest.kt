package com.example.derinibikunle.hermes

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HelloWorldEspressoTest {

    @Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.chat_btn)).perform(click())
        /*  A new activity should have opened */
        onView(withId(R.id.textView)).check(matches(withText("Group Chat")))
    }
}