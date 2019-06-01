package com.alexiacdura.mn_core.data.user.states

import com.alexiacdura.mn_core.data.models.User

sealed class UserState {
    object Loading : UserState()
    data class Success(val item: User) : UserState()
    data class Error(val throwable: Throwable) : UserState()
}