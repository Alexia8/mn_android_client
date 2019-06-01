package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import io.reactivex.Observable

interface UserFeedInteractor {
    fun getUserRecentFeedPosts(userId: Int, limFrom: Int, limTo: Int): Observable<UserFeedState>

    fun getUserRecentPosts(userId: Int, limFrom: Int, limTo: Int): Observable<UserPostsState>

    fun getUserRecentStarredPosts(userId: Int, limFrom: Int, limTo: Int): Observable<UserStarredState>
}