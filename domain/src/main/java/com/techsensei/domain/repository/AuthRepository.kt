package com.techsensei.domain.repository

import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User

interface AuthRepository {


    suspend fun verifyUser(user: User) : Resource<User>
    suspend fun registerUser(user: User) : Resource<User>

}