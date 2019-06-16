package com.alexiacdura.mn_ui.ui.components.user

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.databinding.BaseObservable
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewUserDataBinding

class UserCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr),
    BindableView<UserCardViewModel> {

    var viewModel: UserCardViewModel by viewModel(EmptyViewModel())

    private val binding: ViewUserDataBinding by viewBinding(resId = R.layout.view_user_data)

    override fun bind(viewModel: UserCardViewModel) {
        binding.viewModel = viewModel

        binding.followers.text = viewModel.followersList.size.toString()
        binding.followings.text = viewModel.followingsList.size.toString()
    }

    private class EmptyViewModel : BaseObservable(), UserCardViewModel {

        override val contentsInvisible = false
        override val loadingVisible = false
        override var username = ""
        override var postQuantity = ""
        override var starredQuantity = ""
        override var followersList: List<UserData.User> = emptyList()
        override var followingsList: List<UserData.User> = emptyList()

        override fun update(state: UserDataState) {
            // don't do anything
        }

        override fun followersClick(followersList: List<UserData.User>) {
            // don't do anything
        }

        override fun followingsClick(followingsList: List<UserData.User>) {
            // don't do anything
        }

    }
}