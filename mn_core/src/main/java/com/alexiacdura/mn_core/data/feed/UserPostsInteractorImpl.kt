package com.alexiacdura.mn_core.data.feed

import com.alexiacdura.mn_core.data.user.UserDataInteractor
import com.alexiacdura.mn_core.data.user.UserFeedInteractor
import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

internal class UserPostsInteractorImpl(
    private val userFeedInteractor: UserFeedInteractor,
    private val userDataInteractor: UserDataInteractor
) : UserPostsInteractor {
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

        val userPostsFilter = eventsObservable
            .filter { it is FeedEvent.UserPosts }
            .switchMap {
                createUserStateObservable { userPostsState, userDataState ->
                    FeedState.UserPosts.SuccessInitial(userPostsState = userPostsState, userDataState = userDataState)
                }
            }

        val userPostsLoadMoreFilter = eventsObservable
            .filter { it is FeedEvent.LoadMoreProfile }
            .switchMap { createLoadMoreUserPostStateObservable(it as FeedEvent.LoadMoreProfile) }

        return Observable
            .merge(userPostsFilter, userPostsLoadMoreFilter)
            .onErrorReturn { FeedState.Error(it) }
    }

    /** UserProfile states */

    private fun createUserStateObservable(
        stateBuilder: (userPostsState: UserPostsState, userDataState: UserDataState) -> FeedState
    ): Observable<FeedState> {
        val userPostsObservable = userFeedInteractor.getUserRecentPosts(userId, limFrom, limTo)
        val userDataObservable = userDataInteractor.getUserData(userId)

        val combinedObservables = Observable.combineLatest(userPostsObservable, userDataObservable,
            BiFunction<UserPostsState, UserDataState, Pair<UserDataState, UserPostsState>> { userPostsState, userDataState ->
                Pair(userDataState, userPostsState)
            })

        return combinedObservables.map {
            when {
                hasErrorUserState(it) -> createFeedErrorUserState(it)
                hasSuccessUserState(it) -> stateBuilder(it.second, it.first)
                else -> FeedState.Loading
            }
        }
    }

    private fun hasErrorUserState(pair: Pair<UserDataState, UserPostsState>): Boolean {
        return pair.first is UserDataState.Error || pair.second is UserPostsState.Error
    }

    private fun hasSuccessUserState(pair: Pair<UserDataState, UserPostsState>): Boolean {
        return pair.first is UserDataState.Success && pair.second is UserPostsState.Success
    }

    private fun createFeedErrorUserState(statesPair: Pair<UserDataState, UserPostsState>): FeedState.Error {
        val throwable = when {
            statesPair.first is UserDataState.Error -> (statesPair.first as UserDataState.Error).throwable
            statesPair.second is UserPostsState.Error -> (statesPair.second as UserPostsState.Error).throwable
            else -> Throwable("Unknown error in the feed interactor with user posts.")
        }
        return FeedState.Error(throwable)
    }

    /** Load more posts */
    private fun createLoadMoreUserPostStateObservable(event: FeedEvent.LoadMoreProfile): Observable<FeedState> {
        return userFeedInteractor.getUserRecentPosts(userId, event.skip, event.skip + SKIP)
            .map {
                when (it) {
                    is UserPostsState.Success -> processStarredLoadMoreSuccess(it)
                    is UserPostsState.Loading -> FeedState.LoadingMore
                    is UserPostsState.Error -> FeedState.Error(it.throwable)
                }
            }
    }

    private fun processStarredLoadMoreSuccess(state: UserPostsState.Success): FeedState {
        return if (state.items.size < LOADING_LIMIT) {
            FeedState.UserPosts.SuccessMore(state)
        } else {
            FeedState.UserPosts.SuccessEnd(state)
        }
    }

    companion object {
        private const val LOADING_LIMIT = 10
        private const val SKIP = 3
    }
}