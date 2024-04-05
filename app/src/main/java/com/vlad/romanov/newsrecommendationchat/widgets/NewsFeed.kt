package com.vlad.romanov.newsrecommendationchat.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlad.romanov.newsrecommendationchat.NewsArticle

@Composable
fun NewsFeed(newsArticles: List<NewsArticle>, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        newsArticles.forEach { newsArticle ->
            NewsArticleWidget(newsArticle)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}