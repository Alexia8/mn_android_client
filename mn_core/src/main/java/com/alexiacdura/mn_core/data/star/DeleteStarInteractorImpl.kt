package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.star.StarApi
import com.alexiacdura.mn_core.data.star.states.DeleteStarState
import io.reactivex.Observable

internal class DeleteStarInteractorImpl(private val starApi: StarApi) : DeleteStarInteractor {

    override fun deleteStar(starId: Int): Observable<DeleteStarState> {
        return starApi
            .deleteStar(starId)
            .toObservable()
            .map { DeleteStarState.Success(it) as DeleteStarState }
            .onErrorReturn {
                DeleteStarState.Error(it)
            }
            .startWith(DeleteStarState.Loading)
    }
}