package com.alexiacdura.mn_ui.ui.components.post.body

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewPostBodyCardBinding

class BodyCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr),
    BindableView<BodyCardViewModel> {

    var viewModel: BodyCardViewModel by viewModel(BodyCardViewModel(null))

    private val binding: ViewPostBodyCardBinding by viewBinding(resId = R.layout.view_post_body_card)

    init {
        configureBinding(viewModel)
    }

    override fun bind(viewModel: BodyCardViewModel) {
        configureBinding(viewModel)
    }

    private fun configureBinding(cardViewModel: BodyCardViewModel) {
        binding.viewModel = cardViewModel
    }
}