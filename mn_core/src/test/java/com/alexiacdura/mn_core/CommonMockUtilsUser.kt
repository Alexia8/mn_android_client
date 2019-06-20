package com.alexiacdura.mn_core

import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.models.User
import com.alexiacdura.mn_core.data.models.UserData
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import java.io.File

object CommonMockUtilsUser {
    const val USER_FEED = "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/user_feed.json"
    const val USER_POSTS = "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/user_posts.json"
    const val USER_STARRED =
        "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/user_starred.json"
    const val USER_DATA = "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/user_data.json"
    const val USER = "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/user.json"

    private fun getJson(path: String, malformed: Boolean): String {
        //val uri = this.javaClass.classLoader.getResource(path)
        //val decodedPath = URLDecoder.decode(uri.path, "UTF-8")
        val file = File(path)
        val resourceText = String(file.readBytes())
        return if (malformed) {
            resourceText.substring(2)
        } else {
            resourceText
        }
    }

    fun getResponse(malformed: Boolean, file: String): MockResponse {
        val responseBody = getJson(file, malformed)
        return MockResponse().setBody(responseBody)
    }

    fun getModifiedResponse(attributeToModify: String, file: String): MockResponse {
        val responseBody = getModifiedResponseString(attributeToModify, file)
        return MockResponse().setBody(responseBody)
    }

    private fun getModifiedResponseString(attributeToModify: String, file: String): String {
        val responseBody = getJson(file, false)

        val regex = Regex("\"$attributeToModify\": (\".+\")")
        val match = regex.find(responseBody)

        return responseBody.replaceRange(match?.groups?.get(1)?.range ?: 0..0, "null")
    }

    fun assertFeedPostEquals(expected: FeedPost, actual: FeedPost) {
        Assert.assertEquals(expected.feedPostId, actual.feedPostId)
        Assert.assertEquals(expected.feedPostDate, actual.feedPostDate)
        assertUserPostEquals(expected.feedPostUser, actual.feedPostUser)
        assertPostEquals(expected.feedPostData, actual.feedPostData)
        assertPostListStarsEquals(expected.feedPostStars!!, actual.feedPostStars!!)
        assertPostListVotesEquals(expected.feedPostVotes!!, actual.feedPostVotes!!)
    }

    private fun assertUserPostEquals(expected: FeedPost.UserPost, actual: FeedPost.UserPost) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.username, actual.username)
        Assert.assertEquals(expected.imageUrl, actual.imageUrl)
    }

    private fun assertPostEquals(expected: FeedPost.Post, actual: FeedPost.Post) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.title, actual.title)
        Assert.assertEquals(expected.postImage, actual.postImage)
        Assert.assertEquals(expected.postUrl, actual.postUrl)
        assertPostGenreEquals(expected.postGenres, actual.postGenres)
    }

    private fun assertPostGenreEquals(expected: List<FeedPost.Post.PostGenre>, actual: List<FeedPost.Post.PostGenre>) {
        expected.forEach { postGenre ->
            actual.forEach { actualPostGenre ->
                assertPostGenreGenreEquals(postGenre.genre, actualPostGenre.genre)
            }
        }
    }

    private fun assertPostGenreGenreEquals(
        expected: FeedPost.Post.PostGenre.Genre,
        actual: FeedPost.Post.PostGenre.Genre
    ) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.name, actual.name)
        assertPostGenreGenreStyleEquals(expected.style, actual.style)
    }

    private fun assertPostGenreGenreStyleEquals(
        expected: FeedPost.Post.PostGenre.Genre.Style,
        actual: FeedPost.Post.PostGenre.Genre.Style
    ) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.name, actual.name)
    }

    private fun assertPostListStarsEquals(expected: List<FeedPost.Star>, actual: List<FeedPost.Star>) {
        expected.forEach { postStar ->
            actual.forEach { actualPostStar ->
                Assert.assertEquals(postStar.id, actualPostStar.id)
                Assert.assertEquals(postStar.dateStar, actualPostStar.dateStar)
                Assert.assertEquals(postStar.feedPostIdStar, actualPostStar.feedPostIdStar)
                assertPostUserStarEquals(postStar.userStar, actualPostStar.userStar)
            }
        }
    }

    private fun assertPostUserStarEquals(expected: FeedPost.Star.UserStar, actual: FeedPost.Star.UserStar) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.username, actual.username)
        Assert.assertEquals(expected.imageUrl, actual.imageUrl)
    }

    private fun assertPostListVotesEquals(expected: List<FeedPost.Vote>, actual: List<FeedPost.Vote>) {
        expected.forEach { postVote ->
            actual.forEach { actualPostVote ->
                Assert.assertEquals(postVote.id, actualPostVote.id)
                Assert.assertEquals(postVote.upvote, actualPostVote.upvote)
                Assert.assertEquals(postVote.dateVote, actualPostVote.dateVote)
                Assert.assertEquals(postVote.feedPostIdVote, actualPostVote.feedPostIdVote)
                assertPostUserVoteEquals(postVote.userVote, actualPostVote.userVote)
            }
        }
    }

    private fun assertPostUserVoteEquals(expected: FeedPost.Vote.UserVote, actual: FeedPost.Vote.UserVote) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.username, actual.username)
        Assert.assertEquals(expected.imageUrl, actual.imageUrl)
    }

    internal fun assertUserDataEquals(expected: UserData, actual: UserData) {
        Assert.assertEquals(expected.postQuantity, actual.postQuantity)
        Assert.assertEquals(expected.starredQuantity, actual.starredQuantity)
        assertUserDataUserEquals(expected.followings, actual.followings)
        assertUserDataUserEquals(expected.followers, actual.followers)
    }

    private fun assertUserDataUserEquals(expected: List<UserData.UserFollow>, actual: List<UserData.UserFollow>) {
        expected.forEach { user ->
            actual.forEach { actualUser ->
                Assert.assertEquals(user.id, actualUser.id)
                Assert.assertEquals(user.username, actualUser.username)
                Assert.assertEquals(user.email, actualUser.email)
                Assert.assertEquals(user.imageUrl, actualUser.imageUrl)
            }
        }
    }

    internal fun assertUserEquals(expected: User, actual: User) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.email, actual.email)
        Assert.assertEquals(expected.username, actual.username)
        Assert.assertEquals(expected.imageUrl, actual.imageUrl)
        assertUserStylesEquals(expected.likedStyles, actual.likedStyles)
    }

    private fun assertUserStylesEquals(expected: List<User.Style>, actual: List<User.Style>) {
        expected.forEach { style ->
            actual.forEach { actualStyle ->
                Assert.assertEquals(style.id, actualStyle.id)
                Assert.assertEquals(style.name, actualStyle.name)
            }
        }
    }
}


