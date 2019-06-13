package com.alexiacdura.mn_ui.ui.components.loading

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewLoadingBinding

class LoadingLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    BindableView<LoadingLayoutViewModel> {

    var viewModel: LoadingLayoutViewModel by viewModel(LoadingLayoutViewModel())
    private var set: AnimatorSet? = null
    private var loadingIcon: ImageView? = null

    private val binding by viewBinding<ViewLoadingBinding>(resId = R.layout.view_loading)

    init {
        configureBinding(viewModel)

        set = AnimatorInflater.loadAnimator(context, R.animator.rotate_animation) as AnimatorSet
        setLoadingAnimation()
    }

    override fun bind(viewModel: LoadingLayoutViewModel) {
        configureBinding(viewModel)
    }

    private fun configureBinding(viewModel: LoadingLayoutViewModel) {
        binding.loadingViewModel = viewModel
    }

    private fun setLoadingAnimation() {
        loadingIcon = findViewById<View>(binding.loadingLayoutProgress.id) as ImageView
        set!!.setTarget(loadingIcon)
        set!!.start()
    }
}