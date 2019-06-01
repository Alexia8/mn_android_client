package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.star.states.DeleteVoteState
import io.reactivex.Observable

interface DeleteVoteInteractor {
    fun deleteVote(voteId: Int): Observable<DeleteVoteState>
}