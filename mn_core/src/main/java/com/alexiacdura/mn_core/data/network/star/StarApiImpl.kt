package com.alexiacdura.mn_core.data.network.star

import com.alexiacdura.mn_core.core.factory.ServiceFactory
import com.alexiacdura.mn_core.data.models.StarResponse
import com.alexiacdura.mn_core.data.network.entities.insert.Star
import io.reactivex.Single

internal class StarApiImpl(serviceFactory: ServiceFactory<StarApiService>) : StarApi {
    private val service: StarApiService = serviceFactory.createService()

    override fun createStar(star: Star): Single<StarResponse> {
        return service.createStar(star)
            .map { starResponseEntity -> starResponseEntity.createStarResponse() }
    }

    override fun deleteStar(starId: Int): Single<StarResponse> {
        return service.deleteStar(starId)
            .map { starResponseEntity -> starResponseEntity.createStarResponse() }
    }
}