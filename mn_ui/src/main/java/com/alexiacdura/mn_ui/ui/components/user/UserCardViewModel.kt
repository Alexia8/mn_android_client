package com.alexiacdura.mn_ui.ui.components.user

import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.user.states.UserDataState

interface UserCardViewModel : Observable {

    @get:Bindable
    val contentsInvisible: Boolean

    @get:Bindable
    val loadingVisible: Boolean

    @get:Bindable
    var username: String

    @get:Bindable
    var postQuantity: String

    @get:Bindable
    var starredQuantity: String

    @get:Bindable
    var followersList: List<UserData.User>

    @get:Bindable
    var followingsList: List<UserData.User>

    fun followersClick(followersList: List<UserData.User>)
    fun followingsClick(followingsList: List<UserData.User>)

    fun update(state: UserDataState)
}