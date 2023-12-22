package com.example.sampleapplication.ui.home

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapplication.model.ui.SampleUiListEntity
import com.example.sampleapplication.ui.base.BaseViewModel
import com.example.sampleapplication.usecases.SampleEntitiesUseCase
import com.example.sampleapplication.utils.mapper.SampleEntityListUiDomainMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val sampleEntitiesUseCase: SampleEntitiesUseCase,
    private val sampleEntityListUiDomainMapper: SampleEntityListUiDomainMapper
): BaseViewModel(application), SampleEntityClickListener {

    val userSampleEntities = sampleEntitiesUseCase.getUserSampleEntities().map { domainItems ->
        domainItems.map { domainItem ->
            sampleEntityListUiDomainMapper.mapFromLowerToHigher(domainItem)
        }
    }.asLiveData()

    fun onAddEntityClick() {
        viewModelScope.launch(Dispatchers.IO) {
            sampleEntitiesUseCase.addUserSampleEntities(System.currentTimeMillis())
        }
    }

    fun showExitDialog() {
        navigate(HomeFragmentDirections.actionHomeFragmentToExitDialog())
    }

    override fun onSampleEntityClick(sampleUiEntity: SampleUiListEntity) {
        navigate(HomeFragmentDirections.actionHomeFragmentToItemFragment(sampleUiEntity.sampleEntityId))
    }
}