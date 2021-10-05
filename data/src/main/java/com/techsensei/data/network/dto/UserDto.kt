package com.techsensei.data.network.dto

import com.techsensei.domain.model.User

data class UserDto(
    val name: String?,
    val email: String?,
    val password: String?,
    val profileImage: String?,
    val exists: Boolean = false,
    val id: Int? = null,
    val message:String? = null
)

fun UserDto.toUser() = User(name,email,password,id,exists = this.exists,profileImage = profileImage)
