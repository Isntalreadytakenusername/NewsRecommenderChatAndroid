package com.vlad.romanov.newsrecommendationchat.data.recAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class ChatRequestData(
    val user_id: String,
    val request: String
)

data class ChatRequestResponse(
    val response: String
)

interface ChatRequest {
    @POST("/adjust_recommendations/")
    suspend fun submitChatRequest(@Body chatRequestData: ChatRequestData): ChatRequestResponse
}

object ChatRequestRetrofitClient {
    private const val BASE_URL = "http://51.20.85.27"

    val instance: ChatRequest by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatRequest::class.java)
    }
}


