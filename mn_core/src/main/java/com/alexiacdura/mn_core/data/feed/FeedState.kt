package com.alexiacdura.mn_core.data.feed

import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_core.data.user.states.UserStarredState

sealed class FeedState {
    object Loading : FeedState()
    object LoadingMore : FeedState()
    data class Error(val throwable: Throwable) : FeedState()
    sealed class Feed : FeedState() {
        data class SuccessInitial(val userFeedState: UserFeedState) : Feed()
        data class SuccessMore(val userFeedState: UserFeedState) : Feed()
        data class SuccessEnd(val userFeedState: UserFeedState) : Feed()
    }

    sealed class Starred : FeedState() {
        data class SuccessInitial(val userStarredState: UserStarredState) : Starred()
        data class SuccessMore(val userStarredState: UserStarredState) : Starred()
        data class SuccessEnd(val userStarredState: UserStarredState) : Starred()
    }

    sealed class UserPosts : FeedState() {
        data class SuccessInitial(val userPostsState: UserPostsState, val userDataState: UserDataState) : UserPosts()
        data class SuccessMore(val userPostsState: UserPostsState) : UserPosts()
        data class SuccessEnd(val userPostsState: UserPostsState) : UserPosts()
    }
}