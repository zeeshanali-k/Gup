package com.techsensei.domain.model

data class User(
    var name: String? = "",
    var email: String? = "",
    var password: String? = "",
    var id: Int? = null,
    var profileImage: String? = "",
    var profileImageTest: Int = -1,
    val exists: Boolean = false
)
