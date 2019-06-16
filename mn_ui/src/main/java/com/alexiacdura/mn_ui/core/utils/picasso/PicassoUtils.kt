package com.alexiacdura.mn_ui.core.utils.picasso

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.net.URL

/**
 *  Load an image from a url and apply a circular transform to it by using Picasso. The function
 *  accepts a reference to another function that can retrieve a placeholder [Drawable]
 */
internal fun ImageView.loadCircleImageFrom(url: URL?, loadPlaceholder: () -> Drawable?) {
    val imageUrl = url?.toExternalForm() + "?w=" + width
    val creator = Picasso.get().load(imageUrl)

    loadPlaceholder()?.let {
        creator.placeholder(it)
    }

    creator.transform(CircleTransform()).into(this)
}