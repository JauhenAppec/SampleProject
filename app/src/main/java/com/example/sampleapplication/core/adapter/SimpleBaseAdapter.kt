package com.example.sampleapplication.core.adapter

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil

/**
 * SimpleBaseAdapter can help you with list with one item type. You can create the instance without
 * creating own adapter
 */
class SimpleBaseAdapter<T>(
    @LayoutRes val layoutId: Int,
    diffCallback: DiffUtil.ItemCallback<T>,
    val handler: Any? = null,
    dataBindingItemId: Int,
    dataBindingHandlerId: Int
) : BaseAdapter<T>(diffCallback, dataBindingItemId, dataBindingHandlerId) {
    override fun getLayout(viewType: Int): Int = layoutId
    override fun getHandler(position: Int) = handler
}
