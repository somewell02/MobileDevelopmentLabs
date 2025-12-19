package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.Message
import com.example.myapplication.data.repositories.MessageRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val messageRepository: MessageRepository) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val messageList = messageRepository.getMessages()
                _messages.value = messageList
                
            } catch (e: Exception) {
                _error.value = "Failed to load messages: ${e.message}"
                _messages.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val messageList = messageRepository.refreshMessages()
                _messages.value = messageList
                
            } catch (e: Exception) {
                _error.value = "Failed to refresh messages: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}