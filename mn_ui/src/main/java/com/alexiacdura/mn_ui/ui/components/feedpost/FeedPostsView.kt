package com.alexiacdura.mn_ui.ui.components.feedpost

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.BaseObservable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewFeedpostsBinding
import com.alexiacdura.mn_ui.ui.adapters.any.AnyItemListCallback
import com.alexiacdura.mn_ui.ui.adapters.any.DefaultMutableAnyAdapter


class FeedPostsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val itemsBeforeLoadMore: Int = 2
) : FrameLayout(context, attrs, defStyleAttr),
    BindableView<FeedPostsViewModel> {

    var viewModel: FeedPostsViewModel by viewModel(EmptyViewModel())

    private val binding: ViewFeedpostsBinding by viewBinding(R.layout.view_feedposts)

    init {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.contentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            adapter = DefaultMutableAnyAdapter(diffCallbackBuilder = ::createCallback)
            setItemViewCacheSize(15)
        }

    }

    override fun bind(viewModel: FeedPostsViewModel) {
        binding.viewModel = viewModel
    }

    fun setOnLoadMoreCallback(callback: () -> Unit) {
        binding.contentsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount

                    if (totalItemCount > 0) {
                        val positionOfLastItem = layoutManager.findLastVisibleItemPosition()
                        if (positionOfLastItem + itemsBeforeLoadMore >= totalItemCount) {
                            callback()
                        }
                    }
                }
            }
        })
    }

    private fun createCallback(
        oldItems: List<FeedPostsItemViewModel>,
        newItems: List<FeedPostsItemViewModel>
    ): AnyItemListCallback<FeedPostsItemViewModel> {
        return FeedPostsDiffCallback(oldItems, newItems)
    }

    private class EmptyViewModel : BaseObservable(), FeedPostsViewModel {

        override val contentVisible = false
        override val loadingVisible = false
        override val items: List<FeedPostsItemViewModel> = emptyList()

        override fun update(state: Any) {
            // don't do anything
        }
    }

    private class FeedPostsDiffCallback(
        oldItems: List<FeedPostsItemViewModel>,
        newItems: List<FeedPostsItemViewModel>
    ) : AnyItemListCallback<FeedPostsItemViewModel>(oldItems, newItems) {
        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }
    }

}