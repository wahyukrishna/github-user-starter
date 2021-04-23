package com.dicoding.mygithubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItems(
    var avatar: String? = null,
    var username: String? = null,
    var name: String? = null,
    var location: String? = null,
    var company: String? = null,
    var repo: Int = 0
) : Parcelable
