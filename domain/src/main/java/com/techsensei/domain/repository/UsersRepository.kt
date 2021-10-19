package com.techsensei.domain.repository

import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User

interface UsersRepository {
    suspend fun getUser(userId:Int):Resource<List<User>>
}