//package com.vlad.romanov.newsrecommendationchat.widgets.chat
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.vlad.romanov.newsrecommendationchat.data.recAPI.ChatRequest
//import com.vlad.romanov.newsrecommendationchat.data.recAPI.ChatRequestData
//import com.vlad.romanov.newsrecommendationchat.data.recAPI.ChatRequestRetrofitClient
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class ChatViewModel : ViewModel() {
//    private val _state = MutableStateFlow(ChatState())
//    val state: StateFlow<ChatState> = _state.asStateFlow()
//
//    private val chatApi: ChatRequest = ChatRequestRetrofitClient.instance
//
//    fun sendMessage(content: String, userId: String) {
//        // Add user's message to the UI
//        val userMessage = Message(content = content, isSent = true, isUserMessage = true)
//        _state.value = _state.value.copy(messages = _state.value.messages + userMessage)
//
//        // Send the message to the API
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = chatApi.submitChatRequest(ChatRequestData(userId, content))
//                // Add server's response to the UI
//                val serverMessage = Message(content = response.response, isSent = true, isUserMessage = false)
//                _state.value = _state.value.copy(messages = _state.value.messages + serverMessage)
//            } catch (e: Exception) {
//                // Handle error or update message as not sent
//                handleError(userMessage)
//            }
//        }
//    }
//
//    private fun handleError(message: Message) {
//        // Update the message to reflect that it was not sent
//        _state.value = _state.value.copy(
//            messages = _state.value.messages.map {
//                if (it == message) it.copy(isSent = false) else it
//            }
//        )
//    }
//}
//
//data class ChatState(
//    val messages: List<Message> = emptyList()
//)
//
//data class Message(
//    val content: String,
//    val isSent: Boolean,
//    val isUserMessage: Boolean
//)
