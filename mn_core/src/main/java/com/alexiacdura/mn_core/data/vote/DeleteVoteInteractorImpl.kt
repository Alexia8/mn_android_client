package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.vote.VoteApi
import com.alexiacdura.mn_core.data.star.states.DeleteVoteState
import io.reactivex.Observable

internal class DeleteVoteInteractorImpl(private val voteApi: VoteApi) : DeleteVoteInteractor {

    override fun deleteVote(voteId: Int): Observable<DeleteVoteState> {
        return voteApi
            .deleteVote(voteId)
            .toObservable()
            .map { DeleteVoteState.Success(it) as DeleteVoteState }
            .onErrorReturn {
                DeleteVoteState.Error(it)
            }
            .startWith(DeleteVoteState.Loading)
    }
}