package com.example.sampleapplication.ui.exit

import android.app.Application
import com.example.sampleapplication.ui.base.BaseViewModel
import com.example.sampleapplication.utils.SingleLiveEvent

class ExitDialogViewModel(application: Application): BaseViewModel(application) {

    val exitClickEvent = SingleLiveEvent<Unit>()

    fun onExitClick() {
        exitClickEvent.call()
    }

    fun onCancelClick() {
        navigateBack()
    }
}