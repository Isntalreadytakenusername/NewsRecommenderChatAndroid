package com.vlad.romanov.newsrecommendationchat.data.recAPI

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecommendationInstance {
    companion object {
        val recommendationService = Retrofit.Builder()
            .baseUrl("http://51.20.85.27")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecommendationService::class.java)
        }
}