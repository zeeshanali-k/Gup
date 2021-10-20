package com.techsensei.gupp.main.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val profileImageUrl: String? = null
)
