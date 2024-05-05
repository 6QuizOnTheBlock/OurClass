package com.sixkids.data.repository.user.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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

    override suspend fun getUserId(): Int {
        return dataStore.data.map { preferences ->
            preferences[ID_KEY] ?: 0
        }.first()
    }

    override suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = userId
        }
    }

    override suspend fun getUserName(): String {
        return dataStore.data.map { preferences ->
            preferences[NAME_KEY] ?: ""
        }.first()
    }

    override suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = userName
        }
    }

    override suspend fun getUserProfileImage(): String {
        return dataStore.data.map { preferences ->
            preferences[IMAGE_KEY] ?: ""
        }.first()
    }

    override suspend fun saveUserProfileImage(image: String) {
        dataStore.edit { preferences ->
            preferences[IMAGE_KEY] = image
        }
    }

    companion object {
        val ROLE_KEY = stringPreferencesKey("role")
        val ID_KEY = intPreferencesKey("id")
        val NAME_KEY = stringPreferencesKey("name")
        val IMAGE_KEY = stringPreferencesKey("image")
    }
}