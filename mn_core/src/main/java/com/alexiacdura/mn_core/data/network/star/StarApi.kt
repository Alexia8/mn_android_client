package com.alexiacdura.mn_core.data.network.star

import com.alexiacdura.mn_core.data.models.StarResponse
import com.alexiacdura.mn_core.data.network.entities.insert.Star
import io.reactivex.Single

internal interface StarApi {
    fun createStar(star: Star): Single<StarResponse>

    fun deleteStar(starId: Int): Single<StarResponse>
}