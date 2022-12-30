package com.ferdyfermadi.storyapp.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ferdyfermadi.storyapp.data.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<LoginResult> =
        dataStore.data.map { preferences ->
            LoginResult(
                preferences[NAME_KEY] ?: "",
                preferences[USERID_KEY] ?: "",
                preferences[TOKEN_KEY] ?: ""
            )
        }

    suspend fun saveUser(name: String, userId: String, token: String) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[USERID_KEY] = userId
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val USERID_KEY = stringPreferencesKey("userid")
        private val TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference =
            INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
    }
}