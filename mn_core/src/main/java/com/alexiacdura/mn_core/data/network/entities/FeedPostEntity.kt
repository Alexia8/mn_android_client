package com.alexiacdura.mn_core.data.network.entities

import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class FeedPostEntity(
    @SerializedName("id") val idFeedPost: Int,
    @SerializedName("date") val dateFeedPost: Int,
    @SerializedName("user") val userFeedPost: UserPost,
    @SerializedName("post") val postFeedPost: Post,
    @SerializedName("stars") val starsFeedPost: List<Star>?,
    @SerializedName("votes") val votesFeedPost: List<Vote>?
) {
    data class UserPost(
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("email") val email: String,
        @SerializedName("imageUrl") val imageUrl: URL?
    )

    data class Post(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("imageUrl") val postImage: URL,
        @SerializedName("postUrl") val postUrl: URL,
        @SerializedName("postGenres") val postGenres: List<PostGenre>?
    ) {
        data class PostGenre(
            @SerializedName("genre") val genre: Genre
        ) {
            data class Genre(
                @SerializedName("id") val id: Int,
                @SerializedName("name") val name: String,
                @SerializedName("style") val style: Style
            ) {
                data class Style(
                    @SerializedName("id") val id: Int,
                    @SerializedName("name") val name: String
                )
            }
        }
    }

    data class Star(
        @SerializedName("id") val idStar: Int,
        @SerializedName("title") val dateStar: Int,
        @SerializedName("feedPostId") val feedPostId: Int,
        @SerializedName("user") val userStar: UserStar
    ) {
        data class UserStar(
            @SerializedName("id") val id: Int,
            @SerializedName("username") val username: String,
            @SerializedName("imageUrl") val imageUrl: URL?
        )
    }

    data class Vote(
        @SerializedName("id") val id: Int,
        @SerializedName("upvote") val upvote: Int,
        @SerializedName("date") val dateVote: Int,
        @SerializedName("user") val userVote: UserVote,
        @SerializedName("feedPostId") val feedPostId: Int
    ) {
        data class UserVote(
            @SerializedName("id") val id: Int,
            @SerializedName("username") val username: String,
            @SerializedName("imageUrl") val imageUrl: URL?
        )
    }
}