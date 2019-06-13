package com.alexiacdura.mn_core.data.feed

import com.alexiacdura.mn_core.data.user.UserDataInteractor
import com.alexiacdura.mn_core.data.user.UserFeedInteractor
import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import io.reactivex.Observable

internal class FeedInteractorImpl(
    private val userFeedInteractor: UserFeedInteractor,
    private val userDataInteractor: UserDataInteractor
) : FeedInteractor {
    private var userId: Int = 1
    override fun createFeedStateObservable(
        eventsObservable: Observable<FeedEvent>,
        userId: Int
    ): Observable<FeedState> {
        this.userId = userId

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

        /**
        val userPostsFilter = eventsObservable
        .filter { it is FeedEvent.UserPosts }
        .switchMap {
        createUserStateObservable { userPostsState, userDataState ->
        FeedState.UserPosts.SuccessInitial(userPostsState = userPostsState, userDataState = userDataState)
        }
        }

         */

        val feedLoadMoreFilter = eventsObservable
            .filter { it is FeedEvent.LoadMore }
            .switchMap { createLoadMoreStateObservable(it as FeedEvent.LoadMore) }

        return Observable
            .merge(feedFilter, feedLoadMoreFilter)
            .onErrorReturn { FeedState.Error(it) }
    }

    /** UserFeed states */

    private fun createFeedStateObservable(
        stateBuilder: (userFeedState: UserFeedState) -> FeedState
    ): Observable<FeedState> {
        val userFeedObservable = userFeedInteractor.getUserRecentFeedPosts(userId = userId, limFrom = LIMFROM, limTo = LIMTO)

        return userFeedObservable.map {
            when (it) {
                is UserFeedState.Error -> FeedState.Error(it.throwable)
                is UserFeedState.Success -> stateBuilder(it)
                else -> FeedState.Loading
            }
        }
    }

    private fun createLoadMoreStateObservable(event: FeedEvent.LoadMore): Observable<FeedState> {
        return userFeedInteractor.getUserRecentFeedPosts(userId, LIMFROM + event.skip, LIMTO + event.skip)
            .map {
                when (it) {
                    is UserFeedState.Success -> processLoadMoreSuccess(it)
                    is UserFeedState.Loading -> FeedState.LoadingMore
                    is UserFeedState.Error -> FeedState.Error(it.throwable)
                }
            }
    }

    private fun processLoadMoreSuccess(state: UserFeedState.Success): FeedState {
        return if (state.items.size < LOADING_LIMIT) {
            FeedState.Feed.SuccessMore(state)
        } else {
            FeedState.Feed.SuccessEnd(state)
        }
    }

    /** UserStarred states */

    private fun createStarredStateObservable(
        stateBuilder: (userStarredState: UserStarredState) -> FeedState
    ): Observable<FeedState> {
        val userStarredObservable = userFeedInteractor.getUserRecentStarredPosts(userId = userId, limFrom = LIMFROM, limTo = LIMTO)

        return userStarredObservable.map {
            when (it) {
                is UserStarredState.Error -> FeedState.Error(it.throwable)
                is UserStarredState.Success -> stateBuilder(it)
                else -> FeedState.Loading
            }
        }
    }

    /**

    private fun createUserStateObservable(
    stateBuilder: (userFeedState: UserFeedState, userDataState: UserDataState) -> FeedState
    ): Observable<FeedState> {
    val userFeedObservable = userFeedInteractor.getUserRecentFeedPosts(userId = userId, limFrom = LIMFROM, limTo = LIMTO)
    val userDataObservable = userDataInteractor.getUserData(userId)

    val combinedObservables = Observable.combineLatest(userFeedObservable, userDataObservable,
    BiFunction<UserFeedState, UserDataState, Pair<UserDataState, UserFeedState>> { userFeedState, userDataState ->
    Pair(userDataState, userFeedState)
    })

    return combinedObservables.map {
    when {
    hasErrorUserState(it) -> createFeedErrorUserState(it)
    hasSuccessUserState(it) -> stateBuilder(it.first, it.second)
    else -> FeedState.Loading
    }
    }
    }

     */

    private fun hasErrorUserState(pair: Pair<UserDataState, UserFeedState>): Boolean {
        return pair.first is UserDataState.Error || pair.second is UserFeedState.Error
    }

    private fun hasSuccessUserState(pair: Pair<UserDataState, UserFeedState>): Boolean {
        return pair.first is UserDataState.Success && pair.second is UserFeedState.Success
    }

    private fun createFeedErrorUserState(statesPair: Pair<UserFeedState, UserDataState>): FeedState.Error {
        val throwable = when {
            statesPair.first is UserFeedState.Error -> (statesPair.first as UserFeedState.Error).throwable
            statesPair.second is UserDataState.Error -> (statesPair.second as UserDataState.Error).throwable
            else -> Throwable("Unknown error in the feed interactor.")
        }

        return FeedState.Error(throwable)
    }

    companion object {
        private const val LIMFROM = 0
        private const val LIMTO = 10
        private const val LOADING_LIMIT = 10
    }
}