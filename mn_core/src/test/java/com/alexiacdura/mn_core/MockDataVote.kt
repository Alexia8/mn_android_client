package com.alexiacdura.mn_core

import com.alexiacdura.mn_core.data.models.VoteResponse

@Suppress("MaxLineLength", "MagicNumber")
fun getTestVoteResponse(): VoteResponse {
    return object : VoteResponse {
        override val id = 4
        override val upvote = 1
        override val dateVote = 1559427987
        override val userIdVote = 2
        override val feedPostIdVote = 1
    }
}