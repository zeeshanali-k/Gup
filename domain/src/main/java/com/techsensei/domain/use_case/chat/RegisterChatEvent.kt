package com.techsensei.domain.use_case.chat

import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.repository.ChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class RegisterChatEvent(private val chatRepository: ChatRepository) {

    @ExperimentalCoroutinesApi
    operator fun invoke(roomId: Int, userId: Int): Flow<Resource<Chat>> = callbackFlow {
        trySend(Resource.Loading())
        chatRepository.registerChatEvent(roomId).collectLatest {
            if (it is Resource.Success && it.data!!.user!!.id == userId) {
                return@collectLatest
            }
            trySend(it)
        }
    }

}
