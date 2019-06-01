package com.alexiacdura.mn_core.data.network.user

import com.alexiacdura.mn_core.core.factory.ServiceFactory
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.models.User
import com.alexiacdura.mn_core.data.models.UserData
import io.reactivex.Single

internal class UserApiImpl(serviceFactory: ServiceFactory<UserApiService>) : UserApi {
    private val service: UserApiService = serviceFactory.createService()

    override fun getUserByEmail(email: String): Single<User> {
        return service.getUserByEmail(email)
            .map { userEntity -> userEntity.toUser() }
    }

    override fun getUserFeed(userId: Int, limFrom: Int, limTo: Int): Single<List<FeedPost>> {
        return service.getUserFeed(userId, limFrom, limTo)
            .map { it.mapIgnoreException { entity -> entity.toFeedPost() } }
    }

    override fun getUserRecentPosts(userId: Int, limFrom: Int, limTo: Int): Single<List<FeedPost>> {
        return service.getUserRecentPosts(userId, limFrom, limTo)
            .map { it.mapIgnoreException { entity -> entity.toFeedPost() } }
    }

    override fun getUserStarredPosts(userId: Int, limFrom: Int, limTo: Int): Single<List<FeedPost>> {
        return service.getUserStarredPosts(userId, limFrom, limTo)
            .map { it.mapIgnoreException { entity -> entity.toFeedPost() } }
    }

    override fun getUserData(userId: Int): Single<UserData> {
        return service.getUserData(userId)
            .map { userDataEntity -> userDataEntity.toUserData() }
    }

    @Suppress("TooGenericExceptionCaught")
    private inline fun <T, R : Any> Iterable<T>.mapIgnoreException(transform: (T) -> R): List<R> {
        return mapNotNull {
            try {
                transform(it)
            } catch (ex: Exception) {
                null
            }
        }
    }
}