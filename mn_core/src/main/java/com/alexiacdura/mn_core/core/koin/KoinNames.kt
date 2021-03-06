package com.alexiacdura.mn_core.core.koin

import org.koin.core.qualifier.named

object KoinNames {
    val MUSICNERDS_BASE_URL = named("MusicnerdsBaseUrl")
    val MUSICNERDS_IS_DEBUG = named("MusicnerdsIsDebug")
    val USER_API_SERVICE = named("FeedApiService")
    val FEED = named("FeedInteractor")
    val USER_API = named("UserApi")
    val USER_FEED = named("UserFeedInteractor")
    val USER_DATA = named("UserDataInteractor")
    val STAR_API_SERVICE = named("StarApiService")
    val VOTE_API_SERVICE = named("VoteApiService")
}