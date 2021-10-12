package com.techsensei.data.network.dto

data class ChatApiResponse(
    val user: UserDto?,
    val rooms : List<RoomDto>?,
    val chats : List<ChatDto>?,
    val success: Boolean
)
