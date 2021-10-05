package com.techsensei.data.repository

import android.util.Log
import com.techsensei.data.network.AuthApiClient
import com.techsensei.data.network.dto.toUser
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.repository.AuthRepository
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApiClient: AuthApiClient) : AuthRepository {

    private val TAG = "AuthRepository_Impl"
    override suspend fun verifyUser(user: User): Resource<User> {
        try {
            val userDto = authApiClient.verifyUser(user)
            return userDto.message?.let { Resource.Error(message = it) }
                ?:Resource.Success(userDto.toUser())
//                ?: Resource.Error("Some Error Occurred. Try again!") // User does not exist
        } catch (e: Exception) {
            Log.d(TAG, "verifyUser: " + e.localizedMessage)
            Log.d(TAG, "verifyUser: " + e.cause)
        }
        return Resource.Error("Network Error")
    }

    override suspend fun registerUser(user: User): Resource<User> {
        try {
            val userDto = authApiClient.registerUser(user)
            return userDto.message?.let { Resource.Error(message = it) }
                ?:Resource.Success(userDto.toUser())
//                ?: Resource.Error("Some Error Occurred. Try again!") // User does not exist
        } catch (e: Exception) {
            Log.d(TAG, "verifyUser: " + e.localizedMessage)
            Log.d(TAG, "verifyUser: " + e.cause)
        }
        return Resource.Error("Network Error")
    }

}