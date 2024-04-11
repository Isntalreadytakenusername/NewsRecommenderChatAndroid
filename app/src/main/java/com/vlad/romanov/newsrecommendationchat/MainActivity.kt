package com.vlad.romanov.newsrecommendationchat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.vlad.romanov.newsrecommendationchat.data.recAPI.InteractionData
import com.vlad.romanov.newsrecommendationchat.ui.theme.AppColorScheme
import com.vlad.romanov.newsrecommendationchat.widgets.MainScreen
import com.vlad.romanov.newsrecommendationchat.widgets.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// initialise a global color scheme
val myColorScheme = AppColorScheme.fromHex(
    background = "#0E1117",
    itemTextBox = "#262730",
    textNormal = "#818285",
    textHighlight = "#F8CBAD"
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsRecommendationChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = myColorScheme.background
                ) {
                    //RecommendationScreen()
                    MainScreen()
                }
            }
        }
    }
}



@Composable
fun RecommendationScreen(recommendationState: ArticleState) {
//    val state = viewModel.recommendation.observeAsState()

    when (recommendationState) {
        is ArticleState.Loading -> {
            // Display a loading indicator
            CircularProgressIndicator()
        }
        is ArticleState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items((recommendationState as ArticleState.Success).articles) { article ->
                    NewsArticleWidget(newsArticle = article)
                }
            }
        }
        is ArticleState.Error -> {
            // Display an error message
            Text(text = (recommendationState as ArticleState.Error).message)
        }
    }
}


@Composable
fun NewsArticleWidget(newsArticle: NewsArticle, modifier: Modifier = Modifier, viewModel: SingleViewModel = viewModel()) {
    val context = LocalContext.current

    Card(modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = myColorScheme.itemTextBox,
        )) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = newsArticle.title, style = MaterialTheme.typography.titleLarge.copy(color = myColorScheme.textHighlight),
                maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsArticle.summary, style = MaterialTheme.typography.bodyMedium.copy(color = myColorScheme.textNormal),
                maxLines = 4, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Published: ${newsArticle.published}", style = MaterialTheme.typography.bodySmall.copy(color = myColorScheme.textNormal))
            Spacer(modifier = Modifier.height(4.dp))
            ClickableText(
                text = androidx.compose.ui.text.AnnotatedString("Read more at ${newsArticle.link}"),
                style = MaterialTheme.typography.bodySmall.copy(color = myColorScheme.textHighlight),
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