package com.example.myapplication.data.repositories

import com.example.myapplication.data.models.Message

/**
 * Repository interface for managing message data
 * Provides unified access to messages from API and local database
 * Requirements: 3.2, 10.6
 */
interface MessageRepository {
    suspend fun getMessages(): List<Message>
    suspend fun refreshMessages(): List<Message>
    suspend fun isNetworkAvailable(): Boolean
}