package com.vlad.romanov.newsrecommendationchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ApiResponse
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

    val recommendation: LiveData<ArticleState> = liveData(Dispatchers.IO) {
        emit(ArticleState.Loading) // Emit loading state
        try {
            val response = ArticlesRecommendationInstance.recommendationService.getArticles()
            if (response.isSuccessful && response.body() != null) {
                emit(ArticleState.Success(convertApiResponseToNewsArticles(response.body()!!)))
            } else {
                emit(ArticleState.Error("Failed to fetch articles"))
            }
        } catch (e: Exception) {
            emit(ArticleState.Error("Error fetching articles: ${e.localizedMessage}"))
        }
    }
    // Example processing logic (to be adapted as needed)
    private fun convertApiResponseToNewsArticles(response: ApiResponse): List<NewsArticle> {
        val articles = mutableListOf<NewsArticle>()
        for (i in response.distance.indices) {
            articles.add(
                NewsArticle(
                    distance = response.distance[i],
                    domain = response.domain[i],
                    link = response.link[i],
                    published = response.published[i],
                    summary = response.summary[i],
                    title = response.title[i],
                    explanation = response.explanations[i]
                )
            )
        }
        return articles
    }


    // testing function
    suspend fun getRecommendation(): Response<Recommendation> {
        return RecommendationInstance.recommendationService.getRecommendation()
    }

    suspend fun getRecommendedArticles(): Response<ApiResponse> {
        return ArticlesRecommendationInstance.recommendationService.getArticles()
    }
}

// Sealed class to represent the different states of the articles.
sealed class ArticleState {
    object Loading : ArticleState()
    data class Success(val articles: List<NewsArticle>) : ArticleState()
    data class Error(val message: String) : ArticleState()
}

