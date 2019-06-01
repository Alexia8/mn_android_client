package com.alexiacdura.mn_core.data.user.states

import com.alexiacdura.mn_core.data.models.FeedPost

sealed class UserFeedState {
    object Loading : UserFeedState()
    data class Success(val items: List<FeedPost>) : UserFeedState()
    data class Error(val throwable: Throwable) : UserFeedState()
}