package com.techsensei.domain.repository

import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User

class AuthRepositoryFake : AuthRepository {

    override suspend fun verifyUser(user: User): Resource<User> {
        if (user.email.equals("abc") && user.password.equals("123456")){
            return Resource.Success(User("name",email = "abc","123456"))
        }else{
            return Resource.Error("User not found")
        }
    }

}