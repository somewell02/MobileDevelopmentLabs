package com.example.myapplication.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.MessageDao
import com.example.myapplication.data.models.Message
import com.example.myapplication.data.models.toEntities
import com.example.myapplication.data.models.toMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(
    private val context: Context,
    private val apiService: ApiService = RetrofitClient.createApiService(),
    private val messageDao: MessageDao = AppDatabase.getInstance(context).messageDao()
) : MessageRepository {
    override suspend fun getMessages(): List<Message> = withContext(Dispatchers.IO) {
        try {
            if (isNetworkAvailable()) {
                val messagesFromApi = apiService.getComments()

                messageDao.deleteAllMessages()
                messageDao.insertMessages(messagesFromApi.toEntities())
                
                messagesFromApi
            } else {
                messageDao.getAllMessages().toMessages()
            }
        } catch (e: Exception) {
            try {
                messageDao.getAllMessages().toMessages()
            } catch (dbException: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun refreshMessages(): List<Message> = withContext(Dispatchers.IO) {
        try {
            val messagesFromApi = apiService.getComments()

            messageDao.deleteAllMessages()
            messageDao.insertMessages(messagesFromApi.toEntities())
            
            messagesFromApi
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun isNetworkAvailable(): Boolean = withContext(Dispatchers.IO) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return@withContext false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return@withContext false
            
            return@withContext networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            false
        }
    }
}