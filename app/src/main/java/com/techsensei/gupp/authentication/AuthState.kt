package com.techsensei.gupp.authentication

import com.techsensei.domain.model.User

data class AuthState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val user: User? = null,
    val isRegistered: Boolean = false
)
