package com.example.myapplication.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.models.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(context: Context) : UserRepository {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("user_profile_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_STATUS = "user_status"

        private const val DEFAULT_USER_ID = "default_user"
        private const val DEFAULT_USER_EMAIL = "user@example.com"
    }
    
    override suspend fun getUserProfile(): UserProfile = withContext(Dispatchers.IO) {
        UserProfile(
            id = sharedPreferences.getString(KEY_USER_ID, DEFAULT_USER_ID) ?: DEFAULT_USER_ID,
            name = sharedPreferences.getString(KEY_USER_NAME, "") ?: "",
            email = sharedPreferences.getString(KEY_USER_EMAIL, DEFAULT_USER_EMAIL) ?: DEFAULT_USER_EMAIL,
            status = sharedPreferences.getString(KEY_USER_STATUS, "") ?: ""
        )
    }
    
    override suspend fun updateUserProfile(profile: UserProfile): Unit = withContext(Dispatchers.IO) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_ID, profile.id)
            putString(KEY_USER_NAME, profile.name)
            putString(KEY_USER_EMAIL, profile.email)
            putString(KEY_USER_STATUS, profile.status)
            apply()
        }
    }
}