package com.alexiacdura.mn_core.data.network.entities

import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class StarResponseEntity(
    @SerializedName("id") val idStar: Int,
    @SerializedName("date") val dateStar: Int,
    @SerializedName("feedPostId") val feedPostIdStar: Int,
    @SerializedName("userId") val userIdStar: Int
)