package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.SettingsRepository
import com.example.myapplication.utils.LogUtils
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {
    
    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme
    
    init {
        LogUtils.logViewModelCreated("SettingsViewModel")
        loadThemeState()
    }
    
    private fun loadThemeState() {
        viewModelScope.launch {
            try {
                val savedTheme = settingsRepository.getThemeState()
                _isDarkTheme.value = savedTheme
                LogUtils.logDataChange("SettingsViewModel", "themeLoaded", null, if (savedTheme) "Dark" else "Light")
            } catch (e: Exception) {
                LogUtils.logDataChange("SettingsViewModel", "themeLoadError", null, e.message)
                _isDarkTheme.value = false
            }
        }
    }
    
    fun toggleTheme(isDark: Boolean) {
        if (_isDarkTheme.value != isDark) {
            LogUtils.logDataChange("SettingsViewModel", "isDarkTheme", _isDarkTheme.value, isDark)
            _isDarkTheme.value = isDark
            saveThemeState(isDark)
        }
    }
    
    private fun saveThemeState(isDark: Boolean) {
        viewModelScope.launch {
            try {
                settingsRepository.saveThemeState(isDark)
                LogUtils.logDataChange("SettingsViewModel", "themeSaved", null, if (isDark) "Dark" else "Light")
            } catch (e: Exception) {
                LogUtils.logDataChange("SettingsViewModel", "themeSaveError", null, e.message)
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        LogUtils.logViewModelDestroyed("SettingsViewModel")
    }
}