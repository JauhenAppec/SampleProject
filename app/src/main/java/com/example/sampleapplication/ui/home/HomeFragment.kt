package com.example.sampleapplication.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapplication.R
import com.example.sampleapplication.databinding.FragmentHomeBinding
import com.example.sampleapplication.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sampleapplication.core.adapter.SimpleBaseAdapter
import com.example.sampleapplication.model.ui.SampleUiListEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()

    private val entitiesAdapter by lazy {
        SimpleBaseAdapter(
            R.layout.item_sample_entity,
            object : DiffUtil.ItemCallback<SampleUiListEntity>() {
                override fun areItemsTheSame(
                    oldItem: SampleUiListEntity,
                    newItem: SampleUiListEntity
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: SampleUiListEntity,
                    newItem: SampleUiListEntity
                ): Boolean {
                    return oldItem == newItem
                }
            },
            viewModel,
            dataBindingItemId = BR.item,
            dataBindingHandlerId = BR.handler
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.showExitDialog()
            }
        })
    }

    private fun configureViews() {
        viewBinding.itemsRecyclerView.apply {
            itemAnimator = null
            adapter = entitiesAdapter
        }
    }
}