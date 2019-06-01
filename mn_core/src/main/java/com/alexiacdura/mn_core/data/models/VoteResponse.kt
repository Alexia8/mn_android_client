package com.alexiacdura.mn_core.data.models

interface VoteResponse {
    val id: Int
    val upvote: Int
    val dateVote: Int
    val feedPostIdVote: Int
    val userIdVote: Int
}