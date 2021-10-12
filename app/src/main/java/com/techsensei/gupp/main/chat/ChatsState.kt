package com.techsensei.gupp.main.chat

import com.techsensei.domain.model.Room

data class ChatsState(
    val isLoading: Boolean = false,
    val chats: List<Room>? = null,
    val message : String? = null
)
