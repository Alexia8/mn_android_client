package com.alexiacdura.mn_ui.ui.components.feedpost

import androidx.databinding.Bindable
import androidx.databinding.Observable

interface FeedPostsViewModel : Observable {

    @get:Bindable
    val contentVisible: Boolean

    @get:Bindable
    val loadingVisible: Boolean

    @get:Bindable
    val items: List<FeedPostsItemViewModel>

    fun update(state: Any)
}