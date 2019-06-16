package com.alexiacdura.mn_core.data.feed

sealed class FeedEvent {
    object Feed : FeedEvent()
    object Starred : FeedEvent()
    object UserPosts : FeedEvent()
    class LoadMoreFeed(val skip: Int) : FeedEvent()
    class LoadMoreStarred(val skip: Int) : FeedEvent()
    class LoadMoreProfile(val skip: Int) : FeedEvent()
}