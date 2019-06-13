package com.alexiacdura.mn_ui.ui.feed

import com.alexiacdura.mn_core.data.feed.FeedState

sealed class FeedUiState {
    class Updated(val feedState: FeedState) : FeedUiState()
    object UiLoaded : FeedUiState()
}