package com.alexiacdura.mn_ui.ui.components.post.footer

import com.alexiacdura.mn_core.data.models.FeedPost
import kotlin.reflect.KFunction1

data class FooterCardViewModel(
    val feedPost: FeedPost?,
    private var clickStarTextCallback: KFunction1<@ParameterName(name = "clickStarText") List<FeedPost.Star>, Unit>? = null,
    private var clickVoteTextCallback: KFunction1<@ParameterName(name = "clickVoteText") List<FeedPost.Vote>, Unit>? = null,
    private var clickDownVoteTextCallback: KFunction1<@ParameterName(name = "clickDownVoteText") List<FeedPost.Vote>, Unit>? = null
) {
    var starsList: List<FeedPost.Star> = feedPost?.feedPostStars ?: emptyList()

    var votesList: List<FeedPost.Vote> = feedPost?.feedPostVotes ?: emptyList()

    fun clickStarText() {
        clickStarTextCallback?.invoke(feedPost?.feedPostStars!!)
    }

    fun clickVoteText() {
        clickVoteTextCallback?.invoke(feedPost?.feedPostVotes!!)
    }

    fun clickDownVoteText() {
        clickDownVoteTextCallback?.invoke(feedPost?.feedPostVotes!!)
    }
}