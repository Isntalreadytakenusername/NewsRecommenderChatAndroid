package com.vlad.romanov.newsrecommendationchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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

    val recommendation: LiveData<Response<Recommendation>> = liveData(Dispatchers.IO) {
        try {
            val response = RecommendationInstance.recommendationService.getRecommendation()
            emit(response)
        } catch (e: Exception) {
            throw e
        }
    }

    // testing function
    suspend fun getRecommendation(): Response<Recommendation> {
        return RecommendationInstance.recommendationService.getRecommendation()
    }
}
