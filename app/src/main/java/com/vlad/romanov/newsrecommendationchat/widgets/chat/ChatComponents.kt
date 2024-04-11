package com.vlad.romanov.newsrecommendationchat.widgets.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlad.romanov.newsrecommendationchat.ChatState
import com.vlad.romanov.newsrecommendationchat.SingleViewModel
import com.vlad.romanov.newsrecommendationchat.ui.theme.AppColorScheme

val myColorScheme = AppColorScheme.fromHex(
    background = "#0E1117",
    itemTextBox = "#262730",
    textNormal = "#818285",
    textHighlight = "#F8CBAD"
)

@Composable
fun ChatScreen(viewModel: SingleViewModel) {
    val chatState by viewModel.state.collectAsState()

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(all = 8.dp) // Add padding around the list
        ) {
            items(chatState.messages) { message ->
                // MessageCard wraps Text composable for styling
                MessageCard(message.content)
            }
        }
        MessageInput(onMessageSent = { viewModel.sendMessage(content = it, userId = "user_1") })
    }
}

@Composable
fun MessageCard(message: String) {
    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp), // Space between messages
        color = MaterialTheme.colorScheme.surfaceVariant, // Background color of the message
        shadowElevation = 4.dp // Elevation for a subtle shadow
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(all = 8.dp), // Padding inside the message box
            style = MaterialTheme.typography.bodyMedium // Text style
        )
    }
}


@Composable
fun MessageInput(onMessageSent: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f)
        )
        Button(onClick = {
            if (text.isNotBlank()) {
                onMessageSent(text)
                text = ""
            }
        }) {
            Text("Send")
        }
    }
}
