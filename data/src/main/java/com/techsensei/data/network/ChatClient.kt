package com.techsensei.data.network

import com.techsensei.data.network.dto.ChatApiResponse
import com.techsensei.data.network.dto.RoomDto
import com.techsensei.data.utils.QueryConstants
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.ChatResponse
import com.techsensei.domain.model.Room
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatClient {

    @GET("get-user-chat-rooms")
    suspend fun getUserChats(@Query(QueryConstants.ID_QUERY_ARG) userId: Int): ChatApiResponse


    @GET("get-room-chats")
    suspend fun getChatMessages(@Query(QueryConstants.ID_QUERY_ARG) room_id: Int): ChatApiResponse

    @POST("send-message")
    suspend fun sendMessage(@Body chat: Chat): ChatResponse

    @POST("verify-chat-room")
    suspend fun verifyChat(
        @Query(QueryConstants.USER_ID_ARG) userId: Int,
        @Query(QueryConstants.CHAT_USER_ID_ARG) chatUserId: Int
    ): RoomDto

}