package com.agoines.goods.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val USER_URL = stringPreferencesKey("user_url")
fun DataStore<Preferences>.getUrl(): Flow<String> {
    return this@getUrl.data.map { preferences ->
        preferences[USER_URL]!!
    }
}

suspend fun DataStore<Preferences>.setUrl(url: String) {
    this@setUrl.edit { setting ->
        setting[USER_URL] = url
    }
}

val TOKEN = stringPreferencesKey("token")

fun DataStore<Preferences>.getToken(): Flow<String> {
    return this@getToken.data.map { preferences ->
        preferences[TOKEN]!!
    }
}

suspend fun DataStore<Preferences>.setToken(token: String) {
    this@setToken.edit { setting ->
        setting[TOKEN] = token
    }
}

fun DataStore<Preferences>.getUrlAndToken(): Flow<Array<String>> {

    return this@getUrlAndToken.data.map { preferences ->
        arrayOf(preferences[USER_URL]!!, preferences[TOKEN]!!)
    }
}