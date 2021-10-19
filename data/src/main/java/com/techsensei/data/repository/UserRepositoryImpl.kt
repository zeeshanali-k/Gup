package com.techsensei.data.repository

import com.techsensei.data.network.UsersClient
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.repository.UsersRepository
import java.lang.Exception

class UserRepositoryImpl(private val usersClient: UsersClient):UsersRepository {

    override suspend fun getUser(userId: Int): Resource<List<User>> {
        return try {
            Resource.Success(usersClient.getUsers(userId))
        }catch (e:Exception){
            Resource.Error("Failed to get users")
        }
    }

}