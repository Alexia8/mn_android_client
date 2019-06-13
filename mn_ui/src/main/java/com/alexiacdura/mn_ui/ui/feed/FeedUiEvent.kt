package com.alexiacdura.mn_ui.ui.feed

sealed class FeedUiEvent {
    object Feed : FeedUiEvent()
    object UiLoaded : FeedUiEvent()
}