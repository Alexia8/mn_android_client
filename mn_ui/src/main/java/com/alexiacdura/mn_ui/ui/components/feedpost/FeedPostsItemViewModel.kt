package com.alexiacdura.mn_ui.ui.components.feedpost

import com.alexiacdura.mn_ui.BR
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.ui.adapters.any.AnyItem
import com.alexiacdura.mn_ui.ui.components.post.header.HeaderCardViewModel

class FeedPostsItemViewModel(
    val headerCardViewModel: HeaderCardViewModel
) : AnyItem {
    override fun getBindingGenerator() = AnyItem.BindingGenerator(R.layout.layout_feedposts_view_item, BR.viewModel)
}