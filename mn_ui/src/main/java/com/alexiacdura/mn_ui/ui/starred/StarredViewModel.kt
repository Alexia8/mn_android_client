package com.alexiacdura.mn_ui.ui.starred

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import com.alexiacdura.mn_core.data.feed.FeedEvent
import com.alexiacdura.mn_core.data.feed.FeedInteractor
import com.alexiacdura.mn_core.data.feed.FeedState
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import com.alexiacdura.mn_ui.MusicnerdsRouter
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.utils.resources.AppConstants
import com.alexiacdura.mn_ui.core.viewmodel.RxAndroidViewModel
import com.alexiacdura.mn_ui.ui.components.error.ErrorViewModel
import com.alexiacdura.mn_ui.ui.components.feedpost.FeedPostsViewModelImpl
import com.alexiacdura.mn_ui.ui.components.loading.LoadingLayoutViewModel
import io.reactivex.subjects.PublishSubject

internal class StarredViewModel(
    private val interactor: FeedInteractor,
    application: Application,
    private val router: MusicnerdsRouter,
    private val schedulersProvider: SchedulersProvider
) : RxAndroidViewModel(application) {

    private val starredPublisher = PublishSubject.create<FeedEvent>()
    private var userId: Int = 0

    val viewsForFeedLoadingVisible = MutableLiveData<Boolean>()
    val viewsForFeedErrorVisible = MutableLiveData<Boolean>()
    val feedVisible = MutableLiveData<Boolean>()
    val loadingLatestVisible = MutableLiveData<Boolean>()

    val errorViewModel = MutableLiveData<ErrorViewModel>().apply { value = ErrorViewModel() }
    val loadingViewModel = MutableLiveData<LoadingLayoutViewModel>().apply { value = LoadingLayoutViewModel() }

    val starredPostsViewModel = FeedPostsViewModelImpl(::userImageCallback, ::userNameCallback)

    fun init(userId: Int) {
        this.userId = userId

        val subscriber = interactor
            .createFeedStateObservable(starredPublisher, userId, AppConstants.LIMFROM, AppConstants.LIMTO)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleFeedStates, ::showError)
        registerDisposable(subscriber)
    }

    fun starredStart() {
        starredPublisher.onNext(FeedEvent.Starred)
    }

    fun loadMore() {
        starredPublisher.onNext(FeedEvent.LoadMoreStarred(starredPostsViewModel.items.size))
    }

    private fun handleFeedStates(state: FeedState) {
        when (state) {
            is FeedState.Loading -> updateVisibilityState(loadingVisible = true)
            is FeedState.Error -> showError(state.throwable)
            is FeedState.Starred.SuccessInitial -> handleStarredInitialState(state.userStarredState)
            is FeedState.LoadingMore -> updateVisibilityState(
                loadingVisible = false,
                feedVisible = true,
                loadingLatestVisible = true
            )
            is FeedState.Starred.SuccessMore -> handleMorePostsLoaded(state.userStarredState)
            is FeedState.Starred.SuccessEnd -> updateVisibilityState(loadingLatestVisible = false)
        }
    }

    private fun handleStarredInitialState(userStarredState: UserStarredState) {
        starredPostsViewModel.update(userStarredState)
        updateVisibilityState(loadingVisible = false, feedVisible = true)
    }

    private fun handleMorePostsLoaded(userStarredState: UserStarredState) {
        updateVisibilityState(
            loadingVisible = false,
            errorVisible = false,
            feedVisible = true,
            loadingLatestVisible = false
        )
        starredPostsViewModel.update(userStarredState)
    }

    private fun showError(throwable: Throwable) {
        // MusicnerdsLog.crashlytics(throwable)

        val application = getApplication<Application>()
        errorViewModel.postValue(
            ErrorViewModel(
                title = application.getString(R.string.title_error),
                description = application.getString(R.string.text_error),
                actionButtonVisibility = true,
                callback = { starredStart() }
            )
        )
        updateVisibilityState(errorVisible = true)
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
        router.openProfileFragment(user.id)
    }

    private fun userNameCallback(user: FeedPost.UserPost) {
        router.openProfileFragment(user.id)
    }
}

