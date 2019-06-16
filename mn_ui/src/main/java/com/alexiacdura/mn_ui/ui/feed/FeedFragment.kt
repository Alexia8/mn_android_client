package com.alexiacdura.mn_ui.ui.feed

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
import com.alexiacdura.mn_ui.databinding.FragmentFeedBinding
import org.koin.android.viewmodel.ext.android.viewModel

internal class FeedFragment : Fragment() {

    val viewModel by viewModel<FeedViewModel>()
    private lateinit var binding: FragmentFeedBinding

    private val args: FeedFragmentArgs by navArgs()

    private val fragmentInflater = BindingViewInflater(resId = R.layout.fragment_feed)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setupBinding(inflater.context, container)

        binding.feedPostsView.setOnLoadMoreCallback(::onLoadMore)

        viewModel.init(getUserId())

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.feedStart()
    }

    private fun onLoadMore() {
        viewModel.loadMore()
    }

    private fun setupBinding(context: Context, container: ViewGroup?) {
        binding = fragmentInflater.inflate(context, container)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }

    private fun getUserId(): Int {
        return arguments?.getInt(USERID_ARGUMENT) ?: 0
    }

    companion object {

        private const val USERID_ARGUMENT = "userId"

        fun newInstance(userId: Int): FeedFragment {
            val fragment = FeedFragment()

            val arguments = Bundle()
            arguments.putInt(USERID_ARGUMENT, userId)
            fragment.arguments = arguments

            return fragment
        }
    }
}
