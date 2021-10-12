package com.techsensei.domain.use_case.chat

import android.util.Log
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.Room
import com.techsensei.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllChats(private val chatRepository: ChatRepository) {
    private val TAG = "GetAllChats"
    operator fun invoke(userId: Int):Flow<Resource<List<Room>>> = flow {
        emit(Resource.Loading())
        emit(chatRepository.getUserChats(userId))
    }

}
