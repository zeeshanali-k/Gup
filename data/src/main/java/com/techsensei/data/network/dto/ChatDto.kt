package com.techsensei.data.network.dto

import com.techsensei.domain.model.Chat

data class ChatDto(
    val user: UserDto,
    val message: String,
    val id: Int,
    val created_at: String,
    val room_id:Int
)

fun ChatDto.toChat() = Chat(user.toUser(),message,created_at,room_id,id)