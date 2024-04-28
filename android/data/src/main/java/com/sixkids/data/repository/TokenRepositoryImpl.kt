package com.sixkids.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sixkids.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenRepository {

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
    override suspend fun getAccessToken(): String =
        dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY] ?: ""
        }.first()


    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun getRefreshToken(): String =
        dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY] ?: ""
        }.first()

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

}