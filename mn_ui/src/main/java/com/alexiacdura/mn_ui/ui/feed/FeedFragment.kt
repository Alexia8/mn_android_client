package com.alexiacdura.mn_ui.ui.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexiacdura.mn_ui.BR
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindingViewInflater
import com.alexiacdura.mn_ui.databinding.FragmentFeedBinding
import org.koin.android.viewmodel.ext.android.viewModel

internal class FeedFragment : Fragment() {

    val viewModel by viewModel<FeedViewModel>()
    private lateinit var binding: FragmentFeedBinding

    private val fragmentInflater = BindingViewInflater(resId = R.layout.fragment_feed)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setupBinding(inflater.context, container)

        binding.feedPostsView.setOnLoadMoreCallback(::onLoadMore)

        viewModel.init(getUser())

        viewModel.feedStart()

        return binding.root
    }

    private fun onLoadMore() {
        viewModel.loadMore()
    }

    private fun setupBinding(context: Context, container: ViewGroup?) {
        binding = fragmentInflater.inflate(context, container)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }

    private fun getUser(): Int {
        return arguments?.getInt("userId") ?: 1
    }
}
