package com.alexiacdura.mn_core.data.user.states

import com.alexiacdura.mn_core.data.models.FeedPost

sealed class UserStarredState {
    object Loading : UserStarredState()
    data class Success(val items: List<FeedPost>) : UserStarredState()
    data class Error(val throwable: Throwable) : UserStarredState()
}