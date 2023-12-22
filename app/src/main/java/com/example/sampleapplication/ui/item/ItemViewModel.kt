package com.example.sampleapplication.ui.item

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.sampleapplication.model.ui.SampleUiEntity
import com.example.sampleapplication.ui.base.BaseViewModel
import com.example.sampleapplication.usecases.SampleEntitiesUseCase
import com.example.sampleapplication.utils.mapper.SampleEntityUiDomainMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map

class ItemViewModel @AssistedInject constructor(
    application: Application,
    sampleEntitiesUseCase: SampleEntitiesUseCase,
    sampleEntityUiDomainMapper: SampleEntityUiDomainMapper,
    @Assisted private val sampleEntityId: Long
): BaseViewModel(application) {

    val item: LiveData<SampleUiEntity?> = sampleEntitiesUseCase.getUserSampleEntityById(sampleEntityId).map { sampleEntityDomainModel ->
        if (sampleEntityDomainModel != null) sampleEntityUiDomainMapper.mapFromLowerToHigher(sampleEntityDomainModel) else null
    }.asLiveData()

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(sampleEntityId: Long): ItemViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            sampleEntityId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(sampleEntityId) as T
            }
        }
    }
}