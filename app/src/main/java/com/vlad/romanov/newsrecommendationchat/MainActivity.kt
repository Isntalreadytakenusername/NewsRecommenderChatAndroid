package com.vlad.romanov.newsrecommendationchat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import com.vlad.romanov.newsrecommendationchat.data.recAPI.InteractionData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
            // Handle initial state or loading state
        }
    }
}


@Composable
fun NewsArticleWidget(newsArticle: NewsArticle, modifier: Modifier = Modifier, viewModel: RecommendationViewModel = viewModel()) {
    val context = LocalContext.current

    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = newsArticle.title, style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsArticle.summary, style = MaterialTheme.typography.bodyMedium, maxLines = 4, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Published: ${newsArticle.published}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            ClickableText(
                text = androidx.compose.ui.text.AnnotatedString("Read more at ${newsArticle.link}"),
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
                onClick = {
                    val interactionData = InteractionData(
                        user_id = "user_1", // later replace with actual user ID
                        title = newsArticle.title,
                        date = getCurrentDate(),// get actual current date
                        domain = newsArticle.domain
                    )
                    viewModel.sendInteractionData(interactionData)

                    // Implement action to open article link
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(newsArticle.link)
                    }
                    context.startActivity(intent)
                 }
            )
        }
    }
}




fun getCurrentDate(): String {
    val currentDate = Date()
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(currentDate)
}