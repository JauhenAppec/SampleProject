package com.example.sampleapplication.utils.mapper

import android.content.Context
import com.example.sampleapplication.R
import com.example.sampleapplication.model.db.SampleDbEntity
import com.example.sampleapplication.model.domain.SampleDomainEntity
import com.example.sampleapplication.model.ui.SampleUiEntity
import com.example.sampleapplication.model.ui.SampleUiListEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject

class SampleEntityDbDomainMapper @Inject constructor(@ApplicationContext val context: Context) : Mapper<SampleDbEntity, SampleDomainEntity> {
    override fun mapFromLowerToHigher(from: SampleDbEntity): SampleDomainEntity {
        return SampleDomainEntity(
            sampleEntityId = from.sampleEntityId,
            timeAdded = from.timeAdded,
        )
    }

    override fun mapFromHigherToLower(from: SampleDomainEntity): SampleDbEntity {
        return SampleDbEntity(
            sampleEntityId = from.sampleEntityId,
            timeAdded = from.timeAdded
        )
    }
}

class SampleEntityListUiDomainMapper @Inject constructor(@ApplicationContext val context: Context) : Mapper<SampleDomainEntity, SampleUiListEntity> {
    override fun mapFromLowerToHigher(from: SampleDomainEntity): SampleUiListEntity {
        return SampleUiListEntity(
            sampleEntityId = from.sampleEntityId,
            timeAdded = from.timeAdded,
            timeAddedString = Date(from.timeAdded).toString(),
            entityTextColorResId = R.color.white,
            defaultBackgroundColor = R.color.colorPrimary
        )
    }

    override fun mapFromHigherToLower(from: SampleUiListEntity): SampleDomainEntity {
        return SampleDomainEntity(
            sampleEntityId = from.sampleEntityId,
            timeAdded = from.timeAdded
        )
    }
}

class SampleEntityUiDomainMapper @Inject constructor(@ApplicationContext val context: Context) : Mapper<SampleDomainEntity, SampleUiEntity> {
    override fun mapFromLowerToHigher(from: SampleDomainEntity): SampleUiEntity {
        return SampleUiEntity(
            sampleEntityId = from.sampleEntityId,
            timeAdded = from.timeAdded,
            timeAddedString = Date(from.timeAdded).toString(),
            entityTextColorResId = R.color.colorAccent
        )
    }
    override fun mapFromHigherToLower(from: SampleUiEntity): SampleDomainEntity {
        return SampleDomainEntity(
            sampleEntityId = from.sampleEntityId,
            timeAdded = from.timeAdded
        )
    }
}
