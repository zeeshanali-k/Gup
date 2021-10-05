package com.techsensei.domain.use_case.auth

import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.repository.AuthRepository
import com.techsensei.domain.repository.AuthRepositoryFake
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class VerifyUserUseCaseTest {

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authRepository = AuthRepositoryFake()
    }


    @Test
    fun `user does not exist`() {
        runBlocking {
            val userRes = authRepository.verifyUser(User("ab","1234"))
            assert(userRes is Resource.Error)
        }
    }

    @Test
    fun `user exists`() {
        runBlocking {
            val userRes = authRepository.verifyUser(User(password = "123456", email = "abc"))
            assert(userRes is Resource.Success)
        }
    }


    @Test
    fun `network error`() {
        runBlocking {
            val userRes = authRepository.verifyUser(User(password = "123456", email = "abc"))
            assert(userRes.message.equals("network"))
        }
    }

}