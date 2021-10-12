package com.techsensei.gupp.main.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.domain.model.Resource
import com.techsensei.domain.use_case.chat.GetAllChats
import com.techsensei.gupp.utils.constants.ArgConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getAllChats: GetAllChats,
//    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _chatsState: MutableState<ChatsState> = mutableStateOf(ChatsState(isLoading = true))
    val chatsState: State<ChatsState> = _chatsState
    private val TAG = "ChatsViewModel"

    fun getUserChats(userId: Int) {
//        val userId = savedStateHandle.get<Int>(ArgConstants.USER_ID)!!
//        Log.d(TAG, "user id: $userId")
        _chatsState.value = ChatsState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getAllChats(1).collectLatest {
                when (it) {
                    is Resource.Success -> _chatsState.value = chatsState.value
                        .copy(
                            isLoading = false,
                            chats = it.data
                        )

                    is Resource.Error -> _chatsState.value =
                        _chatsState.value.copy(
                            isLoading = false,
                            message = it.message
                        )
                    is Resource.Loading -> _chatsState.value =
                        chatsState.value.copy(
                            isLoading = true
                        )
                }
            }
        }
    }

}