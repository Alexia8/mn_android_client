package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.user.states.UserDataState
import io.reactivex.Observable

interface UserDataInteractor {
    fun getUserData(userId: Int): Observable<UserDataState>
}