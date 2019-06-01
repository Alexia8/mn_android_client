package com.alexiacdura.mn_core.data.network.entities

import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class UserDataEntity(
    @SerializedName("posts") val postQuantity: Int,
    @SerializedName("stars") val starredQuantity: Int,
    @SerializedName("followings") val followings: List<UserPost>,
    @SerializedName("followers") val followers: List<UserPost>

) {
    data class UserPost(
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("email") val email: String,
        @SerializedName("imageUrl") val imageUrl: URL?
    )
}