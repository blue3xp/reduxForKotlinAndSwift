package com.example.reduxforandroid.redux.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class ReduxPersistUtil(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("reduxPersist", Context.MODE_PRIVATE)

    fun saveState(key: String, state: Any) {
        val json = Gson().toJson(state)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun <T> loadState(key: String, clazz: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            Gson().fromJson(json, clazz)
        } else {
            null
        }
    }
}