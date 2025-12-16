package com.example.myapplication.data.repositories

import com.example.myapplication.data.models.UserProfile

interface UserRepository {
    suspend fun getUserProfile(): UserProfile
    suspend fun updateUserProfile(profile: UserProfile)
}