package com.alexiacdura.mn_ui.ui.feed

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import com.alexiacdura.mn_core.data.feed.FeedEvent
import com.alexiacdura.mn_core.data.feed.FeedInteractor
import com.alexiacdura.mn_core.data.feed.FeedState
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_ui.MusicnerdsRouter
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.utils.resources.AppConstants
import com.alexiacdura.mn_ui.core.viewmodel.RxAndroidViewModel
import com.alexiacdura.mn_ui.ui.components.error.ErrorViewModel
import com.alexiacdura.mn_ui.ui.components.feedpost.FeedPostsViewModelImpl
import com.alexiacdura.mn_ui.ui.components.loading.LoadingLayoutViewModel
import io.reactivex.subjects.PublishSubject

internal class FeedViewModel(
    private val interactor: FeedInteractor,
    application: Application,
    private val router: MusicnerdsRouter,
    private val schedulersProvider: SchedulersProvider
) : RxAndroidViewModel(application) {

    private val feedPublisher = PublishSubject.create<FeedEvent>()
    private var userId: Int = 0

    val viewsForFeedLoadingVisible = MutableLiveData<Boolean>()
    val viewsForFeedErrorVisible = MutableLiveData<Boolean>()
    val feedVisible = MutableLiveData<Boolean>()
    val loadingLatestVisible = MutableLiveData<Boolean>()

    val errorViewModel = MutableLiveData<ErrorViewModel>().apply { value = ErrorViewModel() }
    val loadingViewModel = MutableLiveData<LoadingLayoutViewModel>().apply { value = LoadingLayoutViewModel() }

    val feedPostsViewModel = FeedPostsViewModelImpl(::userImageCallback, ::userNameCallback)


    fun init(userId: Int) {
        this.userId = userId

        val subscriber = interactor
            .createFeedStateObservable(feedPublisher, userId, AppConstants.LIMFROM, AppConstants.LIMTO)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleFeedStates, ::showError)
        registerDisposable(subscriber)
    }

    fun feedStart() {
        feedPublisher.onNext(FeedEvent.Feed)
    }

    fun loadMore() {
        feedPublisher.onNext(FeedEvent.LoadMoreFeed(feedPostsViewModel.items.size))
    }


    private fun handleFeedStates(state: FeedState) {
        when (state) {
            is FeedState.Loading -> updateVisibilityState(loadingVisible = true)
            is FeedState.Error -> showError(state.throwable)
            is FeedState.Feed.SuccessInitial -> handleFeedInitialState(state.userFeedState)
            is FeedState.LoadingMore -> updateVisibilityState(
                loadingVisible = false,
                feedVisible = true,
                loadingLatestVisible = true
            )
            is FeedState.Feed.SuccessMore -> handleMorePostsLoaded(state.userFeedState)
            is FeedState.Feed.SuccessEnd -> updateVisibilityState(loadingLatestVisible = false)
        }
    }

    private fun handleFeedInitialState(userFeedState: UserFeedState) {
        feedPostsViewModel.update(userFeedState)
        updateVisibilityState(loadingVisible = false, feedVisible = true)
    }

    private fun handleMorePostsLoaded(userFeedState: UserFeedState) {
        updateVisibilityState(
            loadingVisible = false,
            errorVisible = false,
            feedVisible = true,
            loadingLatestVisible = false
        )
        feedPostsViewModel.update(userFeedState)
    }

    private fun showError(throwable: Throwable) {
        // MusicnerdsLog.crashlytics(throwable)

        val application = getApplication<Application>()
        errorViewModel.postValue(
            ErrorViewModel(
                title = application.getString(R.string.title_error),
                description = application.getString(R.string.text_error),
                actionButtonVisibility = true,
                callback = { feedStart() }
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
        if (this.userId != user.id) {
            router.openProfileFragment(user.id)
        }
    }

    private fun userNameCallback(user: FeedPost.UserPost) {
        if (this.userId != user.id) {
            router.openProfileFragment(user.id)
        }
    }
}

