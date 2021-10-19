package com.techsensei.data.network.dto

import com.google.gson.annotations.SerializedName
import com.techsensei.domain.model.Room
import com.techsensei.domain.model.User

data class RoomDto(
    val id: Int,
    @SerializedName("chat_user") val user: UserDto?=null,
    val chats: List<ChatDto>?=null
)

fun RoomDto.toRoom() = Room(
    id,
    user?.toUser()?: User(),
    chats?.let {
        chats[0].toChat()
    }
)
