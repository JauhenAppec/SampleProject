package com.example.sampleapplication.utils.mapper

interface Mapper<LOWER_MODEL, HIGHER_MODEL> {
    fun mapFromLowerToHigher(from:LOWER_MODEL): HIGHER_MODEL
    fun mapFromHigherToLower(from:HIGHER_MODEL): LOWER_MODEL
}


