package com.example.sampleapplication.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val dataBindingItemId: Int,
    private val dataBindingHandlerId: Int
) :
    ListAdapter<T, BaseAdapter.BaseViewHolder<T>>(diffCallback) {

    /**
     * layout ids for items
     */
    @LayoutRes
    protected abstract fun getLayout(viewType: Int): Int

    /**
     * Handler for click listeners
     */
    open fun getHandler(position: Int): Any? = null

    /**
     * By default we use BaseViewHolder, that can cover most places. Override if you need custom VH
     */
    open fun getViewHolder(binding: ViewDataBinding): BaseViewHolder<T> = BaseViewHolder(binding, dataBindingItemId, dataBindingHandlerId)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        return getViewHolder(
            DataBindingUtil.inflate(inflater, getLayout(viewType), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position), getHandler(position))
    }

    open class BaseViewHolder<T>(
        private val binding: ViewDataBinding,
        private val dataBindingItemId: Int,
        private val dataBindingHandlerId: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            onCreateViewHolder()
        }

        protected fun onCreateViewHolder() {
        }

        open fun bind(item: T, handler: Any?) {
            binding.setVariable(dataBindingItemId, item)
            if (handler != null) binding.setVariable(dataBindingHandlerId, handler)
            binding.executePendingBindings()
        }
    }
}
