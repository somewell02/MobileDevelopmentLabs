package com.example.myapplication.data.models

data class UserProfile(
    val id: String,
    var name: String,
    val email: String,
    var status: String = ""
) {
    fun updateName(newName: String): UserProfile {
        return this.copy(name = newName)
    }
    
    fun updateStatus(newStatus: String): UserProfile {
        return this.copy(status = newStatus)
    }
}