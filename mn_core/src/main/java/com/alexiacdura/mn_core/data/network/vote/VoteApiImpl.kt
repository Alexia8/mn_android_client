package com.alexiacdura.mn_core.data.network.vote

import com.alexiacdura.mn_core.core.factory.ServiceFactory
import com.alexiacdura.mn_core.data.models.VoteResponse
import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import io.reactivex.Single

internal class VoteApiImpl(serviceFactory: ServiceFactory<VoteApiService>) : VoteApi {
    private val service: VoteApiService = serviceFactory.createService()

    override fun createVote(vote: Vote): Single<VoteResponse> {
        return service.createVote(vote)
            .map { voteResponseEntity -> voteResponseEntity.createVoteResponse() }
    }

    override fun deleteVote(voteId: Int): Single<VoteResponse> {
        return service.deleteVote(voteId)
            .map { voteResponseEntity -> voteResponseEntity.createVoteResponse() }
    }
}