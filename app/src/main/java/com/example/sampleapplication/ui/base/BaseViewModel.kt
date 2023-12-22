package com.example.sampleapplication.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import com.example.sampleapplication.ui.base.navigation.NavigationCommand
import com.example.sampleapplication.utils.SingleLiveEvent

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navigationCommands.value = NavigationCommand.To(directions)
    }

    fun navigateBack() {
        navigationCommands.value = NavigationCommand.Back
    }
}