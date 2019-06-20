package com.alexiacdura.mn_ui.ui.components.post.header

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewPostHeaderBinding
import com.alexiacdura.mn_ui.ui.components.post.footer.FooterCardViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.view_post_header.view.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class HeaderCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr),
    BindableView<HeaderCardViewModel> {

    var viewModel: HeaderCardViewModel by viewModel(
        HeaderCardViewModel(feedPost = null, footerCardViewModel = FooterCardViewModel(feedPost = null))
    )

    private val binding: ViewPostHeaderBinding by viewBinding(resId = R.layout.view_post_header)

    override fun bind(viewModel: HeaderCardViewModel) {
        binding.headerViewModel = viewModel

        loadImage()

        loadPostVideo()

        convertDate()
    }

    private fun loadImage() {
        Picasso.get()
            .load(viewModel.userImage.toString())
            .transform(CropCircleTransformation())
            .placeholder(context.resources.getDrawable(R.drawable.ic_default_user_icon, context.theme))
            .into(binding.userImage)
    }

    private fun loadPostVideo() {

        val youTubeListener: YouTubePlayerListener = object : YouTubePlayerListener {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = viewModel.postUrl!!.substringAfter("=", "=")
                youTubePlayer.cueVideo(videoId, 0F)
                binding.bodyPostImage.visibility = View.INVISIBLE
                binding.youtubeView.visibility = View.VISIBLE
            }

            override fun onApiChange(youTubePlayer: YouTubePlayer) {
                //nothing for now
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                //nothing for now
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                //nothing for now
            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
                //nothing for now
            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {
                //nothing for now
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                //nothing for now
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                //nothing for now
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

            }

            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
                //nothing for now
            }
        }

        youtubeView.addYouTubePlayerListener(youTubePlayerListener = youTubeListener)
    }

    private fun convertDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewModel.datePost = Instant.ofEpochSecond(viewModel.datePost.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm"))
                .toString()
        }
    }
}