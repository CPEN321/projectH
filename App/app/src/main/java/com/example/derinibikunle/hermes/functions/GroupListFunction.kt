package com.example.derinibikunle.hermes.functions

import android.os.AsyncTask
import android.util.Log
import com.example.derinibikunle.hermes.GroupChatPreview
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class GroupListFunction {

    companion object {
        const val FUNCTION_URL: String = "https://us-central1-teamh-90491.cloudfunctions.net/getChats"
        const val FUNCTION_GROUP: String = "https://us-central1-teamh-90491.cloudfunctions.net/getGroupInfo"

        /* Companion helper class to get the list of group ids */
        class ChatDataTask : AsyncTask<String, String, List<String>>() {
            override fun doInBackground(vararg myURL: String?): List<String> {

                val result: LinkedList<String> = LinkedList()
                val resp: String
                val connection = URL(myURL[0]).openConnection() as HttpURLConnection

                /* Get the response in JSONArray format */
                resp = try {
                    connection.connect()
                    connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
                } catch(err: Exception) {
                    Log.e("functionError", "Could not retrieve Group ids from FireBase function")
                    "[]"
                } finally {
                    connection.disconnect()
                }

                val jsonResp = JSONArray(resp)
                for(i in 0 until jsonResp.length()) result.push(jsonResp.getString(i))
                return result
            }
        }

        /* */
        class GroupDataTask : AsyncTask<String, String, GroupChatPreview>() {
            override fun doInBackground(vararg myURL: String?): GroupChatPreview {
                val connection = URL(myURL[0]).openConnection() as HttpURLConnection

                val resp: String =
                try {
                    connection.connect()
                    connection.inputStream.use { it.reader().use { reader -> reader.readText() } }

                }
                catch (err:Exception) {
                    Log.e("functionError", "Could not retrieve Chat data from FireBase function")
                    "{}"
                }
                finally{
                    connection.disconnect()
                }

                val jsonResp = JSONObject(resp)
                val f = GroupChatPreview(jsonResp)
                return f
            }
        }
    }

    fun getGroupPreviews(userId: String):List<GroupChatPreview> {
        val groupIds = getUserChats(userId)
        val f = groupIds.map { it ->
            getGroupPreview(it)
        }
        return f

    }

    private fun getUserChats(id: String):List<String>{
        val chatUrl = "$FUNCTION_URL?id=$id"

        val task: AsyncTask<String, String, List<String>> = ChatDataTask()
        task.execute(chatUrl)

        /* Wait until the request is completed and collect the result */

        return try {
            task.get()
        }
        catch(err:Exception) {
            LinkedList()
        }
    }

    private fun getGroupPreview(id: String) : GroupChatPreview{
        val groupUrl = "$FUNCTION_GROUP?id=$id"

        val task = GroupDataTask()
        task.execute(groupUrl)
        Log.i("myTag", id)

        val f = try {
            task.get()
        }
        catch(err:Exception) {
            GroupChatPreview()
        }

        return f
    }


}