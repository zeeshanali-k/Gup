package com.techsensei.gupp.authentication.login_register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.use_case.auth.RegisterUserUseCase
import com.techsensei.domain.use_case.auth.VerifyUserUseCase
import com.techsensei.gupp.authentication.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject
constructor(private val verifyUserUseCase: VerifyUserUseCase,
            private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {

    private val _resource: MutableState<AuthState> = mutableStateOf(AuthState())
    val resource: State<AuthState> = _resource

    fun verifyUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            verifyUserUseCase(user).collect {
                when (it) {
                    is Resource.Loading -> {
                        _resource.value = AuthState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _resource.value = AuthState(user = it.data)
                    }
                    is Resource.Error -> {
                        _resource.value = AuthState(error = it.message)
                    }
                }
            }
        }
    }

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUserUseCase(user).collect {
                when (it) {
                    is Resource.Loading -> {
                        _resource.value = AuthState(isLoading = true,user = User(exists = false))
                    }
                    is Resource.Success -> {
                        _resource.value = AuthState(user = it.data,isRegistered = true)
                    }
                    is Resource.Error -> {
                        _resource.value = AuthState(error = it.message,user = User(exists = false))
                    }
                }
            }
        }
    }

}