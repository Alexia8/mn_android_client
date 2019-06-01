package com.alexiacdura.mn_core.data.user.states

import com.alexiacdura.mn_core.data.models.UserData

sealed class UserDataState {
    object Loading : UserDataState()
    data class Success(val item: UserData) : UserDataState()
    data class Error(val throwable: Throwable) : UserDataState()
}