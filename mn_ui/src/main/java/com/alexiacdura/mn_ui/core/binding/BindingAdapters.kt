package com.alexiacdura.mn_ui.core.binding

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.BindingAdapter
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.recyclerview.widget.ItemTouchHelper
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.widget.*
import com.squareup.picasso.Picasso

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("selected")
    fun setSelected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @JvmStatic
    @BindingAdapter("activated")
    fun setActivated(view: View, activated: Boolean) {
        view.isActivated = activated
    }

    /**
     * @param view
     * @param animate true will start anim. false stops it.
     */
    @JvmStatic
    @BindingAdapter("animateBackgroundDrawable")
    fun animateBackground(view: View, animate: Boolean) {
        (view.background as? AnimationDrawable)?.run {
            when {
                animate && !isRunning -> start()
                !animate && isRunning -> stop()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun setViewGone(view: View, gone: Boolean) {
        view.visibility = if (gone) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setViewVisible(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }

    @SuppressLint("SetJavaScriptEnabled")
    @JvmStatic
    @BindingAdapter("setWebViewBodyWithJavaScriptEnabled")
    fun setWebViewBody(webView: WebView, content: String?) {
        webView.settings.javaScriptEnabled = true
        webView.loadData(content, "text/html", "UTF-8")
    }

    @JvmStatic
    @BindingAdapter("invisible")
    fun setViewVisibilityWithInvisibility(view: View, invisible: Boolean) {
        view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("stringItems")
    fun setSpinnerStringItems(spinner: AppCompatSpinner, items: List<String>) {
        try {
            val adapter = spinner.adapter

            @Suppress("UNCHECKED_CAST")
            val arrayAdapter = adapter as ArrayAdapter<String>

            arrayAdapter.clear()
            arrayAdapter.addAll(items)
        } catch (e: ClassCastException) {
            throw UnsupportedOperationException("Adapter type is not supported. Please add it.")
        }
    }

    @JvmStatic
    @BindingAdapter("onItemSelected")
    fun setOnItemSelectedListener(spinner: AppCompatSpinner, itemSelectedListener: AdapterView.OnItemSelectedListener) {
        spinner.onItemSelectedListener = itemSelectedListener
    }

    @JvmStatic
    @BindingAdapter("backgroundLevel")
    fun setBackgroundLevel(view: View, level: Int) {
        view.background?.level = level
    }

    @JvmStatic
    @BindingAdapter("recyclerViewAdapter")
    fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        adapter?.let { recyclerView.adapter = it }
    }

    @BindingAdapter("attachItemTouchHelper")
    @JvmStatic
    fun setItemTouchHelper(recyclerView: RecyclerView, itemTouchHelper: ItemTouchHelper?) {
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    @JvmStatic
    @BindingAdapter("pagerAdapter")
    fun setPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter?) {
        adapter?.let { viewPager.adapter = it }
    }

    @JvmStatic
    @BindingAdapter("onTouchListener")
    fun setOnTouchListener(view: View, onTouchListener: View.OnTouchListener?) {
        onTouchListener?.run {
            view.setOnTouchListener(onTouchListener)
        }
    }

    @JvmStatic
    @BindingAdapter("bindingSrc")
    fun setImageBindingSource(imageView: ImageView, resId: Int?) {
        setImageViewResource(resId, imageView)
    }

    private fun setImageViewResource(resId: Int?, imageView: ImageView) {
        resId?.let { imageView.setImageDrawable(AppCompatResources.getDrawable(imageView.context, it)) }
    }

    @Deprecated("This should only be used if your imageview is in an included layout because databinding doesn't understand the app:bindingSrc attribute yet")
    @JvmStatic
    @BindingAdapter("android:src")
    fun setAndroidImageSource(imageView: ImageView, resId: Int?) {
        setImageViewResource(resId, imageView)
    }

    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun setImageFromUrl(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Picasso.get().load(url).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("snapHelper")
    fun setRecyclerViewSnapHelper(recyclerView: RecyclerView, snapHelper: SnapHelper?) {
        snapHelper?.apply { attachToRecyclerView(recyclerView) }
    }

    @JvmStatic
    @BindingAdapter("onScrollListeners")
    fun setRecyclerViewOnScrollListeners(
        recyclerView: RecyclerView,
        onScrollListeners: List<RecyclerView.OnScrollListener>?
    ) {
        onScrollListeners?.forEach { recyclerView.addOnScrollListener(it) }
    }

    @BindingAdapter("addTextChangeListener")
    @JvmStatic
    fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }

    /**
     * This BindingAdapter can be used in the following ways:
     * `app:drawableStart="@{model.resourceId}"`;
     * `app:drawableEnd="@{model.drawable}"`;
     * `app:drawableBottom="@{R.drawable.drawableId}"`, needs import of R in layout;
     * `app:drawableTop="@{@drawable/drawable_id}"`, this isn't supported as the framework implements
     * the Binding class using `View.getResources.getDrawable(...)` which breaks on 4.4
     * */
    @JvmStatic
    @BindingAdapter(
        "drawableStart",
        "drawableEnd",
        "drawableTop",
        "drawableBottom",
        requireAll = false
    )
    fun setSvgDrawableIntoTextView(
        textView: TextView,
        drawableStart: Any?,
        drawableEnd: Any?,
        drawableTop: Any?,
        drawableBottom: Any?
    ) {
        val context = textView.context
        val compoundDrawables = textView.compoundDrawables
        textView.setCompoundDrawablesWithIntrinsicBounds(
            getDrawableFrom(
                context,
                drawableStart,
                compoundDrawables[DRAWABLE_START_INDEX]
            ),
            getDrawableFrom(
                context,
                drawableTop,
                compoundDrawables[DRAWABLE_TOP_INDEX]
            ),
            getDrawableFrom(
                context,
                drawableEnd,
                compoundDrawables[DRAWABLE_END_INDEX]
            ),
            getDrawableFrom(
                context,
                drawableBottom,
                compoundDrawables[DRAWABLE_BOTTOM_INDEX]
            )
        )
    }

    private fun getDrawableFrom(
        context: Context,
        drawableUnit: Any?,
        defaultDrawable: Drawable?
    ): Drawable? {
        return when (drawableUnit) {
            is Drawable -> drawableUnit
            is Int -> {
                if (drawableUnit > 0) {
                    AppCompatResources.getDrawable(context, drawableUnit)
                } else {
                    defaultDrawable
                }
            }
            else -> defaultDrawable
        }
    }

    private const val DRAWABLE_START_INDEX = 0
    private const val DRAWABLE_TOP_INDEX = 1
    private const val DRAWABLE_END_INDEX = 2
    private const val DRAWABLE_BOTTOM_INDEX = 3
}