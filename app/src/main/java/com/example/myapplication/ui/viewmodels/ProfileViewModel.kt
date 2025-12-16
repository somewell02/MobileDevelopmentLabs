package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.UserProfile
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.utils.LogUtils
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    
    private val _userStatus = MutableLiveData<String>()
    val userStatus: LiveData<String> = _userStatus
    
    private var currentUserProfile: UserProfile? = null
    
    init {
        LogUtils.logViewModelCreated("ProfileViewModel")
        loadUserProfile()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                currentUserProfile = userRepository.getUserProfile()
                currentUserProfile?.let { profile ->
                    _userName.value = profile.name
                    _userStatus.value = profile.status
                    LogUtils.logDataChange("ProfileViewModel", "profileLoaded", null, "name: ${profile.name}, status: ${profile.status}")
                }
            } catch (e: Exception) {
                LogUtils.logDataChange("ProfileViewModel", "profileLoadError", null, e.message)
                _userName.value = ""
                _userStatus.value = ""
            }
        }
    }
    
    fun updateUserName(name: String) {
        LogUtils.logDataChange("ProfileViewModel", "userName", _userName.value, name)
        _userName.value = name
        saveUserProfile()
    }
    
    fun updateUserStatus(status: String) {
        LogUtils.logDataChange("ProfileViewModel", "userStatus", _userStatus.value, status)
        _userStatus.value = status
        saveUserProfile()
    }
    
    private fun saveUserProfile() {
        viewModelScope.launch {
            try {
                val updatedProfile = currentUserProfile?.copy(
                    name = _userName.value ?: "",
                    status = _userStatus.value ?: ""
                ) ?: UserProfile(
                    id = "default_user",
                    name = _userName.value ?: "",
                    email = "user@example.com",
                    status = _userStatus.value ?: ""
                )
                
                userRepository.updateUserProfile(updatedProfile)
                currentUserProfile = updatedProfile
                LogUtils.logDataChange("ProfileViewModel", "profileSaved", null, "name: ${updatedProfile.name}, status: ${updatedProfile.status}")
            } catch (e: Exception) {
                LogUtils.logDataChange("ProfileViewModel", "profileSaveError", null, e.message)
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        LogUtils.logViewModelDestroyed("ProfileViewModel")
    }
}