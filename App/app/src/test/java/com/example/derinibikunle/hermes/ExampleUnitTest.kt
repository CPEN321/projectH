package com.example.derinibikunle.hermes

import org.junit.Test


import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun userMessageTest1(){
       val message =  UserMessage("primo", "(====3", "042019")

        assertEquals("primo", message.getUser())
        assertEquals("(====3", message.getText())
        assertEquals("042019", message.getDate())

        message.setUser("def not primo")
        message.setDate("051219")
        message.setText("Seen")

        assertEquals("def not primo", message.getUser())
        assertEquals("Seen", message.getText())
        assertEquals("051219", message.getDate())

    }
}
