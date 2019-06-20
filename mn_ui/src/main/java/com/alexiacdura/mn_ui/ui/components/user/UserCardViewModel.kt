package com.alexiacdura.mn_ui.ui.components.user

import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.alexiacdura.mn_core.data.models.User
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.user.states.UserDataState

interface UserCardViewModel : Observable {

    @get:Bindable
    val contentsInvisible: Boolean

    @get:Bindable
    val loadingVisible: Boolean

    @get:Bindable
    var user: UserData.User

    @get:Bindable
    var postQuantity: String

    @get:Bindable
    var starredQuantity: String

    @get:Bindable
    var followersList: List<UserData.UserFollow>

    @get:Bindable
    var followingsList: List<UserData.UserFollow>

    fun followersClick(followersList: List<UserData.UserFollow>)
    fun followingsClick(followingsList: List<UserData.UserFollow>)

    fun update(state: UserDataState)
}