package com.alexiacdura.mn_core.data.network.entities.insert

data class Vote(
    val upvote: Boolean,
    val feedPostId: Int,
    val userId: Int
)