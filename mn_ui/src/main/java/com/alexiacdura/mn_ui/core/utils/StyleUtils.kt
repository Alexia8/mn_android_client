package com.alexiacdura.mn_ui.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper

object StyleUtils {

    /**
     *  Create a new context based on the specified attribute id
     */
    @JvmStatic
    fun getContextThemeWrapper(context: Context?, @AttrRes attributeId: Int): ContextThemeWrapper {
        val typedValue = TypedValue()
        val found = context?.theme?.resolveAttribute(attributeId, typedValue, true) ?: false
        if (!found) {
            throw IllegalStateException("Could not find resource with id $attributeId")
        }

        return ContextThemeWrapper(context, typedValue.resourceId)
    }

    /**
     * Use this only when we need to fetch ONE int from index position in typed attributes array. Single use.
     */
    @SuppressLint("Recycle")
    @JvmStatic
    @JvmOverloads
    fun getIntFromAttribute(context: Context, attrs: IntArray, index: Int, defaultValue: Int = 0): Int {
        return context.theme?.obtainStyledAttributes(attrs)?.use { ta ->
            ta.getInt(index, defaultValue)
        } ?: defaultValue
    }

    /**
     * Use this only when we need to fetch one colour.
     */
    @SuppressLint("Recycle")
    @JvmStatic
    @JvmOverloads
    fun getColorFromAttribute(context: Context, attrs: IntArray, index: Int, defaultColor: Int = Color.BLACK): Int {
        return context.theme?.obtainStyledAttributes(attrs)?.use { ta ->
            ta.getColor(index, defaultColor)
        } ?: defaultColor
    }

    /**
     * Use this only when we need to fetch one dimension.
     */
    @SuppressLint("Recycle")
    @JvmStatic
    @JvmOverloads
    fun getDimensionFromAttribute(
        context: Context,
        attrs: IntArray,
        index: Int,
        set: AttributeSet? = null,
        defaultDimension: Int = 0
    ): Int {
        return context.obtainStyledAttributes(set, attrs)?.use { ta ->
            ta.getDimensionPixelSize(index, defaultDimension)
        } ?: defaultDimension
    }

    /**
     * Use this only when we need to fetch one fraction.
     */
    @SuppressLint("Recycle")
    @JvmStatic
    @JvmOverloads
    fun getFractionFromAttribute(
        context: Context,
        attrs: IntArray,
        index: Int,
        set: AttributeSet? = null,
        defaultFraction: Float = 0F
    ): Float {
        return context.obtainStyledAttributes(set, attrs)?.use { ta ->
            ta.getFraction(index, 1, 1, defaultFraction)
        } ?: defaultFraction
    }

    /**
     * Use this only when we need to fetch one drawable.
     */
    @SuppressLint("Recycle")
    @JvmStatic
    @JvmOverloads
    fun getDrawableFromAttribute(context: Context, attrs: IntArray, index: Int, set: AttributeSet? = null): Drawable? {
        return context.obtainStyledAttributes(set, attrs).use { ta ->
            getDrawableFromTypeArray(context, ta, index)
        }
    }

    @JvmStatic
    fun setDrawableColor(drawable: Drawable, @ColorInt color: Int) {
        when (drawable) {
            is ShapeDrawable -> drawable.paint.color = color
            is GradientDrawable -> drawable.setColor(color)
            is ColorDrawable -> drawable.color = color
        }
    }

    @JvmStatic
    fun setDrawableStroke(drawable: Drawable, width: Int, @ColorInt color: Int) {
        (drawable as? GradientDrawable)?.setStroke(width, color)
    }

    @JvmStatic
    fun getDrawableFromTypeArray(context: Context, ta: TypedArray, index: Int): Drawable? {
        val resourceId = ta.getResourceId(index, 0)

        return if (resourceId != 0) {
            AppCompatResources.getDrawable(context, resourceId)
        } else {
            null
        }
    }
}