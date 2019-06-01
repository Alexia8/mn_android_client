package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.data.user.states.UserState
import io.reactivex.Observable

interface UserInteractor {
    fun getUserByEmail(userEmail: String): Observable<UserState>
}