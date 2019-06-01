package com.alexiacdura.mn_core.data.star.states

import com.alexiacdura.mn_core.data.models.VoteResponse

sealed class CreateVoteState {
    object Loading : CreateVoteState()
    data class Success(val item: VoteResponse) : CreateVoteState()
    data class Error(val throwable: Throwable) : CreateVoteState()
}