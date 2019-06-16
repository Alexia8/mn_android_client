package com.alexiacdura.mn_core.data.network.vote

import com.alexiacdura.mn_core.data.models.VoteResponse
import com.alexiacdura.mn_core.data.network.entities.VoteResponseEntity

/** VoteResponse mapping */
@Suppress("ThrowsCount")
internal fun VoteResponseEntity.createVoteResponse(): VoteResponse {
    return object : VoteResponse {
        override val id = this@createVoteResponse.idVote
        override val upvote = this@createVoteResponse.upvote
        override val dateVote = this@createVoteResponse.dateVote
        override val feedPostIdVote = this@createVoteResponse.feedPostIdVote
        override val userIdVote = this@createVoteResponse.userIdVote
    }
}



