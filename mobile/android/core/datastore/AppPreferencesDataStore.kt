package com.trinetra.mobile.android.core.datastore

import android.content.Context
import androidx.datastore.core.Serialization
import androidx.datastore.preference.DataStore
import androidx.datastore.preference.GenericPreferenceSerializer
import androidx.datastore.preferencePreferenceManager
import com.trinetra.mobile.android.core.di.ApplicationProvider
import kotlinx.serialization.Serializable

// Preferences DataStore for persistent key-value storage
object AppPreferencesDataStore {

    // Serializer for Boolean values
    private val booleanSerializer: Serialization<Boolean> = GenericPreferenceSerializer(Boolean::class.java)

    // Serializer for String values
    private val stringSerializer: Serialization<String> = GenericPreferenceSerializer(String::class.java)

    // Authentication token storage
    const val KEY_AUTH_TOKEN = "auth_token"

    // Theme preference
    const val KEY_THEME = "theme"

    // Returns the DataStore instance
    fun createDataStore(context: Context): DataStore<Boolean> {
        return preferencesDataStore(
            name = "app_preferences",
            serializers = listOf(booleanSerializer)
        )
    }

    fun getAuthToken(context: Context): DataStore<String> {
        return preferencesDataStore(
            name = "app_preferences",
            serializers = listOf(stringSerializer)
        )
    }

    // Suspend function to read auth token
    suspend fun readAuthToken(context: Context): String? {
        val dataStore = preferencesDataStore(
            name = "app_preferences",
            serializers = listOf(stringSerializer)
        )
        return dataStore.data
            .map { it[KEY_AUTH_TOKEN] }
    }

    // Suspend function to write auth token
    suspend fun writeAuthToken(context: Context, token: String?) {
        val dataStore = preferencesDataStore(
            name = "app_preferences",
            serializers = listOf(stringSerializer)
        )
        dataStore.edit {
            it[KEY_AUTH_TOKEN] = token
        }
    }

    // Suspend function to read theme preference
    suspend fun readTheme(context: Context): String {
        val dataStore = preferencesDataStore(
            name = "app_preferences",
            serializers = listOf(booleanSerializer)
        )
        return dataStore.data
            .let { it[KEY_THEME] ?: "light" }
    }

    // Suspend function to write theme preference
    suspend fun writeTheme(context: Context, theme: String?) {
        val dataStore = preferencesDataStore(
            name = "app_preferences",
            serializers = listOf(booleanSerializer)
        )
        dataStore.edit {
            it[KEY_THEME] = theme
        }
    }
}