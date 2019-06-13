package com.alexiacdura.mn_core.data.network.user

import com.alexiacdura.mn_core.data.network.entities.UserEntity
import com.alexiacdura.mn_core.data.network.entities.FeedPostEntity
import com.alexiacdura.mn_core.data.network.entities.UserDataEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


internal interface UserApiService {
    companion object {
        const val API_PATH = "api/Users"
    }

    /** Retrieve user with liked styles*/
    @GET("$API_PATH/{userEmail}")
    fun getUserByEmail(
        @Path("userEmail") userEmail: String
    ): Single<UserEntity>

    /** Retrieve user with feed posts sorted from backend*/
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("$API_PATH/{user}/userFeed")
    fun getUserFeed(
        @Path("user") user: Int,
        @Query("limFrom") limFrom: Int,
        @Query("limTo") limTo: Int
    ): Single<List<FeedPostEntity>>

    /** Retrieve user with starred posts sorted from backend*/
    @GET("$API_PATH/{user}/userRecentStarredPosts")
    fun getUserStarredPosts(
        @Path("user") user: Int,
        @Query("limFrom") limFrom: Int,
        @Query("limTo") limTo: Int
    ): Single<List<FeedPostEntity>>

    /** Retrieve user posts sorted from backend*/
    @GET("$API_PATH/{user}/userRecentPosts")
    fun getUserRecentPosts(
        @Path("user") user: Int,
        @Query("limFrom") limFrom: Int,
        @Query("limTo") limTo: Int
    ): Single<List<FeedPostEntity>>

    /** Retrieve user data, including posts, stars quantity and followings/followers objects*/
    @GET("/{userId}")
    fun getUserData(
        @Path("userId") userId: Int
    ): Single<UserDataEntity>
}