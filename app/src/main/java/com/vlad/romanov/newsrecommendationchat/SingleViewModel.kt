package com.vlad.romanov.newsrecommendationchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ApiResponse
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ArticlesRecommendationInstance
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ChatRequest
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ChatRequestData
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ChatRequestRetrofitClient
import com.vlad.romanov.newsrecommendationchat.data.recAPI.ClickSubmitRetrofitClient
import com.vlad.romanov.newsrecommendationchat.data.recAPI.InteractionData
import com.vlad.romanov.newsrecommendationchat.data.recAPI.Recommendation
import com.vlad.romanov.newsrecommendationchat.data.recAPI.RecommendationInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
/*
LiveData is a part of the Android Jetpack's Architecture components.
It's an observable data holder class that is lifecycle-aware, meaning it respects the lifecycle of
other app components, such as activities, fragments, or services.

The liveData builder function is a convenient way to instantiate a LiveData object, particularly
when working with coroutines. It provides a simpler and more concise way to work with data that
needs to be fetched asynchronously and observed by UI components.
 */
class SingleViewModel : ViewModel() {

    private val _recommendation = MutableStateFlow<ArticleState>(ArticleState.Loading)
    val recommendation: StateFlow<ArticleState> = _recommendation.asStateFlow()
    fun sendInteractionData(interactionData: InteractionData) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ClickSubmitRetrofitClient.instance.submitInteractionData(interactionData)
                }
                // Check response status
                if (response.status == "success") {
                    println("Interaction data submitted successfully")
                } else {
                    println("Failed to submit interaction data")
                }
            } catch (e: Exception) {
                throw Exception("Error submitting interaction data: ${e.localizedMessage}")
            }
        }
    }

    private fun loadRecommendations() {
        viewModelScope.launch(Dispatchers.IO) {
            _recommendation.value = ArticleState.Loading
            try {
                val response = ArticlesRecommendationInstance.recommendationService.getArticles()
                if (response.isSuccessful && response.body() != null) {
                    _recommendation.value = ArticleState.Success(convertApiResponseToNewsArticles(response.body()!!))
                } else {
                    _recommendation.value = ArticleState.Error("Failed to fetch articles")
                }
            } catch (e: Exception) {
                _recommendation.value = ArticleState.Error("Error fetching articles: ${e.localizedMessage}")
            }
        }
    }

    init {
        loadRecommendations() // Load recommendations when ViewModel is created
    }


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
    // chat handling part
    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val chatApi: ChatRequest = ChatRequestRetrofitClient.instance

    fun sendMessage(content: String, userId: String) {
        val userMessage = Message(content = content, isSent = true, isUserMessage = true)
        _state.value = _state.value.copy(messages = _state.value.messages + userMessage)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = chatApi.submitChatRequest(ChatRequestData(userId, content))
                val serverMessage = Message(content = response.response, isSent = true, isUserMessage = false)
                _state.value = _state.value.copy(messages = _state.value.messages + serverMessage)

                // Trigger an update to recommendations after a successful message exchange
                loadRecommendations()
            } catch (e: Exception) {
                handleError(userMessage)
            }
        }
    }

    private fun handleError(message: Message) {
        // Update the message to reflect that it was not sent
        _state.value = _state.value.copy(
            messages = _state.value.messages.map {
                if (it == message) it.copy(isSent = false) else it
            }
        )
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
// Sealed class to represent the different states of the articles.
sealed class ArticleState {
    object Loading : ArticleState()
    data class Success(val articles: List<NewsArticle>) : ArticleState()
    data class Error(val message: String) : ArticleState()
}


data class ChatState(
    val messages: List<Message> = emptyList()
)

data class Message(
    val content: String,
    val isSent: Boolean,
    val isUserMessage: Boolean
)
