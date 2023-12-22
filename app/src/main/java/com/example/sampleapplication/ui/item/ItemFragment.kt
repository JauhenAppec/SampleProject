package com.example.sampleapplication.ui.item

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.sampleapplication.R
import com.example.sampleapplication.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sampleapplication.core.extensions.observe
import com.example.sampleapplication.databinding.FragmentItemBinding
import com.example.sampleapplication.model.ui.SampleUiEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemFragment : BaseFragment(R.layout.fragment_item) {

    private val args by navArgs<ItemFragmentArgs>()

    override val viewBinding by viewBinding(FragmentItemBinding::bind)

    @Inject
    lateinit var viewModelFactory: ItemViewModel.AssistedFactory
    override val viewModel: ItemViewModel  by viewModels {
        ItemViewModel.provideFactory(viewModelFactory, args.sampleEntityIndex)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureObservers()
    }

    private fun configureObservers() {
        observe(viewModel.item, ::fillData)
    }

    private fun fillData(sampleUiEntity: SampleUiEntity?) {
        sampleUiEntity?.let { uiModel ->
            viewBinding.itemText.apply {
                text = uiModel.timeAddedString
                setTextColor(ContextCompat.getColor(requireContext(), uiModel.entityTextColorResId))
            }
        } ?: Toast.makeText(requireContext(), R.string.no_entity_with_id_in_db, Toast.LENGTH_LONG).show()
    }
}