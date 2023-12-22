package com.example.sampleapplication.core.extensions

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.FlowCollector
import java.io.IOException

suspend fun FlowCollector<Preferences>.handleError(exception: Throwable) {
    // dataStore.data throws an IOException when an error is encountered when reading data
    if (exception is IOException) {
        emit(emptyPreferences())
    } else {
        throw exception
    }
}