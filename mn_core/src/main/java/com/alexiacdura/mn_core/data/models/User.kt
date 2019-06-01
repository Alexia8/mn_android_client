package com.alexiacdura.mn_core.data.models

import java.net.URL

interface User {
    val id: Int
    val username: String
    val email: String
    val imageUrl: URL?
    val likedStyles: List<Style>

    interface Style {
        val id: Int
        val name: String
    }
}