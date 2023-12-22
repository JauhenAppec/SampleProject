package com.example.sampleapplication.core.extensions

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val DIRECTION_DOWN = 1
private const val DIRECTION_UP = -1

fun RecyclerView.canScrollToBottom(): Boolean {
    return canScrollVertically(DIRECTION_DOWN)
}

fun RecyclerView.canScrollToTop(): Boolean {
    return canScrollVertically(DIRECTION_UP)
}

fun View.isVisiblePercent(minPercentageViewed: Int): Boolean {
    val clipRect = Rect()
    if (!getGlobalVisibleRect(clipRect)) {
        // Not visible
        return false
    }
    // % visible check - the cast is to avoid int overflow for large views.
    val visibleViewArea: Long = clipRect.height().toLong() * clipRect.width()
    val totalViewArea: Long = height.toLong() * width
    if (totalViewArea <= 0) {
        return false
    }
    return 100 * visibleViewArea >= minPercentageViewed * totalViewArea
}

fun ViewGroup.getAllChildren(): List<View> {
    return (0 until childCount).mapNotNull { index ->
        getChildAt(index)
    }
}