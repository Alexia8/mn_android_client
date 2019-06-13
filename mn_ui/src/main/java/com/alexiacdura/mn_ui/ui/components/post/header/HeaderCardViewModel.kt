package com.alexiacdura.mn_ui.ui.components.post.header

import com.alexiacdura.mn_core.data.models.FeedPost

data class HeaderCardViewModel(
    val feedPost: FeedPost?,
    private val callbackUserImageClick: (FeedPost.UserPost?) -> Unit = {},
    private val callbackUserNameClick: (FeedPost.UserPost?) -> Unit = {},
    private val callbackPostClick: (FeedPost.Post?) -> Unit = {}
) {
    //val userImage = feedPost?.feedPostUser?.imageUrl ?: URL("")
    val username = feedPost?.feedPostUser?.username ?: ""
    val titlePost = feedPost?.feedPostData?.title ?: ""
    val datePost = feedPost?.feedPostDate.toString()
    val postImage = feedPost?.feedPostData?.postImage

    fun onUserClick() {
        callbackUserImageClick(feedPost?.feedPostUser)
    }

    fun onUserNameClick() {
        callbackUserNameClick(feedPost?.feedPostUser)
    }

    fun onPostClick() {
        callbackPostClick(feedPost?.feedPostData)
    }
}