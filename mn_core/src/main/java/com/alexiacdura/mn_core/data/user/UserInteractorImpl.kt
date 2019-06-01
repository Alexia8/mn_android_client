package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.network.user.UserApi
import com.alexiacdura.mn_core.data.user.states.UserState
import io.reactivex.Observable

internal class UserInteractorImpl(private val userApi: UserApi) : UserInteractor {

    override fun getUserByEmail(userEmail: String): Observable<UserState> {
        return userApi
            .getUserByEmail(userEmail)
            .toObservable()
            .map { UserState.Success(it) as UserState }
            .onErrorReturn {
                UserState.Error(it)
            }
            .startWith(UserState.Loading)
    }
}