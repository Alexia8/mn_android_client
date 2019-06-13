package com.alexiacdura.mn_ui.ui.components.post.header

import android.content.Context
import androidx.cardview.widget.CardView
import android.util.AttributeSet
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewPostHeaderBinding
import com.squareup.picasso.Picasso

class HeaderCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr),
    BindableView<HeaderCardViewModel> {

    var viewModel: HeaderCardViewModel by viewModel(HeaderCardViewModel(null))

    private val binding: ViewPostHeaderBinding by viewBinding(resId = R.layout.view_post_header)

    init {
        configureBinding(viewModel)

        Picasso.get().load(viewModel.postImage.toString()).into(binding.bodyPostImage)
    }

    override fun bind(viewModel: HeaderCardViewModel) {
        configureBinding(viewModel)
    }

    private fun configureBinding(cardViewModel: HeaderCardViewModel) {
        binding.headerViewModel = cardViewModel
    }
}