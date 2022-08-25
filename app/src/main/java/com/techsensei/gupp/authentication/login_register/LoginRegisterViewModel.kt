package com.techsensei.gupp.authentication.login_register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.data.utils.PrefsProvider
import com.techsensei.domain.model.Resource
import com.techsensei.domain.model.User
import com.techsensei.domain.use_case.auth.RegisterUserUseCase
import com.techsensei.domain.use_case.auth.VerifyUserUseCase
import com.techsensei.gupp.authentication.AuthState
import com.techsensei.gupp.utils.constants.PrefConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject
constructor(
    private val verifyUserUseCase: VerifyUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val prefsProvider: PrefsProvider
) : ViewModel() {

    private val _authState: MutableState<AuthState> = mutableStateOf(AuthState())
    val authState: State<AuthState> = _authState

    val isLoggedIn = prefsProvider.getBool(PrefConstants.IS_LOGGED_IN)

    fun verifyUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            verifyUserUseCase(user).collect {
                when (it) {
                    is Resource.Loading -> {
                        _authState.value = AuthState(isLoading = true)
                    }
                    is Resource.Success -> {
                        if(it.data!!.exists){
                            setUserDetails(it.data!!)
                        }
                        _authState.value = AuthState(user = it.data)
                    }
                    is Resource.Error -> {
                        _authState.value = AuthState(error = it.message)
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
                        _authState.value = AuthState(isLoading = true, user = User(exists = false))
                    }
                    is Resource.Success -> {
                        setUserDetails(it.data!!)
                        _authState.value = AuthState(user = it.data, isRegistered = true)
                    }
                    is Resource.Error -> {
                        _authState.value =
                            AuthState(error = it.message, user = User(exists = false))
                    }
                }
            }
        }
    }

    fun setUserDetails(it: User) {
        prefsProvider.setBool(PrefConstants.IS_LOGGED_IN, true)
        prefsProvider.setString(PrefConstants.USER_NAME, it.name!!)
        prefsProvider.setInt(PrefConstants.USER_ID, it.id!!)
        prefsProvider.setString(PrefConstants.USER_EMAIL, it.email!!)
        prefsProvider.setString(PrefConstants.USER_PROFILE_IMAGE, it.profileImage ?: "")
    }

}