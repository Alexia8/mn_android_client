package com.alexiacdura.mn_ui.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import com.alexiacdura.mn_core.data.feed.FeedEvent
import com.alexiacdura.mn_core.data.feed.FeedState
import com.alexiacdura.mn_core.data.feed.UserPostsInteractor
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.models.UserData
import com.alexiacdura.mn_core.data.user.states.UserDataState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_ui.MusicnerdsRouter
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.utils.resources.AppConstants
import com.alexiacdura.mn_ui.core.viewmodel.RxAndroidViewModel
import com.alexiacdura.mn_ui.ui.components.error.ErrorViewModel
import com.alexiacdura.mn_ui.ui.components.feedpost.FeedPostsViewModelImpl
import com.alexiacdura.mn_ui.ui.components.loading.LoadingLayoutViewModel
import com.alexiacdura.mn_ui.ui.components.user.UserCardViewModelImpl
import io.reactivex.subjects.PublishSubject

internal class ProfileViewModel(
    private val interactor: UserPostsInteractor,
    application: Application,
    private val router: MusicnerdsRouter,
    private val schedulersProvider: SchedulersProvider
) : RxAndroidViewModel(application) {

    private val profilePublisher = PublishSubject.create<FeedEvent>()
    private var userId: Int = 0

    val viewsForFeedLoadingVisible = MutableLiveData<Boolean>()
    val viewsForFeedErrorVisible = MutableLiveData<Boolean>()
    val feedVisible = MutableLiveData<Boolean>()
    val loadingLatestVisible = MutableLiveData<Boolean>()

    val errorViewModel = MutableLiveData<ErrorViewModel>().apply { value = ErrorViewModel() }
    val loadingViewModel = MutableLiveData<LoadingLayoutViewModel>().apply { value = LoadingLayoutViewModel() }

    val userPostsViewModel = FeedPostsViewModelImpl(
        ::userImageCallback,
        ::userNameCallback,
        ::clickStarTextCallback,
        ::clickVoteTextCallback,
        ::clickDownVoteTextCallback
    )
    val userCardViewModel = UserCardViewModelImpl(::followersCallback, ::followingsCallback)

    fun init(userId: Int) {
        this.userId = userId

        val subscriber = interactor
            .createFeedStateObservable(profilePublisher, userId, AppConstants.LIMFROM, AppConstants.LIMTO)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleFeedStates, ::showError)
        registerDisposable(subscriber)
    }

    fun profileStart() {
        profilePublisher.onNext(FeedEvent.UserPosts)
    }

    fun loadMore() {
        profilePublisher.onNext(FeedEvent.LoadMoreProfile(userPostsViewModel.items.size))
    }

    private fun handleFeedStates(state: FeedState) {
        when (state) {
            is FeedState.Loading -> updateVisibilityState(loadingVisible = true)
            is FeedState.Error -> showError(state.throwable)
            is FeedState.UserPosts.SuccessInitial -> handleUserPostsInitialState(
                state.userPostsState,
                state.userDataState
            )
            is FeedState.LoadingMore -> updateVisibilityState(
                loadingVisible = false,
                feedVisible = true,
                loadingLatestVisible = true
            )
            is FeedState.UserPosts.SuccessMore -> handleMorePostsLoaded(state.userPostsState)
            is FeedState.UserPosts.SuccessEnd -> updateVisibilityState(loadingLatestVisible = false)
        }
    }

    private fun handleUserPostsInitialState(userPostsState: UserPostsState, userDataState: UserDataState) {
        userPostsViewModel.update(userPostsState)
        userCardViewModel.update(userDataState)
        updateVisibilityState(loadingVisible = false, feedVisible = true)
    }

    private fun handleMorePostsLoaded(userPostsState: UserPostsState) {
        updateVisibilityState(
            loadingVisible = false,
            errorVisible = false,
            feedVisible = true,
            loadingLatestVisible = false
        )
        userPostsViewModel.update(userPostsState)
    }

    private fun showError(throwable: Throwable) {
        // MusicnerdsLog.crashlytics(throwable)

        val application = getApplication<Application>()
        errorViewModel.postValue(
            ErrorViewModel(
                title = application.getString(R.string.title_error),
                description = application.getString(R.string.text_error),
                actionButtonVisibility = true,
                callback = { profileStart() }
            )
        )
        updateVisibilityState(errorVisible = true)
        Log.d("ProfileViewModel", "error in call: " + throwable.printStackTrace())
    }

    @Suppress("LongParameterList")
    private fun updateVisibilityState(
        loadingVisible: Boolean = false,
        errorVisible: Boolean = false,
        feedVisible: Boolean = false,
        loadingLatestVisible: Boolean = false
    ) {
        this.viewsForFeedLoadingVisible.value = loadingVisible
        this.viewsForFeedErrorVisible.value = errorVisible
        this.feedVisible.value = feedVisible
        this.loadingLatestVisible.value = loadingLatestVisible
    }

    private fun userImageCallback(user: FeedPost.UserPost) {
        if (this.userId != user.id) {
            router.openProfileFragment(user.id)
        }
    }

    private fun userNameCallback(user: FeedPost.UserPost) {
        if (this.userId != user.id) {
            router.openProfileFragment(user.id)
        }
    }

    private fun followersCallback(followesList: List<UserData.UserFollow>) {
        Log.d("ProfileViewModel", "Followers click")
    }

    private fun followingsCallback(followingsList: List<UserData.UserFollow>) {
        Log.d("ProfileViewModel", "Following click")
    }

    private fun clickStarTextCallback(starList: List<FeedPost.Star>) {
        Log.d("ProfileViewModel", " clicked on star list")
    }

    private fun clickVoteTextCallback(votesList: List<FeedPost.Vote>) {
        Log.d("ProfileViewModel", " clicked on upVotes list")
    }

    private fun clickDownVoteTextCallback(downVotesList: List<FeedPost.Vote>) {
        Log.d("ProfileViewModel", " clicked on downVotes list")
    }
}

