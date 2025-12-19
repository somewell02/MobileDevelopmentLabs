package com.example.myapplication.data.api

import com.example.myapplication.data.models.Message
import retrofit2.http.GET

interface ApiService {
    @GET("comments")
    suspend fun getComments(): List<Message>
}