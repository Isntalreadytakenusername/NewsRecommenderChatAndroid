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
import com.vlad.romanov.newsrecommendationchat.ui.theme.NewsRecommendationChatTheme
import com.vlad.romanov.newsrecommendationchat.widgets.NewsArticleWidget
import com.vlad.romanov.newsrecommendationchat.widgets.NewsFeed

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