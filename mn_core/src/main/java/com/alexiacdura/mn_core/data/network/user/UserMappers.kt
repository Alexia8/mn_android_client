package com.alexiacdura.mn_core.data.network.user

import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.models.User
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.network.entities.FeedPostEntity
import com.alexiacdura.mn_core.data.network.entities.UserDataEntity
import com.alexiacdura.mn_core.data.network.entities.UserEntity

/** FeedPost mapping */
@Suppress("ThrowsCount")
internal fun FeedPostEntity.toFeedPost(): FeedPost {
    return object : FeedPost {
        override val feedPostId = this@toFeedPost.idFeedPost
        override val feedPostDate = this@toFeedPost.dateFeedPost
        override val feedPostUser = postUser()
        override val feedPostData = post()
        override val feedPostStars = postStars()
        override val feedPostVotes = postVotes()
    }
}

private fun FeedPostEntity.postUser(): FeedPost.UserPost {
    userFeedPost.let {
        return object : FeedPost.UserPost {
            override val id = it.id
            override val username = it.username
            override val imageUrl = it.imageUrl
        }
    }
}

private fun FeedPostEntity.post(): FeedPost.Post {
    postFeedPost.let {
        return object : FeedPost.Post {
            override val id = it.id
            override val title = it.title
            override val postImage = it.postImage
            override val postUrl = it.postUrl
            override val postGenres = postGenres()
        }
    }
}

private fun FeedPostEntity.postGenres(): List<FeedPost.Post.PostGenre> {
    return postFeedPost.postGenres!!.map {
        it.let { postGenre ->
            object : FeedPost.Post.PostGenre {
                override val genre = postGenre.genre.let { genre ->
                    object : FeedPost.Post.PostGenre.Genre {
                        override val id = genre.id
                        override val name = genre.name
                        override val style = genre.style.let { style ->
                            object : FeedPost.Post.PostGenre.Genre.Style {
                                override val id = style.id
                                override val name = style.name
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun FeedPostEntity.postStars(): List<FeedPost.Star>? {
    return starsFeedPost?.map {
        object : FeedPost.Star {
            override val id = it.idStar
            override val dateStar = it.dateStar
            override val feedPostIdStar = it.feedPostId
            override val userStar = it.userStar.let { userStar ->
                object : FeedPost.Star.UserStar {
                    override val id = userStar.id
                    override val username = userStar.username
                    override val imageUrl = userStar.imageUrl
                }
            }
        }
    }
}

private fun FeedPostEntity.postVotes(): List<FeedPost.Vote>? {
    return votesFeedPost?.map {
        object : FeedPost.Vote {
            override val id = it.id
            override val upvote = it.upvote
            override val dateVote = it.dateVote
            override val feedPostIdVote = it.feedPostId
            override val userVote = it.userVote.let { userVote ->
                object : FeedPost.Vote.UserVote {
                    override val id = userVote.id
                    override val username = userVote.username
                    override val imageUrl = userVote.imageUrl
                }
            }
        }
    }
}

/** User mapping */
@Suppress("ThrowsCount")
internal fun UserEntity.toUser(): User {
    return object : User {
        override val id = this@toUser.userId
        override val username = this@toUser.username
        override val email = this@toUser.email
        override val imageUrl = this@toUser.imageUrl
        override val likedStyles = userStyles()
    }
}

private fun UserEntity.userStyles(): List<User.Style> {
    return likedStyles.map {
        object : User.Style {
            override val id = it.id
            override val name = it.name
        }
    }
}

/** User Data mapping */
@Suppress("ThrowsCount")
internal fun UserDataEntity.toUserData(): UserData {
    return object : UserData {
        override val postQuantity = this@toUserData.postQuantity
        override val starredQuantity = this@toUserData.starredQuantity
        override val followings = userFollowings()
        override val followers = userFollowers()
    }
}

private fun UserDataEntity.userFollowings(): List<UserData.User> {
    return followings.map {
        object : UserData.User {
            override val id = it.id
            override val username = it.username
            override val email = it.email
            override val imageUrl = it.imageUrl
        }
    }
}

private fun UserDataEntity.userFollowers(): List<UserData.User> {
    return followers.map {
        object : UserData.User {
            override val id = it.id
            override val username = it.username
            override val email = it.email
            override val imageUrl = it.imageUrl
        }
    }
}


