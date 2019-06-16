package com.alexiacdura.mn_core.data.network.vote

import com.alexiacdura.mn_core.data.network.entities.VoteResponseEntity
import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import io.reactivex.Single
import retrofit2.http.*

internal interface VoteApiService {

    companion object {
        const val API_PATH = "api/Votes"
    }

    /** Create a vote by user and feedPostId*/
    @Headers("Content-Type: application/json")
    @POST(API_PATH)
    fun createVote(@Body vote: Vote): Single<VoteResponseEntity>

    /** Delete vote by id*/
    @DELETE("$API_PATH{voteId}")
    fun deleteVote(
        @Query("starId") voteId: Int
    ): Single<VoteResponseEntity>
}