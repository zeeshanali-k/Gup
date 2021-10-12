package com.techsensei.gupp.main.chat.chat

import androidx.compose.runtime.MutableState
import com.techsensei.domain.model.Chat

data class ChatMessagesState(
    val messages: MutableState<List<Chat>?>? = null,
    val isLoading: Boolean = false,
    val error:String? = null
)