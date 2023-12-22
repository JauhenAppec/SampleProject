package com.example.sampleapplication.ui.exit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sampleapplication.R
import com.example.sampleapplication.core.extensions.observe
import com.example.sampleapplication.databinding.DialogExitBinding
import com.example.sampleapplication.ui.base.BaseDialogFragment

class ExitDialogFragment: BaseDialogFragment() {

    override val viewBinding by viewBinding(DialogExitBinding::bind)
    override val viewModel by viewModels<ExitDialogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_exit, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureObservers()
    }

    private fun configureObservers() {
        observe(viewModel.exitClickEvent) {
            requireActivity().finish()
        }
    }
}