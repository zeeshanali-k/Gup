package com.techsensei.data.repository

import android.util.Log
import com.google.gson.Gson
import com.techsensei.data.network.ChatClient
import com.techsensei.data.network.dto.toChat
import com.techsensei.data.network.dto.toRoom
import com.techsensei.data.network.dto.toUser
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.ChatResponse
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.Room
import com.techsensei.domain.repository.ChatRepository
import java.lang.Exception

class ChatRepositoryImpl(private val chatClient: ChatClient) : ChatRepository {

    private val TAG = "ChatRepositoryImpl"

    override suspend fun getUserChats(userId: Int): Resource<List<Room>> {
        return try {
            val apiResponse = chatClient.getUserChats(userId)
            apiResponse.rooms?.let { roomDtos ->
                Resource.Success(roomDtos.map { roomDto -> roomDto.toRoom() })
            } ?: Resource.Error("Failed to get data")

        } catch (e: Exception) {
            Log.d(TAG, "getUserChats: " + e.localizedMessage)
            Log.d(TAG, "getUserChats: " + e.cause)
            Resource.Error("Failed to get data")
        }
    }

    override suspend fun getChatMessages(roomId: Int): Resource<List<Chat>> {
        return try {
            val apiResponse = chatClient.getChatMessages(roomId)
            apiResponse.chats?.let { chatDtos ->
                Resource.Success(chatDtos.map { chatDto -> chatDto.toChat() })
            } ?: Resource.Error("Failed to get data")
//                ?: Resource.Error("Some Error Occurred. Try again!") // User does not exist
        } catch (e: Exception) {
            Log.d(TAG, "getChatMessages: " + e.localizedMessage)
            Log.d(TAG, "getChatMessages: " + e.cause)
            Resource.Error("Failed to get data")
        }
    }

    override suspend fun sendMessage(chat: Chat): Resource<ChatResponse> {
        return try {
            val chatResponse = chatClient.sendMessage(chat)
            Resource.Success(chatResponse)
        } catch (e: Exception) {
            Log.d(TAG, "getChatMessages: " + e.localizedMessage)
            Log.d(TAG, "getChatMessages: " + e.cause)
            Resource.Error("Failed to get data")
        }
    }

//    override suspend fun registerForChatEvent(channelPostFix : String): Flow<Resource<Chat>> {
//
//    }

}