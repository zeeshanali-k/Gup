package com.techsensei.domain.use_case.auth

import android.util.Log
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VerifyUserUseCase constructor(private val authRepository: AuthRepository) {
    private val TAG = "VerifyUserUseCase"
    operator fun invoke(user: User):Flow<Resource<User>> = flow {
        emit(Resource.Loading())
//        kotlinx.coroutines.delay(1000)
        Log.d(TAG, "invoke: ")
        emit(authRepository.verifyUser(user))
//        emit(Resource.Error("hungry"))
    }

}