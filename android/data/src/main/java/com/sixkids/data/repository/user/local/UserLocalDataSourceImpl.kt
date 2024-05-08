package com.sixkids.data.repository.user.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sixkids.model.UserInfo
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

    override suspend fun getUserInfo(): UserInfo {
        return dataStore.data.map { preferences ->
            UserInfo(
                id = preferences[ID_KEY] ?: 0,
                name = preferences[NAME_KEY] ?: "",
                email = preferences[EMAIL_KEY] ?: "",
                photo = preferences[IMAGE_KEY] ?: "",
                role = preferences[ROLE_KEY] ?: ""
            )
        }.first()
    }

    override suspend fun saveUserInfo(
        id: Int,
        name: String,
        email: String,
        photo: String,
        role: String
    ) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = id
            preferences[NAME_KEY] = name
            preferences[EMAIL_KEY] = email
            preferences[IMAGE_KEY] = photo
            preferences[ROLE_KEY] = role
        }
    }

    override suspend fun signOut(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    companion object {
        val ROLE_KEY = stringPreferencesKey("role")
        val ID_KEY = intPreferencesKey("id")
        val NAME_KEY = stringPreferencesKey("name")
        val IMAGE_KEY = stringPreferencesKey("image")
        val EMAIL_KEY = stringPreferencesKey("email")
    }
}