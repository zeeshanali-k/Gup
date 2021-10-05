package com.techsensei.data.network

import com.techsensei.data.network.dto.UserDto
import com.techsensei.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiClient {

//    For checking if user already exist or is new user
    @POST("verify-user")
    suspend fun verifyUser(@Body user:User):UserDto

    @POST("register-user")
    suspend fun registerUser(@Body user:User):UserDto

}
