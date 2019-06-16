package com.alexiacdura.mn_ui.ui.components.user

import androidx.databinding.BaseObservable
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_ui.BR
import com.alexiacdura.mn_ui.core.binding.bindableProperty
import kotlin.reflect.KFunction1

class UserCardViewModelImpl(
    private val followersCallback: KFunction1<@ParameterName(name = "user") List<UserData.User>, Unit>,
    private val followingsCallback: KFunction1<@ParameterName(name = "user") List<UserData.User>, Unit>
) : BaseObservable(), UserCardViewModel {

    override var contentsInvisible by bindableProperty(false, BR.contentsInvisible)
        private set

    override var loadingVisible by bindableProperty(false, BR.loadingVisible)
        private set

    override var username by bindableProperty("", BR.username)

    override var postQuantity by bindableProperty("", BR.postQuantity)

    override var starredQuantity by bindableProperty("", BR.starredQuantity)

    override var followersList: List<UserData.User> = emptyList()

    override var followingsList: List<UserData.User> = emptyList()

    override fun followersClick(followersList: List<UserData.User>) {
        followersCallback(followersList)
    }

    override fun followingsClick(followingsList: List<UserData.User>) {
        followingsCallback(followingsList)
    }

    override fun update(state: UserDataState) {
        when (state) {
            is UserDataState.Success -> processSuccessState(state.item)
            is UserDataState.Loading -> showLoading()
            is UserDataState.Error -> hideAll()
        }
    }

    private fun processSuccessState(item: UserData) {
        this.username = ""
        this.postQuantity = Integer.toString(item.postQuantity)
        this.starredQuantity = Integer.toString(item.starredQuantity)
        this.followersList = item.followers
        this.followingsList = item.followings

        showContents()
        notifyChange()
    }

    private fun showLoading() {
        loadingVisible = true
        contentsInvisible = true
    }

    private fun showContents() {
        contentsInvisible = false
        loadingVisible = false
    }

    private fun hideAll() {
        loadingVisible = false
        contentsInvisible = false
    }
}