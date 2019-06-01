package com.alexiacdura.mn_core.data.star.states

import com.alexiacdura.mn_core.data.models.StarResponse

sealed class DeleteStarState {
    object Loading : DeleteStarState()
    data class Success(val item: StarResponse) : DeleteStarState()
    data class Error(val throwable: Throwable) : DeleteStarState()
}