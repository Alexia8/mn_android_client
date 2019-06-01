package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.entities.insert.Star
import com.alexiacdura.mn_core.data.star.states.CreateStarState
import io.reactivex.Observable

interface CreateStarInteractor {
    fun createStar(star: Star): Observable<CreateStarState>
}