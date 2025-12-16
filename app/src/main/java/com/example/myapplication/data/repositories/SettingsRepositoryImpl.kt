package com.example.myapplication.data.repositories

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(context: Context) : SettingsRepository {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_IS_DARK_THEME = "is_dark_theme"
        private const val DEFAULT_THEME = false // Light theme as default
    }
    
    override suspend fun getThemeState(): Boolean = withContext(Dispatchers.IO) {
        sharedPreferences.getBoolean(KEY_IS_DARK_THEME, DEFAULT_THEME)
    }
    
    override suspend fun saveThemeState(isDarkTheme: Boolean): Unit = withContext(Dispatchers.IO) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_IS_DARK_THEME, isDarkTheme)
            apply()
        }
    }
}