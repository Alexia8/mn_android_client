package com.alexiacdura.mn_ui.ui.components.user

import androidx.databinding.BaseObservable
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_ui.BR
import com.alexiacdura.mn_ui.core.binding.bindableProperty
import java.net.URL
import kotlin.reflect.KFunction1

class UserCardViewModelImpl(
    private val followersCallback: KFunction1<@ParameterName(name = "user") List<UserData.UserFollow>, Unit>,
    private val followingsCallback: KFunction1<@ParameterName(name = "user") List<UserData.UserFollow>, Unit>
) : BaseObservable(), UserCardViewModel {

    override var contentsInvisible by bindableProperty(false, BR.contentsInvisible)
        private set

    override var loadingVisible by bindableProperty(false, BR.loadingVisible)
        private set

    override var user = object : UserData.User {
        override val id = 0
        override val username = ""
        override val email = ""
        override val imageUrl = URL("https://github.com/alexia8.png")
        override val styles: List<UserData.User.Style> = emptyList()
    }

    override var postQuantity by bindableProperty("", BR.postQuantity)

    override var starredQuantity by bindableProperty("", BR.starredQuantity)

    override var followersList: List<UserData.UserFollow> = emptyList()

    override var followingsList: List<UserData.UserFollow> = emptyList()

    override fun followersClick(followersList: List<UserData.UserFollow>) {
        followersCallback(followersList)
    }

    override fun followingsClick(followingsList: List<UserData.UserFollow>) {
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
        this.user = item.postUser
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