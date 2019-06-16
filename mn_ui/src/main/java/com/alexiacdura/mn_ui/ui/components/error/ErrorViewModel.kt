package com.alexiacdura.mn_ui.ui.components.error

import com.alexiacdura.mn_ui.R

data class ErrorViewModel(
    val title: String = "",
    val description: String = "",
    val icon: Int = R.drawable.ic_error_view,
    val iconVisibility: Boolean = false,
    val actionButtonText: Int = R.string.text_retry,
    val actionButtonVisibility: Boolean = false,
    private val callback: () -> Unit = {}
) {
    val titleVisibility = title.isNotEmpty()
    val descriptionVisibility = description.isNotEmpty()

    fun onActionButtonPressed() {
        callback()
    }
}