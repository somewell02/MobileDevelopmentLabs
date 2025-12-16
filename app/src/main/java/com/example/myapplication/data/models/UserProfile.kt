package com.example.myapplication.data.models

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val status: String = ""
)