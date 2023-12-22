package com.example.sampleapplication.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.sampleapplication.ui.base.navigation.NavigationCommand
import com.example.sampleapplication.utils.SingleLiveEvent

abstract class BaseFragment : Fragment {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    abstract val viewBinding: ViewBinding
    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (viewBinding as? ViewDataBinding)?.run {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
        observeNavigation(viewModel.navigationCommands)
    }
}

fun Fragment.observeNavigation(navigationCommands: SingleLiveEvent<NavigationCommand>) {
    navigationCommands.observe(viewLifecycleOwner) {
        when (it) {
            is NavigationCommand.To -> findNavController().safeNavigate(it.directions)
            is NavigationCommand.Back -> {
                if (!findNavController().popBackStack()) {
                    requireActivity().finish()
                }
            }
        }
    }
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}