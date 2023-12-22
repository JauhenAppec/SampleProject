package com.example.sampleapplication.ui.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupOrientation()
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    protected open fun setupOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("${this.javaClass.canonicalName} onStart()")
    }

    override fun onStop() {
        Timber.tag(TAG).d("${this.javaClass.canonicalName} onStop()")
        super.onStop()
    }

    companion object {
        private const val TAG = "BaseActivity"
    }
}