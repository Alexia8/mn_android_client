package com.alexiacdura.mn_core

import com.alexiacdura.mn_core.data.models.StarResponse

@Suppress("MaxLineLength", "MagicNumber")
fun getTestStarResponse(): StarResponse {
    return object : StarResponse {
        override val id = 4
        override val dateStar = 1555199813
        override val feedPostIdStar = 1
        override val userIdStar = 1
    }
}