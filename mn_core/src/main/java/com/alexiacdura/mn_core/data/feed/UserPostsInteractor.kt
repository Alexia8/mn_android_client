package com.alexiacdura.mn_core.data.feed

import io.reactivex.Observable

interface UserPostsInteractor {
    fun createFeedStateObservable(
        eventsObservable: Observable<FeedEvent>,
        userId: Int,
        limFrom: Int,
        limTo: Int
    ): Observable<FeedState>
}