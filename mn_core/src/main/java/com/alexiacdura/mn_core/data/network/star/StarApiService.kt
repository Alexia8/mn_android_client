package com.alexiacdura.mn_core.data.network.star

import com.alexiacdura.mn_core.data.network.entities.insert.Star
import com.alexiacdura.mn_core.data.network.entities.StarResponseEntity
import io.reactivex.Single
import retrofit2.http.*

internal interface StarApiService {

    companion object {
        const val API_PATH = "api/Stars"
    }

    /** Create a star by user and feedPostId*/
    @Headers("Content-Type: application/json")
    @POST("$API_PATH")
    fun createStar(@Body star: Star): Single<StarResponseEntity>

    /** Delete Star by id*/
    @DELETE("$API_PATH/{starId}")
    fun deleteStar(
        @Query("starId") starId: Int
    ): Single<StarResponseEntity>
}