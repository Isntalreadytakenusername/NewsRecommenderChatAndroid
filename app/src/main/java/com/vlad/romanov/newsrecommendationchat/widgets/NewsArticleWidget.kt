package com.vlad.romanov.newsrecommendationchat.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vlad.romanov.newsrecommendationchat.NewsArticle

@Composable
fun NewsArticleWidget(newsArticle: NewsArticle, modifier: Modifier = Modifier)
{
    Card (modifier = modifier.padding(8.dp)) {
        Column (modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = newsArticle.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = newsArticle.description, maxLines = 4, overflow = TextOverflow.Ellipsis)
            Text(text = newsArticle.url, color = Color.Blue, modifier = Modifier.clickable { /* Open URL in browser */ })
        }
    }
}