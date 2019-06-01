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
    postFeedPost.postGenres.let { postGenres ->
        val postGenreList = listOf<FeedPost.Post.PostGenre>()
        postGenres?.forEach { postGenre ->
            postGenre.genre.let { genre ->
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
                postGenreList.plus(genre)
            }
        }
        return postGenreList
    }
}

private fun FeedPostEntity.postStars(): List<FeedPost.Star> {
    starsFeedPost.let { postStars ->
        val postStarList = listOf<FeedPost.Star>()
        postStars?.forEach { star ->
            object : FeedPost.Star {
                override val id = star.idStar
                override val dateStar = star.dateStar
                override val feedPostIdStar = star.feedPostId
                override val userStar = star.userStar.let { userStar ->
                    object : FeedPost.Star.UserStar {
                        override val id = userStar.id
                        override val username = userStar.username
                        override val imageUrl = userStar.imageUrl
                    }
                }
            }
            postStarList.plus(star)
        }
        return postStarList
    }
}

private fun FeedPostEntity.postVotes(): List<FeedPost.Vote> {
    votesFeedPost.let { postVotes ->
        val postVoteList = listOf<FeedPost.Vote>()
        postVotes?.forEach { vote ->
            object : FeedPost.Vote {
                override val id = vote.id
                override val upvote = vote.upvote
                override val dateVote = vote.dateVote
                override val feedPostIdVote = vote.feedPostId
                override val userVote = vote.userVote.let { userVote ->
                    object : FeedPost.Vote.UserVote {
                        override val id = userVote.id
                        override val username = userVote.username
                        override val imageUrl = userVote.imageUrl
                    }
                }
            }
            postVoteList.plus(vote)
        }
        return postVoteList
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
    likedStyles.let { userStyles ->
        val userStylesList = listOf<User.Style>()
        userStyles.forEach { style ->
            object : User.Style {
                override val id = style.id
                override val name = style.name
            }
            userStylesList.plus(style)
        }
        return userStylesList
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
    followings.let { userFollowings ->
        val userFollowingsList = listOf<UserData.User>()
        userFollowings.forEach { following ->
            object : UserData.User {
                override val id = following.id
                override val username = following.username
                override val email = following.email
                override val imageUrl = following.imageUrl
            }
            userFollowingsList.plus(following)
        }
        return userFollowingsList
    }
}

private fun UserDataEntity.userFollowers(): List<UserData.User> {
    followers.let { userFollowers ->
        val userFollowersList = listOf<UserData.User>()
        userFollowers.forEach { follower ->
            object : UserData.User {
                override val id = follower.id
                override val username = follower.username
                override val email = follower.email
                override val imageUrl = follower.imageUrl
            }
            userFollowersList.plus(follower)
        }
        return userFollowersList
    }
}


