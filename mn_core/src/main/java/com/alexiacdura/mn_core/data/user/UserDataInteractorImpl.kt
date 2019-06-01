package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.user.UserApi
import com.alexiacdura.mn_core.data.user.states.UserDataState
import io.reactivex.Observable

internal class UserDataInteractorImpl(private val userApi: UserApi) : UserDataInteractor {

    override fun getUserData(userId: Int): Observable<UserDataState> {
        return userApi
            .getUserData(userId)
            .toObservable()
            .map { UserDataState.Success(it) as UserDataState }
            .onErrorReturn {
                UserDataState.Error(it)
            }
            .startWith(UserDataState.Loading)
    }
}