package com.example.sampleapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapplication.model.db.SampleDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sampleEntity: SampleDbEntity): Long

    @Query("SELECT * FROM sample_entity")
    fun getAllEntities(): Flow<List<SampleDbEntity>>

    @Query("SELECT * FROM sample_entity WHERE sample_entity_id = :entityId")
    fun getSampleEntityById(entityId: Long): Flow<SampleDbEntity?>
}