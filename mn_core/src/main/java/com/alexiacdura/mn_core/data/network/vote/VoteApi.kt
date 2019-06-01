package com.alexiacdura.mn_core.data.network.vote

import com.alexiacdura.mn_core.data.models.VoteResponse
import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import io.reactivex.Single

internal interface VoteApi {
    fun createVote(vote: Vote): Single<VoteResponse>

    fun deleteVote(voteId: Int): Single<VoteResponse>
}