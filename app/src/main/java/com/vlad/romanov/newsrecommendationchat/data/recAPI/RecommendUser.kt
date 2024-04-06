package com.vlad.romanov.newsrecommendationchat.data.recAPI

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


// Data class to model the articles' information.
data class ApiResponse(
    val distance: List<Double>,
    val domain: List<String>,
    val link: List<String>,
    val published: List<String>,
    val summary: List<String>,
    val title: List<String>,
    val explanations: List<String>
)

// Retrofit interface to fetch the data
// interface to explain retrofit how it will be called by us and what it should do
// which endpoint to call, what method to use, what parameters to pass, etc.
interface ApiService {
    @GET("get_recommendations/user_1")
    suspend fun getArticles(): Response<ApiResponse>
}

// Singleton object to hold the Retrofit instance which can be used to make the API calls.
// as was specified in the interface
class ArticlesRecommendationInstance {
    companion object {
        // custom OkHttpClient to set the timeouts
        private val okHttpClient = OkHttpClient.Builder()
            // Set the connect timeout (optional)
            .connectTimeout(30, TimeUnit.SECONDS) // For example, 30 seconds
            // Set the read timeout
            .readTimeout(100, TimeUnit.SECONDS) // For example, 60 seconds for APIs that take longer to respond
            // Set the write timeout (optional)
            .writeTimeout(30, TimeUnit.SECONDS) // For example, 30 seconds
            .build()

        val recommendationService = Retrofit.Builder()
            .baseUrl("http://51.20.85.27")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java) // here we specify the interface that Retrofit should use so it knows how it is called by us
    }
}