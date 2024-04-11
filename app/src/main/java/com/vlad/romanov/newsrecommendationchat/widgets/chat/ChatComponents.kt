package com.vlad.romanov.newsrecommendationchat.widgets.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlad.romanov.newsrecommendationchat.ChatState
import com.vlad.romanov.newsrecommendationchat.SingleViewModel

@Composable
fun ChatScreen(viewModel: SingleViewModel) {
    val chatState by viewModel.state.collectAsState()

    Column {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chatState.messages) { message ->
                Text(text = message.content)
            }
        }
        MessageInput(onMessageSent = { viewModel.sendMessage(content = it, userId = "user_1") }) // for now, we are using a hardcoded userId
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
