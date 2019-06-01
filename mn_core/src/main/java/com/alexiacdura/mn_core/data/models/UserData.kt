package com.alexiacdura.mn_core.data.models

import java.net.URL

interface UserData {
    val postQuantity: Int
    val starredQuantity: Int
    val followings: List<User>
    val followers: List<User>

    interface User {
        val id: Int
        val username: String
        val email: String
        val imageUrl: URL?
    }
}