package com.alexiacdura.mn_ui.ui.components.post.footer

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.alexiacdura.mn_core.data.models.FeedPost
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel

class FooterCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr),
    BindableView<FooterCardViewModel> {

    var viewModel: FooterCardViewModel by viewModel(
        FooterCardViewModel(feedPost = null)
    )

    var upVotesList: List<FeedPost.Vote> = emptyList()
    var downVotesList: List<FeedPost.Vote> = emptyList()

    private val binding: com.alexiacdura.mn_ui.databinding.ViewPostFooterBinding by viewBinding(resId = R.layout.view_post_footer)

    override fun bind(viewModel: FooterCardViewModel) {
        binding.footerViewModel = viewModel

        setVotesLists()

        setStarBtn()

        setVotesBtn()
    }

    private fun setVotesLists() {
        upVotesList = viewModel.votesList.filter { it.upvote == 1 }
        downVotesList = viewModel.votesList.filter { it.upvote == 0 }
    }

    private fun setStarBtn() {
        binding.starText.text = viewModel.starsList.size.toString()

        viewModel.starsList.forEach { star ->
            if (star.userStar.id == 1) {
                binding.starIcon.setColorFilter(R.color.colorPrimary)

            }
        }
    }

    private fun setVotesBtn() {
        binding.voteText.text = upVotesList.size.toString()
        binding.downVoteText.text = downVotesList.size.toString()

        upVotesList.forEach { vote ->
            if (vote.userVote.id == 1) {
                binding.voteIcon.setColorFilter(R.color.colorPrimary)
            }
        }

        downVotesList.forEach { vote ->
            if (vote.userVote.id == 1) {
                binding.downVoteIcon.setColorFilter(R.color.colorPrimary)
            }
        }
    }

}