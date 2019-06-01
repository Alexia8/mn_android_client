package com.alexiacdura.mn_core.data.star.states

import com.alexiacdura.mn_core.data.models.StarResponse

sealed class CreateStarState {
    object Loading : CreateStarState()
    data class Success(val item: StarResponse) : CreateStarState()
    data class Error(val throwable: Throwable) : CreateStarState()
}