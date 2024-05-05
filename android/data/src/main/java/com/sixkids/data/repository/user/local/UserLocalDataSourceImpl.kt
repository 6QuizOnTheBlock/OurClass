package com.sixkids.data.repository.user.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserLocalDataSource {
    override suspend fun getRole(): String {
        return dataStore.data.map { preferences ->
            preferences[ROLE_KEY] ?: ""
        }.first()
    }

    override suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }

    companion object {
        val ROLE_KEY = stringPreferencesKey("role")
    }
}