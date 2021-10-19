package com.techsensei.gupp.main.users

import com.techsensei.domain.model.User

data class UsersState(
    val isLoading: Boolean,
    val users: List<User>? = null,
    val error: String? = null
)