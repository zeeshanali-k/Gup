package com.techsensei.gupp.main.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.domain.model.Resource
import com.techsensei.domain.use_case.users.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsers: GetUsers
):ViewModel() {

    private val _usersState:MutableState<UsersState> = mutableStateOf(UsersState(isLoading = true))
    val usersState: State<UsersState> = _usersState

    fun getAllUsers(userId:Int){
        if (usersState.value.users!=null) return
        viewModelScope.launch(Dispatchers.IO) {
            getUsers(userId).collectLatest {
                when(it){
                    is Resource.Success->_usersState.value = UsersState(isLoading = false,users = it.data)
                    else -> _usersState.value = UsersState(isLoading = false,error = it.message)
                }
            }
        }
    }

}