package com.alexiacdura.mn_core.data.star.states

import com.alexiacdura.mn_core.data.models.VoteResponse

sealed class DeleteVoteState {
    object Loading : DeleteVoteState()
    data class Success(val item: VoteResponse) : DeleteVoteState()
    data class Error(val throwable: Throwable) : DeleteVoteState()
}