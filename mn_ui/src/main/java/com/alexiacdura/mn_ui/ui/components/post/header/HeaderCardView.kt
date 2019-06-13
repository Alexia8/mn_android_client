package com.alexiacdura.mn_ui.ui.components.post.header

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.cardview.widget.CardView
import com.alexiacdura.mn_ui.R
import com.alexiacdura.mn_ui.core.binding.BindableView
import com.alexiacdura.mn_ui.core.binding.viewBinding
import com.alexiacdura.mn_ui.core.binding.viewModel
import com.alexiacdura.mn_ui.databinding.ViewPostHeaderBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HeaderCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr),
    BindableView<HeaderCardViewModel> {

    var viewModel: HeaderCardViewModel by viewModel(HeaderCardViewModel(null))

    private val binding: ViewPostHeaderBinding by viewBinding(resId = R.layout.view_post_header)

    override fun bind(viewModel: HeaderCardViewModel) {
        binding.headerViewModel = viewModel

        loadPostVideo()

        convertDate()
    }

    private fun loadPostVideo() {
        //Picasso.get().load(viewModel.postImage.toString()).into(binding.bodyPostImage) //for loading an image instead of video
        binding.youtubeWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        val webSettings = binding.youtubeWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        binding.youtubeWebView.loadUrl(viewModel.postUrl)

    }

    private fun convertDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewModel.datePost = Instant.ofEpochSecond(viewModel.datePost.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd / MM / yy"))
                .toString()
        }
    }
}