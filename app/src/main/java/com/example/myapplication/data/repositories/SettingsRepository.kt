package com.example.myapplication.data.repositories

interface SettingsRepository {
    suspend fun getThemeState(): Boolean
    suspend fun saveThemeState(isDarkTheme: Boolean)
}