package com.alexiacdura.mn_core.data.models

import java.net.URL

interface UserData {
    val postUser: User
    val postQuantity: Int
    val starredQuantity: Int
    val followings: List<UserFollow>
    val followers: List<UserFollow>

    interface User {
        val id: Int
        val username: String
        val email: String
        val imageUrl: URL?
        val styles: List<Style>

        interface Style {
            val id: Int
            val name: String
        }
    }

    interface UserFollow {
        val id: Int
        val username: String
        val email: String
        val imageUrl: URL?
    }
}