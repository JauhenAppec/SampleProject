@file:Suppress("unused")

package com.example.sampleapplication.core.bindings

import android.graphics.PorterDuff
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.databinding.BindingAdapter
import com.example.sampleapplication.core.extensions.getGradientRoundedTintDrawable
import com.example.sampleapplication.core.extensions.getRoundedTintDrawable


@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, @DrawableRes resource: Int) {
    if (resource != 0) imageView.setImageResource(resource)
}

@BindingAdapter("android:srcSafe")
fun setImageResource(imageView: ImageView, @DrawableRes resource: Int?) {
    imageView.isVisible = resource != null && resource != 0
    if (resource != null && resource != 0) {
        imageView.setImageResource(resource)
    }
}

@BindingAdapter("isVisible")
fun View.isVisibleView(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isInvisible")
fun View.isInvisibleView(isInvisible: Boolean) {
    this.isInvisible = isVisible
}

@BindingAdapter("android:background")
fun setImageResourceBackground(imageView: ImageView, @DrawableRes resource: Int) {
    imageView.setBackgroundResource(resource)
}

@BindingAdapter("backgroundColorRes")
fun setImageResourceColorBackground(imageView: ImageView, @ColorRes resource: Int) {
    if (resource != 0) {
        imageView.setBackgroundColor(ContextCompat.getColor(imageView.context, resource))
    }
}

@BindingAdapter("customTintColor")
fun setCustomTintColor(view: ImageView, @ColorRes resource: Int) {
    view.setColorFilter(ContextCompat.getColor(view.context, resource), PorterDuff.Mode.SRC_IN)
}

/**
 * We can setup any drawable with your custom color and your custom radius.
 */
@BindingAdapter(value = ["drawableRadius", "drawableTintColor"], requireAll = true)
fun View.setBackgroundRoundDrawable(
    @DimenRes drawableRadiusRes: Int,
    @ColorRes drawableTintColorRes: Int
) {
    val color = ContextCompat.getColor(context, drawableTintColorRes)
    val radius = resources.getDimensionPixelOffset(drawableRadiusRes)
    val drawable = context.getRoundedTintDrawable(radius.toFloat(), color)
    background = drawable
}

fun View.setColorBackgroundRoundDrawable(
    @DimenRes drawableRadiusRes: Int,
    @ColorInt drawableTintColor: Int
) {
    val radius = resources.getDimensionPixelOffset(drawableRadiusRes)
    val drawable = context.getRoundedTintDrawable(radius.toFloat(), drawableTintColor)
    background = drawable
}

/**
 * We can setup any drawable with your custom color and your custom radius and your custom stroke.
 */
@BindingAdapter(value = ["drawableRadius", "drawableTintColor", "drawableStrokeWidth", "drawableStrokeColor"], requireAll = true)
fun View.setBackgroundRoundDrawableWithStroke(
    @DimenRes drawableRadiusRes: Int,
    @ColorRes drawableTintColorRes: Int,
    @DimenRes strokeWithRes: Int,
    @ColorRes strokeColorRes: Int
) {
    val color = ContextCompat.getColor(context, drawableTintColorRes)
    val radius = resources.getDimensionPixelOffset(drawableRadiusRes)
    val strokeWidth = resources.getDimensionPixelOffset(strokeWithRes)
    val strokeColor = ContextCompat.getColor(context, strokeColorRes)
    val drawable = context.getRoundedTintDrawable(radius.toFloat(), color, strokeWidth, strokeColor)
    background = drawable
}

/**
 * We can setup any drawable with your custom color and your custom radius.
 */
@BindingAdapter(value = ["drawableRadius", "drawableStartColor", "drawableEndColor"], requireAll = true)
fun ImageView.setBackgroundGradientRoundDrawable(
    @DimenRes drawableRadiusRes: Int,
    @ColorRes drawableStartColorRes: Int,
    @ColorRes drawableEndColorRes: Int
) {
    val radius = resources.getDimensionPixelOffset(drawableRadiusRes)
    val drawable = context.getGradientRoundedTintDrawable(radius.toFloat(), ContextCompat.getColor(context, drawableStartColorRes), ContextCompat.getColor(context, drawableEndColorRes))
    setImageDrawable(drawable)
}

@BindingAdapter(value = ["safeClickListener", "interval"], requireAll = false)
fun View.setSafeOnClickListener(listener: View.OnClickListener, interval: Int) {
    val safeClickListener = SafeClickListener(defaultInterval = interval) {
        listener.onClick(this)
    }
    setOnClickListener(safeClickListener)
}

@BindingAdapter(value = ["drawableResStart", "drawableResStartTintColor"], requireAll = false)
fun TextView.setDrawableResStart(drawableResId: Int, tintColor: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableResId).apply {
        this?.apply {
            setBounds(0, 0, this.intrinsicWidth, this.intrinsicHeight)
            setTint(tintColor)
        }
    }
    setCompoundDrawables(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
}

class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

