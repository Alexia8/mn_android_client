package com.alexiacdura.mn_ui.ui.feed

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import com.alexiacdura.mn_core.data.feed.FeedEvent
import com.alexiacdura.mn_core.data.feed.FeedInteractor
import com.alexiacdura.mn_core.data.feed.FeedState
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_ui.MusicnerdsRouter
import com.alexiacdura.mn_ui.R
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

    private val eventPublisher = PublishSubject.create<FeedEvent>()
    private var userId: Int? = null

    val viewsForFeedLoadingVisible = MutableLiveData<Boolean>()
    val viewsForFeedVisible = MutableLiveData<Boolean>()
    val viewsForFeedErrorVisible = MutableLiveData<Boolean>()
    val loadingLatestVisible = MutableLiveData<Boolean>()

    val errorViewModel = MutableLiveData<ErrorViewModel>().apply { value = ErrorViewModel() }
    val loadingViewModel = MutableLiveData<LoadingLayoutViewModel>().apply { value = LoadingLayoutViewModel() }

    val feedPostsViewModel = FeedPostsViewModelImpl()

    fun init(userId: Int) {
        this.userId = userId
        val subscriber = interactor
            .createFeedStateObservable(eventPublisher, userId)
            .observeOn(schedulersProvider.main())
            .subscribe(::handleFeedStates, ::showError)
        registerDisposable(subscriber)
    }

    fun feedStart() {
        eventPublisher.onNext(FeedEvent.Feed)
    }

    fun loadMore() {
        eventPublisher.onNext(FeedEvent.LoadMore(feedPostsViewModel.items.size))
    }

    private fun handleFeedStates(state: FeedState) {
        when (state) {
            is FeedState.Loading -> updateVisibilityState(loadingVisible = true)
            is FeedState.Error -> showError(state.throwable)
            is FeedState.Feed.SuccessInitial -> handleFeedInitialState(state.userFeedState)
            is FeedState.LoadingMore-> setLoadingMoreVisible(true)
            is FeedState.Feed.SuccessMore -> handleMorePostsLoaded(state.userFeedState)
            is FeedState.Feed.SuccessEnd -> setLoadingMoreVisible(false)
        }
    }

    private fun handleFeedInitialState(userFeedState: UserFeedState) {
        feedPostsViewModel.update(userFeedState)
        updateVisibilityState(feedVisible = true)
    }

    private fun handleMorePostsLoaded(userFeedState: UserFeedState) {
        viewsForFeedLoadingVisible.value = false
        viewsForFeedErrorVisible.value = false
        setLoadingMoreVisible(false)
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
        feedVisible: Boolean = false
    ) {
        this.viewsForFeedLoadingVisible.value = loadingVisible
        this.viewsForFeedErrorVisible.value = errorVisible
        this.viewsForFeedVisible.value = feedVisible
    }

    private fun setLoadingMoreVisible(visible: Boolean) {
        loadingLatestVisible.value = visible
    }

    private fun userImageCallback(user: FeedPost) {
        router.openProfileFragment(user.feedPostUser.id)
    }

    private fun userNameCallback(user: FeedPost) {
        router.openProfileFragment(user.feedPostUser.id)
    }

    private fun postImageCallback(post: FeedPost) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.feedPostData.postUrl))
    }
}

