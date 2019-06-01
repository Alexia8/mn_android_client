package com.alexiacdura.mn_core.data.network.user

import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.models.User
import com.alexiacdura.mn_core.data.models.UserData
import io.reactivex.Single

internal interface UserApi {
    fun getUserByEmail(email: String): Single<User>

    fun getUserRecentPosts(userId: Int, limFrom: Int, limTo: Int): Single<List<FeedPost>>

    fun getUserStarredPosts(userId: Int, limFrom: Int, limTo: Int): Single<List<FeedPost>>

    fun getUserFeed(userId: Int, limFrom: Int, limTo: Int): Single<List<FeedPost>>

    fun getUserData(userId: Int): Single<UserData>
}