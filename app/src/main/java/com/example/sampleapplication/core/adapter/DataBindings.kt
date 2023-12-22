package com.example.sampleapplication.core.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["adapterItems", "scrollToTop"], requireAll = false)
fun <T> RecyclerView.submitAdapterItems(list: List<T>?, scrollToTop: Boolean = false) {
    if (adapter is ListAdapter<*, *>) {
        (adapter as ListAdapter<T, RecyclerView.ViewHolder>).submitList(list) {
            if (scrollToTop) scrollToPosition(0)
        }
    } else {
        throw RuntimeException("you should init adapter as ListAdapter first")
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["adapterItems", "commitCallback"], requireAll = true)
fun <T> RecyclerView.submitAdapterItemsAndCallback(list: List<T>?, commitCallback: Runnable) {
    if (adapter is ListAdapter<*, *>) {
        if (list != null) {
            (adapter as ListAdapter<T, RecyclerView.ViewHolder>).submitList(list, commitCallback)
        }
    } else {
        throw RuntimeException("you should init adapter as ListAdapter first")
    }
}
