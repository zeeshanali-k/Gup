package com.techsensei.domain.use_case.chat

import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.ChatResponse
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.Room
import com.techsensei.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendMessage(private val chatRepository: ChatRepository) {
    private val TAG = "SendMessage"
    operator fun invoke(chat: Chat):Flow<Resource<ChatResponse>> = flow {
        emit(Resource.Loading())
        emit(chatRepository.sendMessage(chat))
    }

}
