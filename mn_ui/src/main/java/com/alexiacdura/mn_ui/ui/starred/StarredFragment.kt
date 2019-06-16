package com.alexiacdura.mn_ui.ui.starred

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.alexiacdura.mn_ui.BR
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindingViewInflater
import com.alexiacdura.mn_ui.databinding.FragmentStarredBinding
import org.koin.android.viewmodel.ext.android.viewModel

internal class StarredFragment : Fragment() {

    val viewModel by viewModel<StarredViewModel>()
    private lateinit var binding: FragmentStarredBinding

    private val args: StarredFragmentArgs by navArgs()

    private val fragmentInflater = BindingViewInflater(resId = R.layout.fragment_starred)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setupBinding(inflater.context, container)

        binding.feedPostsView.setOnLoadMoreCallback(::onLoadMore)

        viewModel.init(args.userId)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // viewModel.postEvent(FeedUiEvent.Starred)
        viewModel.starredStart()
    }

    private fun onLoadMore() {
        viewModel.loadMore()
    }

    private fun setupBinding(context: Context, container: ViewGroup?) {
        binding = fragmentInflater.inflate(context, container)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }
}
