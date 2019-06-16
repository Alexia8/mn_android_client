package com.alexiacdura.mn_core.data.feed

import com.alexiacdura.mn_core.data.user.UserFeedInteractor
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import io.reactivex.Observable

internal class FeedInteractorImpl(
    private val userFeedInteractor: UserFeedInteractor
) : FeedInteractor {
    private var userId: Int = 1
    private var limFrom: Int = 0
    private var limTo: Int = 5
    override fun createFeedStateObservable(
        eventsObservable: Observable<FeedEvent>,
        userId: Int,
        limFrom: Int,
        limTo: Int
    ): Observable<FeedState> {
        this.userId = userId
        this.limFrom = limFrom
        this.limTo = limTo

        val feedFilter = eventsObservable
            .filter { it is FeedEvent.Feed }
            .switchMap {
                createFeedStateObservable { userFeedState ->
                    FeedState.Feed.SuccessInitial(userFeedState = userFeedState)
                }
            }

        val starredFilter = eventsObservable
            .filter { it is FeedEvent.Starred }
            .switchMap {
                createStarredStateObservable { userStarredState ->
                    FeedState.Starred.SuccessInitial(userStarredState = userStarredState)
                }
            }

        val feedLoadMoreFilter = eventsObservable
            .filter { it is FeedEvent.LoadMoreFeed }
            .switchMap { createLoadMoreFeedStateObservable(it as FeedEvent.LoadMoreFeed) }

        val starredLoadMoreFilter = eventsObservable
            .filter { it is FeedEvent.LoadMoreStarred }
            .switchMap { createLoadMoreStarredStateObservable(it as FeedEvent.LoadMoreStarred) }

        return Observable
            .merge(feedFilter, starredFilter, feedLoadMoreFilter, starredLoadMoreFilter)
            .onErrorReturn { FeedState.Error(it) }
    }

    /** UserFeedPosts states */

    private fun createFeedStateObservable(
        stateBuilder: (userFeedState: UserFeedState) -> FeedState
    ): Observable<FeedState> {
        val userFeedObservable =
            userFeedInteractor.getUserRecentFeedPosts(userId = userId, limFrom = limFrom, limTo = limTo)

        return userFeedObservable.map {
            when (it) {
                is UserFeedState.Error -> FeedState.Error(it.throwable)
                is UserFeedState.Success -> stateBuilder(it)
                else -> FeedState.Loading
            }
        }
    }

    private fun createLoadMoreFeedStateObservable(event: FeedEvent.LoadMoreFeed): Observable<FeedState> {
        return userFeedInteractor.getUserRecentFeedPosts(userId, event.skip, event.skip + SKIP)
            .map {
                when (it) {
                    is UserFeedState.Success -> processFeedLoadMoreSuccess(it)
                    is UserFeedState.Loading -> FeedState.LoadingMore
                    is UserFeedState.Error -> FeedState.Error(it.throwable)
                }
            }
    }

    private fun processFeedLoadMoreSuccess(state: UserFeedState.Success): FeedState {
        return if (state.items.size < LOADING_LIMIT) {
            FeedState.Feed.SuccessMore(state)
        } else {
            FeedState.Feed.SuccessEnd(state)
        }
    }

    /** UserStarredPosts states */

    private fun createStarredStateObservable(
        stateBuilder: (userStarredState: UserStarredState) -> FeedState
    ): Observable<FeedState> {
        val userStarredObservable =
            userFeedInteractor.getUserRecentStarredPosts(userId = userId, limFrom = limFrom, limTo = limTo)

        return userStarredObservable.map {
            when (it) {
                is UserStarredState.Error -> FeedState.Error(it.throwable)
                is UserStarredState.Success -> stateBuilder(it)
                else -> FeedState.Loading
            }
        }
    }

    private fun createLoadMoreStarredStateObservable(event: FeedEvent.LoadMoreStarred): Observable<FeedState> {
        return userFeedInteractor.getUserRecentStarredPosts(userId, event.skip, event.skip + SKIP)
            .map {
                when (it) {
                    is UserStarredState.Success -> processStarredLoadMoreSuccess(it)
                    is UserStarredState.Loading -> FeedState.LoadingMore
                    is UserStarredState.Error -> FeedState.Error(it.throwable)
                }
            }
    }

    private fun processStarredLoadMoreSuccess(state: UserStarredState.Success): FeedState {
        return if (state.items.size < LOADING_LIMIT) {
            FeedState.Starred.SuccessMore(state)
        } else {
            FeedState.Starred.SuccessEnd(state)
        }
    }

    companion object {
        private const val LOADING_LIMIT = 10
        private const val SKIP = 3
    }
}