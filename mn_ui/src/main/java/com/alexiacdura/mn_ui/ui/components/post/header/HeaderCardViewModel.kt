package com.alexiacdura.mn_ui.ui.components.post.header

import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_ui.ui.components.post.footer.FooterCardViewModel
import java.net.URL
import kotlin.reflect.KFunction1

data class HeaderCardViewModel(
    val feedPost: FeedPost?,
    private val callbackUserImageClick: KFunction1<@ParameterName(name = "user") FeedPost.UserPost, Unit>? = null,
    private val callbackUserNameClick: KFunction1<@ParameterName(name = "user") FeedPost.UserPost, Unit>? = null,
    val footerCardViewModel: FooterCardViewModel
) {
    val userImage = feedPost?.feedPostUser?.imageUrl ?: URL("https://github.com/alexia8.png")
    val username = feedPost?.feedPostUser?.username ?: ""
    val titlePost = feedPost?.feedPostData?.title ?: ""
    var datePost = feedPost?.feedPostDate.toString()
    val postImage = feedPost?.feedPostData?.postImage
    val postUrl = feedPost?.feedPostData?.postUrl

    fun onUserClick() {
        callbackUserImageClick?.invoke(feedPost!!.feedPostUser)
    }

    fun onUserNameClick() {
        callbackUserNameClick?.invoke(feedPost!!.feedPostUser)
    }
}