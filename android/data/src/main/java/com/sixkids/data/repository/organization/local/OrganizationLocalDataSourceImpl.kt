package com.sixkids.data.repository.organization.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrganizationLocalDataSourceImpl  @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : OrganizationLocalDataSource {
    override suspend fun getSelectedOrganizationId(): Int {
        return dataStore.data.map { preference ->
            preference[SELECTED_ORGANIZATION_ID_KEY] ?: 0
        }.first()
    }

    override suspend fun saveSelectedOrganizationId(organizationId: Int) {
        dataStore.edit { preference ->
            preference[SELECTED_ORGANIZATION_ID_KEY] = organizationId
        }
    }

    override suspend fun getSelectedOrganizationName(): String {
        return dataStore.data.map { preference ->
            preference[SELECTED_ORGANIZATION_NAME_KEY] ?: ""
        }.first()
    }

    override suspend fun saveSelectedOrganizationName(organizationName: String) {
        dataStore.edit { preference ->
            preference[SELECTED_ORGANIZATION_NAME_KEY] = organizationName
        }
    }

    companion object{
        private val SELECTED_ORGANIZATION_ID_KEY = intPreferencesKey("selected_organization_id")
        private val SELECTED_ORGANIZATION_NAME_KEY = stringPreferencesKey("selected_organization_name")
    }

}