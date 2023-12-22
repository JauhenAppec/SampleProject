package com.example.sampleapplication.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Context.getTintDrawable(
    @DrawableRes imageRes: Int,
    @ColorInt color: Int,
    boundsSize: Int = 0
): Drawable {
    val wrapDrawable = getWrapDrawable(this, imageRes)
    DrawableCompat.setTint(wrapDrawable.mutate(), color)
    if (boundsSize > 0) wrapDrawable.setBounds(0, 0, boundsSize, boundsSize)
    return wrapDrawable
}

fun Context.getRoundedTintDrawable(
    radius: Float,
    @ColorInt color: Int = 0,
    strokeWidth: Int = 0,
    @ColorInt strokeColor: Int = 0
): Drawable {
    return GradientDrawable().apply {
        cornerRadius = radius
        setColor(color)
        if (strokeWidth > 0) {
            setStroke(strokeWidth, strokeColor)
        }
    }
}

fun Context.getGradientRoundedTintDrawable(
    radius: Float,
    @ColorInt startColor: Int = 0,
    @ColorInt endColor: Int = 0,
    strokeWidth: Int = 0,
    @ColorInt strokeColor: Int = 0,
    gradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TR_BL
): Drawable {
    return GradientDrawable().apply {
        orientation = gradientOrientation
        cornerRadius = radius
        colors = intArrayOf(startColor, endColor)
        if (strokeWidth > 0) {
            setStroke(strokeWidth, strokeColor)
        }
    }
}

private fun getWrapDrawable(context: Context, @DrawableRes imageRes: Int): Drawable {
    return DrawableCompat.wrap(
        ContextCompat.getDrawable(context, imageRes) ?: context.resources.getDrawable(imageRes)
    )
}
