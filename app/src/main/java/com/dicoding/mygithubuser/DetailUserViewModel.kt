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

class DetailUserViewModel : ViewModel() {
    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }

    val userDetail = MutableLiveData<UserItems>()
    fun setUserDetail(user: String?) {
        val url = "https://api.github.com/users/${user}"
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
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val userItems = UserItems()
                    userItems.avatar = responseObject.getString("avatar_url")
                    userItems.name = responseObject.getString("name")
                    userItems.location = responseObject.getString("location")
                    userItems.company = responseObject.getString("company")
                    userItems.repo = responseObject.getInt("public_repos")
                    userDetail.postValue(userItems)
                } catch (e: Exception) {
                    e.printStackTrace()
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
    fun getUserDetail(): LiveData<UserItems> {
        return userDetail
    }
}