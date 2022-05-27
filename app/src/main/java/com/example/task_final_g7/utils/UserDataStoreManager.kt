package com.example.task_final_g7.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val context: Context) {

    companion object {
        private const val DATASTORE_NAME = "user_preferences"
        private val ID_KEY = intPreferencesKey("username_key")
        private val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status_key")
        private val Context.userDataStore by preferencesDataStore(DATASTORE_NAME)
    }

    suspend fun saveUser(id: Int, status: Boolean) {
        context.userDataStore.edit { pref ->
            pref[ID_KEY] = id
            pref[LOGIN_STATUS_KEY] = status
        }
    }

    fun getId(): Flow<Int> {
        return context.userDataStore.data.map { pref ->
            pref[ID_KEY] ?: 0
        }
    }

    fun getLoginStatus(): Flow<Boolean> {
        return context.userDataStore.data.map { pref ->
            pref[LOGIN_STATUS_KEY] ?: false
        }
    }

    suspend fun clearLoginStatus() {
        context.userDataStore.edit { pref ->
            pref.clear()
        }
    }
}