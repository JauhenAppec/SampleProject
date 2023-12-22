package com.example.sampleapplication.model.ui

import androidx.annotation.ColorRes

data class SampleUiEntity(
    val sampleEntityId: Long,
    val timeAdded: Long,
    val timeAddedString: String,
    @ColorRes val entityTextColorResId: Int
)