package com.alexiacdura.mn_core.data.feed

import io.reactivex.Observable

interface FeedInteractor {
    fun createFeedStateObservable(eventsObservable: Observable<FeedEvent>, userId: Int): Observable<FeedState>
}