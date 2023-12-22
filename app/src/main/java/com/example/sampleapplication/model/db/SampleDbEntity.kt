package com.example.sampleapplication.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sample_entity")
class SampleDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sample_entity_id")
    val sampleEntityId: Long = 0,
    @ColumnInfo(name = "time_added")
    val timeAdded: Long
)