package com.techsensei.data.repository

import android.util.Log
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.techsensei.data.network.ChatClient
import com.techsensei.data.network.dto.ChatEvent
import com.techsensei.data.network.dto.toChat
import com.techsensei.data.network.dto.toRoom
import com.techsensei.data.network.dto.toUser
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.ChatResponse
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.Room
import com.techsensei.domain.repository.ChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
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

    @ExperimentalCoroutinesApi
    override fun registerChatEvent(roomId: Int): Flow<Resource<Chat>> = callbackFlow {
        val options = PusherOptions()
        options.setCluster("ap2")

        val pusher = Pusher("81067ddc08c9872c59aa", options)
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.d(TAG, "State changed from ${change.previousState} to ${change.currentState}")
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.d(
                    TAG,
                    "There was a problem connecting! code ($code), message ($message), exception($e)"
                )
                trySend(Resource.Error("Failed to Connect: $message"))
            }
        }, ConnectionState.ALL)
        val channel = pusher.subscribe("chat_${roomId}")
        channel.bind("App\\Events\\ChatEvent") { event ->
            Log.d(TAG, "Received Data: " + event.data)
            Log.d(
                TAG, "Received Data: "
                        + Gson().fromJson(event.data, ChatEvent::class.java).toString()
            )
            val isSuccess = trySend(
                Resource.Success(
                    Gson().fromJson(
                        event.data,
                        ChatEvent::class.java
                    ).chat.toChat()
                )
            )
                .isSuccess
            Log.d(TAG, "registerChatEvent: $isSuccess")
        }
        awaitClose { pusher.disconnect() }
    }

    override suspend fun verifyChat(userId: Int, chatUserId: Int): Resource<Room> {
        return try {
            Resource.Success(chatClient.verifyChat(userId, chatUserId).toRoom())
        } catch (e: Exception) {
            Resource.Error("Failed to connect.")
        }
    }

//    override suspend fun registerForChatEvent(channelPostFix : String): Flow<Resource<Chat>> {
//
//    }

}