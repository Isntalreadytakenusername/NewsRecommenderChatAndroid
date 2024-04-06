package com.vlad.romanov.newsrecommendationchat.data.recAPI

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Recommendation(
    @SerializedName("Hello") val testingString: String
)

class RecommendationInstance {
    companion object {
        val recommendationService = Retrofit.Builder()
            .baseUrl("http://51.20.85.27")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecommendationService::class.java)
    }
}

interface RecommendationService {
    @GET("/")
    suspend fun getRecommendation(): Response<Recommendation>
}