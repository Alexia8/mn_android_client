package com.alexiacdura.mn_ui.ui.components.feedpost

import androidx.databinding.BaseObservable
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import com.alexiacdura.mn_ui.BR
import com.alexiacdura.mn_ui.core.binding.bindableProperty
import com.alexiacdura.mn_ui.ui.components.post.header.HeaderCardViewModel
import kotlin.reflect.KFunction1


class FeedPostsViewModelImpl(
    private var userImageCallback: KFunction1<@ParameterName(name = "user") FeedPost.UserPost, Unit>?,
    private var userNameCallback: KFunction1<@ParameterName(name = "user") FeedPost.UserPost, Unit>?
) : BaseObservable(), FeedPostsViewModel {

    override var contentVisible by bindableProperty(false, BR.contentVisible)
        private set

    override var loadingVisible by bindableProperty(false, BR.loadingVisible)
        private set

    override var items: List<FeedPostsItemViewModel> by bindableProperty(
        emptyList(),
        BR.items
    )
        private set

    override fun update(state: Any) {
        when (state) {
            is UserFeedState -> updateFeedView(state)
            is UserStarredState -> updateStarredView(state)
            is UserPostsState -> updateUserView(state)

        }
    }

    private fun updateFeedView(state: UserFeedState) {
        when (state) {
            is UserFeedState.Success -> processSuccessState(state.items)
            is UserFeedState.Loading -> showLoading()
            is UserFeedState.Error -> hideAll()
        }
    }

    private fun updateStarredView(state: UserStarredState) {
        when (state) {
            is UserStarredState.Success -> processSuccessState(state.items)
            is UserStarredState.Loading -> showLoading()
            is UserStarredState.Error -> hideAll()
        }
    }

    private fun updateUserView(state: UserPostsState) {
        when (state) {
            is UserPostsState.Success -> processSuccessState(state.items)
            is UserPostsState.Loading -> showLoading()
            is UserPostsState.Error -> hideAll()
        }
    }

    private fun processSuccessState(items: List<FeedPost>) {
        this.items += items.map { it.toViewModel() }
        showContents()
    }

    private fun showContents() {
        loadingVisible = false
        contentVisible = true
    }

    private fun showLoading() {
        loadingVisible = true
        contentVisible = false
    }

    private fun hideAll() {
        loadingVisible = false
        contentVisible = false
    }

    private fun FeedPost.toViewModel(): FeedPostsItemViewModel {
        return FeedPostsItemViewModel(
            HeaderCardViewModel(
                this,
                userImageCallback,
                userNameCallback
            )
        )
    }
}