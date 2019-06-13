package com.alexiacdura.mn_ui.ui.components.post.body

import com.alexiacdura.mn_core.data.models.FeedPost
import java.net.URL

class BodyCardViewModel(
    private val feedPost: FeedPost.Post?,
    private val clickCallback: (FeedPost.Post?) -> Unit = {}
) {

    val postImageUrl = feedPost?.postImage ?: URL("")
    val postUrl = feedPost?.postUrl ?: URL("")
    fun onClickPost() {
        clickCallback(feedPost)
    }
}