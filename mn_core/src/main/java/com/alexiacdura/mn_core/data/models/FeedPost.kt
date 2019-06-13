package com.alexiacdura.mn_core.data.models

import java.net.URL

interface FeedPost {
    val feedPostId: Int
    val feedPostDate: Int
    val feedPostUser: UserPost
    val feedPostData: Post
    val feedPostStars: List<Star>
    val feedPostVotes: List<Vote>

    interface UserPost {
        val id: Int
        val username: String
        val imageUrl: URL?
    }

    interface Post {
        val id: Int
        val title: String
        val postImage: URL
        val postUrl: String
        val postGenres: List<PostGenre>

        interface PostGenre {
            val genre: Genre

            interface Genre {
                val id: Int
                val name: String
                val style: Style

                interface Style {
                    val id: Int
                    val name: String
                }
            }
        }
    }

    interface Star {
        val id: Int
        val dateStar: Int
        val feedPostIdStar: Int
        val userStar: UserStar

        interface UserStar {
            val id: Int
            val username: String
            val imageUrl: URL?
        }
    }

    interface Vote {
        val id: Int
        val upvote: Int
        val dateVote: Int
        val userVote: UserVote
        val feedPostIdVote: Int

        interface UserVote {
            val id: Int
            val username: String
            val imageUrl: URL?
        }
    }
}