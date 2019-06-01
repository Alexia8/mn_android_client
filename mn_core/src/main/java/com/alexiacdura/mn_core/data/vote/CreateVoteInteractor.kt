package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import com.alexiacdura.mn_core.data.star.states.CreateVoteState
import io.reactivex.Observable

interface CreateVoteInteractor {
    fun createVote(vote: Vote): Observable<CreateVoteState>
}