package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.entities.insert.Star
import com.alexiacdura.mn_core.data.network.star.StarApi
import com.alexiacdura.mn_core.data.star.states.CreateStarState
import io.reactivex.Observable

internal class CreateStarInteractorImpl(private val starApi: StarApi) : CreateStarInteractor {

    override fun createStar(star: Star): Observable<CreateStarState> {
        return starApi
            .createStar(star)
            .toObservable()
            .map { CreateStarState.Success(it) as CreateStarState }
            .onErrorReturn {
                CreateStarState.Error(it)
            }
            .startWith(CreateStarState.Loading)
    }
}