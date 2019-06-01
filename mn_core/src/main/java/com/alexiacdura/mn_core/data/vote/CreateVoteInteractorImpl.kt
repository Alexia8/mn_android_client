package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import com.alexiacdura.mn_core.data.network.vote.VoteApi
import com.alexiacdura.mn_core.data.star.states.CreateVoteState
import io.reactivex.Observable

internal class CreateVoteInteractorImpl(private val voteApi: VoteApi) : CreateVoteInteractor {

    override fun createVote(vote: Vote): Observable<CreateVoteState> {
        return voteApi
            .createVote(vote)
            .toObservable()
            .map { CreateVoteState.Success(it) as CreateVoteState }
            .onErrorReturn {
                CreateVoteState.Error(it)
            }
            .startWith(CreateVoteState.Loading)
    }
}