package com.vlad.romanov.newsrecommendationchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlad.romanov.newsrecommendationchat.ui.theme.NewsRecommendationChatTheme
import androidx.compose.foundation.lazy.items

// Data class to model each individual news article


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
    val state = viewModel.recommendation.observeAsState()

    when (val currentState = state.value) {
        is ArticleState.Loading -> {
            // Display a loading indicator
            CircularProgressIndicator()
        }
        is ArticleState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(currentState.articles) { article ->
                    NewsArticleWidget(newsArticle = article)
                }
            }
        }
        is ArticleState.Error -> {
            // Display an error message
            Text(text = currentState.message)
        }
        null -> {
            // Optional: Handle initial state or loading state
        }
    }
}

//@Composable
//fun RecommendationScreen(viewModel: RecommendationViewModel = viewModel()) {
//    // Assuming viewModel.recommendation is LiveData<List<NewsArticle>>
//    val articles = viewModel.recommendation.observeAsState(initial = listOf<NewsArticle>())
//
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(articles.value) { article ->
//            NewsArticleWidget(newsArticle = article)
//        }
//    }
//}

@Composable
fun NewsArticleWidget(newsArticle: NewsArticle, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = newsArticle.title, style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsArticle.summary, style = MaterialTheme.typography.bodyMedium, maxLines = 4, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Published: ${newsArticle.published}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            ClickableText(
                text = androidx.compose.ui.text.AnnotatedString("Read more at ${newsArticle.domain}"),
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
                onClick = { /* Implement action to open article link */ }
            )
        }
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


}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    NewsRecommendationChatTheme {
//        RecommendationScreen()
//    }
//}


*/








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