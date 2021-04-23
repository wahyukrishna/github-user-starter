package com.dicoding.mygithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserItems>>()

    fun setListUser(user: String?) {
        val url = "https://api.github.com/search/users?q=${user}"
        val list = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                val items = responseObject.getJSONArray("items")
                try {
                    for (i in 0 until items.length()) {
                        val users = items.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.avatar = users.getString("avatar_url")
                        userItems.username = users.getString("login")
                        list.add(userItems)
                    }
                    listUser.postValue(list)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getListUser(): LiveData<ArrayList<UserItems>> {
        return listUser
    }
}