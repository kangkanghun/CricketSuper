package com.moingay.cricketsuper.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cricket_super")

class DataStoreManager(private val context: Context) {
    init {
        context
    }

    companion object {
        val IS_FIRST_TIME = booleanPreferencesKey("IS_FIRST_TIME")
    }

    suspend fun setIsFirstTime(isPlaying: Boolean) {
        context.dataStore.edit {
            it[IS_FIRST_TIME] = isPlaying
        }
    }

    val isFirstTime: Flow<Boolean> = context.dataStore.data.map {
        it[IS_FIRST_TIME] ?: true
    }
}