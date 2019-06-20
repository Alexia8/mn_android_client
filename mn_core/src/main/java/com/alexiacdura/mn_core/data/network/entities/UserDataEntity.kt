package com.alexiacdura.mn_core.data.network.entities

import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class UserDataEntity(
    @SerializedName("user") val userPost: User,
    @SerializedName("posts") val postQuantity: Int,
    @SerializedName("stars") val starredQuantity: Int,
    @SerializedName("followings") val followings: List<UserPost>,
    @SerializedName("followers") val followers: List<UserPost>

) {
    data class User(
        @SerializedName("id") val userId: Int,
        @SerializedName("username") val username: String,
        @SerializedName("email") val email: String,
        @SerializedName("imageUrl") val imageUrl: URL?,
        @SerializedName("likedStyles") val styles: List<Style>
    ) {
        data class Style(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String
        )
    }

    data class UserPost(
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("email") val email: String,
        @SerializedName("imageUrl") val imageUrl: URL?
    )
}