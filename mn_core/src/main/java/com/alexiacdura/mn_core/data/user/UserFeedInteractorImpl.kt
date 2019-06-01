package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.user.UserApi
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import io.reactivex.Observable

internal class UserFeedInteractorImpl(private val userApi: UserApi) : UserFeedInteractor {

    override fun getUserRecentFeedPosts(userId: Int, limFrom: Int, limTo: Int): Observable<UserFeedState> {
        return userApi
            .getUserFeed(userId, limFrom, limTo)
            .toObservable()
            .map { UserFeedState.Success(it) as UserFeedState }
            .onErrorReturn {
                UserFeedState.Error(it)
            }
            .startWith(UserFeedState.Loading)
    }

    override fun getUserRecentPosts(userId: Int, limFrom: Int, limTo: Int): Observable<UserPostsState> {
        return userApi
            .getUserRecentPosts(userId, limFrom, limTo)
            .toObservable()
            .map { UserPostsState.Success(it) as UserPostsState }
            .onErrorReturn {
                UserPostsState.Error(it)
            }
            .startWith(UserPostsState.Loading)
    }

    override fun getUserRecentStarredPosts(userId: Int, limFrom: Int, limTo: Int): Observable<UserStarredState> {
        return userApi
            .getUserStarredPosts(userId, limFrom, limTo)
            .toObservable()
            .map { UserStarredState.Success(it) as UserStarredState }
            .onErrorReturn {
                UserStarredState.Error(it)
            }
            .startWith(UserStarredState.Loading)
    }
}