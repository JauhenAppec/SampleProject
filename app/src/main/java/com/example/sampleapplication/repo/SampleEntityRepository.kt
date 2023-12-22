package com.example.sampleapplication.repo

import com.example.sampleapplication.model.db.SampleDbEntity
import kotlinx.coroutines.flow.Flow

interface SampleEntityRepository {
    suspend fun insertEntity(sampleDbEntity: SampleDbEntity): Boolean
    fun getEntities(): Flow<List<SampleDbEntity>>
    fun getEntityById(entityId: Long): Flow<SampleDbEntity?>
}