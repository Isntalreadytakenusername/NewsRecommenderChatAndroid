package com.vlad.romanov.newsrecommendationchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlad.romanov.newsrecommendationchat.ui.theme.NewsRecommendationChatTheme
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsRecommendationChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecommendationScreen()
                }
            }
        }
    }
}

@Composable
fun RecommendationScreen(viewModel: RecommendationViewModel = viewModel()) {
    // subscribe to the changes in the view model
    val recommendation = viewModel.recommendation.observeAsState()

    // Check if the recommendation is not null and is successful
    if (recommendation.value?.isSuccessful == true) {
        // Assuming your Recommendation object can be displayed as a String
        // Adjust this to fit the structure of your Recommendation object
        Text(text = recommendation.value?.body()?.testingString ?: "No data")
    } else {
        Text(text = "Failed to fetch data")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsRecommendationChatTheme {
        RecommendationScreen()
    }
}











/*
package com.vlad.romanov.newsrecommendationchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.vlad.romanov.newsrecommendationchat.data.AlbumService
import com.vlad.romanov.newsrecommendationchat.data.Albums
import com.vlad.romanov.newsrecommendationchat.data.RetrofitInstance
import com.vlad.romanov.newsrecommendationchat.ui.theme.NewsRecommendationChatTheme
import com.vlad.romanov.newsrecommendationchat.widgets.NewsArticleWidget
import com.vlad.romanov.newsrecommendationchat.widgets.NewsFeed
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsRecommendationChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsFeedPreview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsFeedPreview() {
    val retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
    val responseLiveData: LiveData<Response<Albums>> =
        liveData {
            val response = retrofitService.getAlbums()
            emit(response)
        }

    NewsRecommendationChatTheme {
        val dummyArticles = listOf(
            NewsArticle("Title 1", "Description 1", "URL 1"),
            NewsArticle("Title 2", "Description 2", "URL 2"),
            NewsArticle("Title 3", "Description 3", "URL 3"),
            NewsArticle("Title 4", "Description 4", "URL 4")
        )
        NewsFeed(dummyArticles)
    }
}

 */