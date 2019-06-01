package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.star.states.DeleteStarState
import io.reactivex.Observable

interface DeleteStarInteractor {
    fun deleteStar(starId: Int): Observable<DeleteStarState>
}