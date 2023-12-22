package com.example.sampleapplication.repo

import com.example.sampleapplication.db.dao.SampleEntityDao
import com.example.sampleapplication.model.db.SampleDbEntity
import com.example.sampleapplication.model.domain.SampleDomainEntity
import com.example.sampleapplication.utils.mapper.SampleEntityDbDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SampleEntityRepositoryImpl @Inject constructor(
    private val sampleEntityDao: SampleEntityDao
): SampleEntityRepository {

    override suspend fun insertEntity(sampleDbEntity: SampleDbEntity): Boolean {
       return sampleEntityDao.insert(sampleDbEntity) > 0
    }

    override fun getEntities(): Flow<List<SampleDbEntity>> {
        return sampleEntityDao.getAllEntities()
    }

    override fun getEntityById(entityId: Long): Flow<SampleDbEntity?> {
        return sampleEntityDao.getSampleEntityById(entityId)
    }
}