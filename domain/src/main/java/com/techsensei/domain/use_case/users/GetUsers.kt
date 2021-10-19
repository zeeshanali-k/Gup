package com.techsensei.domain.use_case.users

import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetUsers(private val usersRepository: UsersRepository) {

    operator fun invoke(userId:Int): Flow<Resource<List<User>>> = flow {
        emit(usersRepository.getUser(userId))
    }

}