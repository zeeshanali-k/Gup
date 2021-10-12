package com.techsensei.domain.use_case.chat

import android.util.Log
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.Resource
import com.techsensei.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChatMessages(private val chatRepository: ChatRepository) {
    private val TAG = "GetAllChats"
    operator fun invoke(roomId: Int): Flow<Resource<List<Chat>>> = flow {
        emit(Resource.Loading())
        emit(chatRepository.getChatMessages(roomId))
    }

}
