import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.models.User
import com.alexiacdura.mn_core.data.models.UserData
import java.net.URL

@Suppress("MaxLineLength", "MagicNumber")
fun getTestFeedPostUserFeed(): FeedPost {
    val postGenresList = listOf<FeedPost.Post.PostGenre>()
    val postStarsList = emptyList<FeedPost.Star>()
    val postVotesList = emptyList<FeedPost.Vote>()

    /** Data for UserEntity post */
    val userPost = object : FeedPost.UserPost {
        override val id = 2
        override val username = "Mircea Teodor Oprea"
        override val imageUrl: URL? = null
    }

    /** Data for post genre */
    val soulMusic = object : FeedPost.Post.PostGenre.Genre {
        override val id = 439
        override val name = "Soul Music"
        override val style = object : FeedPost.Post.PostGenre.Genre.Style {
            override val id = 31
            override val name = "Soul"
        }
    }
    postGenresList.plus(soulMusic)

    /** Data for post */
    val post = object : FeedPost.Post {
        override val id = 12
        override val title = "Fkj & Masego - Tadow"
        override val postImage = URL("https://i.ytimg.com/vi/hC8CH0Z3L54/default.jpg")
        override val postUrl: String = "https://www.youtube.com/watch?v=hC8CH0Z3L54"
        override val postGenres = postGenresList
    }

    /** Data for complete FeedPost */
    return object : FeedPost {
        override val feedPostId = 12
        override val feedPostDate = 1554478013
        override val feedPostUser = userPost
        override val feedPostData = post
        override val feedPostStars = postStarsList
        override val feedPostVotes = postVotesList
    }
}

@Suppress("MaxLineLength", "MagicNumber")
fun getTestFeedPostUserStarred(): FeedPost {
    val postGenresList = listOf<FeedPost.Post.PostGenre>()
    val postStarsList = emptyList<FeedPost.Star>()
    val postVotesList = emptyList<FeedPost.Vote>()

    /** Data for Post Votes */
    val vote = object : FeedPost.Vote {
        override val id = 2
        override val upvote = 1
        override val dateVote = 1555096253
        override val userVote = object : FeedPost.Vote.UserVote {
            override val id = 1
            override val username = "Alexia C Dura"
            override val imageUrl: URL? = null
        }
        override val feedPostIdVote = 8
    }
    postVotesList.plus(vote)

    /** Data for Post Stars */
    val star = object : FeedPost.Star {
        override val id = 2
        override val dateStar = 1554478013
        override val feedPostIdStar = 8
        override val userStar = object : FeedPost.Star.UserStar {
            override val id = 1
            override val username = "Alexia C Dura"
            override val imageUrl: URL? = null
        }
    }
    postStarsList.plus(star)

    /** Data for Post and genres */
    val bluesMusic = object : FeedPost.Post.PostGenre.Genre {
        override val id = 77
        override val name = "Blues Music"
        override val style = object : FeedPost.Post.PostGenre.Genre.Style {
            override val id = 5
            override val name = "Blues"
        }
    }
    val soulBlues = object : FeedPost.Post.PostGenre.Genre {
        override val id = 101
        override val name = "Soul Blues"
        override val style = object : FeedPost.Post.PostGenre.Genre.Style {
            override val id = 5
            override val name = "Blues"
        }
    }

    val soulMusic = object : FeedPost.Post.PostGenre.Genre {
        override val id = 439
        override val name = "Soul Music"
        override val style = object : FeedPost.Post.PostGenre.Genre.Style {
            override val id = 31
            override val name = "Soul"
        }
    }

    val soulBlues2 = object : FeedPost.Post.PostGenre.Genre {
        override val id = 445
        override val name = "Soul Blues"
        override val style = object : FeedPost.Post.PostGenre.Genre.Style {
            override val id = 31
            override val name = "Soul"
        }
    }

    postGenresList.plus(bluesMusic)
    postGenresList.plus(soulBlues)
    postGenresList.plus(soulMusic)
    postGenresList.plus(soulBlues2)

    val post = object : FeedPost.Post {
        override val id = 11
        override val title = "Gary B.B. Coleman - The Sky is Crying"
        override val postImage = URL("https://i.ytimg.com/vi/71Gt46aX9Z4/default.jpg")
        override val postUrl: String = "https://www.youtube.com/watch?v=71Gt46aX9Z4"
        override val postGenres = postGenresList
    }

    /** Data for UserEntity post */
    val userPost = object : FeedPost.UserPost {
        override val id = 1
        override val username = "Alexia C Dura"
        override val imageUrl: URL? = null
    }

    /** Data for complete FeedPost */
    return object : FeedPost {
        override val feedPostId = 8
        override val feedPostDate = 1555803708
        override val feedPostUser = userPost
        override val feedPostData = post
        override val feedPostStars = postStarsList
        override val feedPostVotes = postVotesList
    }
}

@Suppress("MaxLineLength", "MagicNumber")
fun getTestUserData(): UserData {
    val followingsList = listOf<UserData.User>()
    val followersList = listOf<UserData.User>()

    val following = object : UserData.User {
        override val id = 2
        override val username = "Mircea Teodor Oprea"
        override val email = "mirceateodor.oprea@gmail.com"
        override val imageUrl: URL? = null
    }
    followingsList.plus(following)
    followingsList.plus(following)

    followersList.plus(following)

    return object : UserData {
        override val postQuantity = 8
        override val starredQuantity = 3
        override val followings = followingsList
        override val followers = followersList
    }
}

@Suppress("MaxLineLength", "MagicNumber")
fun getTestUser(): User {
    val stylesList = listOf<User.Style>()

    val house = object : User.Style {
        override val id = 19
        override val name = "house"
    }
    val dance = object : User.Style {
        override val id = 20
        override val name = "Dance"
    }
    val techno = object : User.Style {
        override val id = 22
        override val name = "Techno"
    }
    val pop = object : User.Style {
        override val id = 29
        override val name = "Pop"
    }
    stylesList.plus(house)
    stylesList.plus(dance)
    stylesList.plus(techno)
    stylesList.plus(pop)

    return object : User {
        override val id = 2
        override val username = "Mircea Teodor Oprea"
        override val email = "mirceateodor.oprea@gmail.com"
        override val imageUrl: URL? = null
        override val likedStyles = stylesList
    }
}
