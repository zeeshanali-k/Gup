package com.techsensei.domain.use_case.chat

import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.Room
import com.techsensei.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VerifyChat (private val chatRepository: ChatRepository) {

    operator fun invoke(userId:Int,chatUserId:Int):Flow<Resource<Room>> = flow{
        emit(chatRepository.verifyChat(userId, chatUserId))
    }

}