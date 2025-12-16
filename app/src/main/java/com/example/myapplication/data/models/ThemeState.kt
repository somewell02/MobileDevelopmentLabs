package com.example.myapplication.data.models

data class ThemeState(
    val isDarkTheme: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun toggle(): ThemeState {
        return this.copy(
            isDarkTheme = !isDarkTheme,
            lastUpdated = System.currentTimeMillis()
        )
    }
}