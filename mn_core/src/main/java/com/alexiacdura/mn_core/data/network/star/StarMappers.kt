package com.alexiacdura.mn_core.data.network.star

import com.alexiacdura.mn_core.data.models.StarResponse
import com.alexiacdura.mn_core.data.network.entities.StarResponseEntity

/** FeedPost mapping */
@Suppress("ThrowsCount")
internal fun StarResponseEntity.createStarResponse(): StarResponse {
    return object : StarResponse {
        override val id = this@createStarResponse.idStar
        override val dateStar = this@createStarResponse.dateStar
        override val feedPostIdStar = this@createStarResponse.feedPostIdStar
        override val userIdStar = this@createStarResponse.userIdStar
    }
}



