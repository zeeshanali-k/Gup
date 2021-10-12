package com.techsensei.data.network.dto

import com.google.gson.annotations.SerializedName
import com.techsensei.domain.model.Room

data class RoomDto(val id: Int, @SerializedName("chat_user") val user: UserDto, val chats: List<ChatDto>)

fun RoomDto.toRoom() = Room(
    id,
    user.toUser(),
    chats[0].toChat()
)
