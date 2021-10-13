package com.techsensei.domain.repository

import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.ChatResponse
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.Room
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getUserChats(userId:Int):Resource<List<Room>>

    suspend fun getChatMessages(roomId:Int):Resource<List<Chat>>
    suspend fun sendMessage(chat: Chat): Resource<ChatResponse>
//    suspend fun registerForChatEvent(channelPostFix:String): Resource<Chat>
    @ExperimentalCoroutinesApi
    fun registerChatEvent(roomId: Int):Flow<Resource<Chat>>
}