package com.example.sampleapplication.usecases

import com.example.sampleapplication.model.domain.SampleDomainEntity
import com.example.sampleapplication.repo.SampleEntityRepository
import com.example.sampleapplication.utils.mapper.SampleEntityDbDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleEntitiesUseCase @Inject constructor (
    private val sampleEntityRepository: SampleEntityRepository,
    private val sampleEntityDbDomainMapper: SampleEntityDbDomainMapper
) {

    fun getUserSampleEntityById(entityId: Long): Flow<SampleDomainEntity?> {
        return sampleEntityRepository.getEntityById(entityId).map { dbModel ->
            if (dbModel != null) sampleEntityDbDomainMapper.mapFromLowerToHigher(dbModel) else null
        }
    }

    fun getUserSampleEntities(): Flow<List<SampleDomainEntity>> {
        return sampleEntityRepository.getEntities().map { dbModels ->
            dbModels.map { dbModel ->
                sampleEntityDbDomainMapper.mapFromLowerToHigher(dbModel)
            }
        }
    }

    suspend fun addUserSampleEntities(timeAdded: Long): Boolean {
        return sampleEntityRepository.insertEntity(sampleEntityDbDomainMapper.mapFromHigherToLower(createNewUserSampleEntity(timeAdded)))
    }

    private fun createNewUserSampleEntity(timeAdded: Long): SampleDomainEntity {
        return SampleDomainEntity(timeAdded = timeAdded)
    }
}