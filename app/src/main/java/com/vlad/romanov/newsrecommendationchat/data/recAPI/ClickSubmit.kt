package com.vlad.romanov.newsrecommendationchat.data.recAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class InteractionData(
    val user_id: String,
    val title: String,
    val date: String,
    val domain: String
)

data class ClickSubmitResponse(
    val status: String
)

interface ClickSubmit {
    @POST("/submit_user_click/") // Replace "your_endpoint" with the actual endpoint you're targeting.
    suspend fun submitInteractionData(@Body interactionData: InteractionData): ClickSubmitResponse
}

object ClickSubmitRetrofitClient {
    private const val BASE_URL = "http://51.20.85.27"

    val instance: ClickSubmit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClickSubmit::class.java)
    }
}

