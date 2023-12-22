package com.example.sampleapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampleapplication.db.dao.SampleEntityDao
import com.example.sampleapplication.model.db.SampleDbEntity

@Database(
    entities = [
        SampleDbEntity::class
    ],
    version = 1
)
abstract class SampleApplicationDatabase : RoomDatabase() {
    abstract fun sampleEntityDao(): SampleEntityDao
}