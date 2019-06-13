package com.alexiacdura.mn_ui.ui.components.error

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewCustomErrorBinding


class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    BindableView<ErrorViewModel> {

    var viewModel: ErrorViewModel by viewModel(ErrorViewModel())

    private val binding: ViewCustomErrorBinding by viewBinding(R.layout.view_custom_error)

    init {
        configureBinding(viewModel)
    }

    override fun bind(viewModel: ErrorViewModel) {
        configureBinding(viewModel)
    }

    private fun configureBinding(viewModel: ErrorViewModel) {
        binding.viewModel = viewModel
    }
}