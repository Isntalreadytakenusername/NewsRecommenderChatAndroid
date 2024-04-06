package com.vlad.romanov.newsrecommendationchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ArticlesRecommendationInstance
import com.vlad.romanov.newsrecommendationchat.data.recAPI.Recommendation
import com.vlad.romanov.newsrecommendationchat.data.recAPI.RecommendationInstance
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
/*
LiveData is a part of the Android Jetpack's Architecture components.
It's an observable data holder class that is lifecycle-aware, meaning it respects the lifecycle of
other app components, such as activities, fragments, or services.

The liveData builder function is a convenient way to instantiate a LiveData object, particularly
when working with coroutines. It provides a simpler and more concise way to work with data that
needs to be fetched asynchronously and observed by UI components.
 */
class RecommendationViewModel : ViewModel() {

    val recommendation: LiveData<List<NewsArticle>> = liveData(Dispatchers.IO) {
        try {
            val response = ArticlesRecommendationInstance.recommendationService.getArticles()
            if (response.isSuccessful) {
                // Directly emit the body if it's not null, or an empty list otherwise.
                emit(response.body() ?: listOf())
            } else {
                // Emit an empty list if the response is not successful.
                emit(listOf<NewsArticle>())
            }
        } catch (e: Exception) {
            // In case of exception, emit an empty list to avoid crashing the app.
            emit(listOf<NewsArticle>())
        }
    }

    // testing function
    suspend fun getRecommendation(): Response<Recommendation> {
        return RecommendationInstance.recommendationService.getRecommendation()
    }

    suspend fun getRecommendedArticles(): Response<List<NewsArticle>> {
        return ArticlesRecommendationInstance.recommendationService.getArticles()
    }
}
