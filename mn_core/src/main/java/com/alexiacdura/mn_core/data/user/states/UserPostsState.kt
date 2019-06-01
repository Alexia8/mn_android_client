package com.alexiacdura.mn_core.data.user.states

import com.alexiacdura.mn_core.data.models.FeedPost

sealed class UserPostsState {
    object Loading : UserPostsState()
    data class Success(val items: List<FeedPost>) : UserPostsState()
    data class Error(val throwable: Throwable) : UserPostsState()
}