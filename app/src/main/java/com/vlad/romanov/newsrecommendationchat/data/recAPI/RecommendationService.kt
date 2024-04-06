package com.vlad.romanov.newsrecommendationchat.data.recAPI

import retrofit2.Response
import retrofit2.http.GET

interface RecommendationService {
    @GET("/")
    suspend fun getRecommendation(): Response<Recommendation>
}