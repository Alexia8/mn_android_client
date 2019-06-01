package com.alexiacdura.mn_core.data.network.entities

import com.google.gson.annotations.SerializedName

internal data class VoteResponseEntity(
    @SerializedName("id") val idVote: Int,
    @SerializedName("upvote") val upvote: Int,
    @SerializedName("date") val dateVote: Int,
    @SerializedName("userId") val userIdVote: Int,
    @SerializedName("feedPostId") val feedPostIdVote: Int
)