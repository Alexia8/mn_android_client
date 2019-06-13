package com.alexiacdura.mn_core.data.feed

sealed class FeedEvent {
    object Feed : FeedEvent()
    object Starred : FeedEvent()
    object UserPosts : FeedEvent()
    class LoadMore(val skip: Int) : FeedEvent()
}