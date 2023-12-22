package com.example.sampleapplication.ui.base

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.viewbinding.ViewBinding
import com.example.sampleapplication.R

abstract class BaseDialogFragment : AppCompatDialogFragment() {
    abstract val viewBinding: ViewBinding
    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.apply {
            setCanceledOnTouchOutside(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (viewBinding as? ViewDataBinding)?.run {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
        observeNavigation(viewModel.navigationCommands)
        dialog?.apply {
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        }
    }
}