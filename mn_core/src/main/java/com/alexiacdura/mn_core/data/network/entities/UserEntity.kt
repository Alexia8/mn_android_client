package com.alexiacdura.mn_core.data.network.entities

import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class UserEntity(
    @SerializedName("id") val userId: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("imageUrl") val imageUrl: URL?,
    @SerializedName("likedStyles") val likedStyles: List<Style>
) {
    data class Style(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String
    )
}