package com.techsensei.gupp.main.chat.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.Resource
import com.techsensei.domain.use_case.chat.GetChatMessages
import com.techsensei.domain.use_case.chat.RegisterChatEvent
import com.techsensei.domain.use_case.chat.SendMessage
import com.techsensei.gupp.utils.constants.ArgConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ChatMessagesViewModel"

@HiltViewModel
class ChatMessagesViewModel @Inject constructor(
    private val getChatMessages: GetChatMessages,
    private val sendChatMessage: SendMessage,
    private val registerChatEvent: RegisterChatEvent,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roomId: Int = savedStateHandle.get<Int>(ArgConstants.ROOM_ID_ARG)!!
    private val _chatMessagesState: MutableState<ChatMessagesState> =
        mutableStateOf(ChatMessagesState(isLoading = true))

    val chatMessagesState: State<ChatMessagesState> = _chatMessagesState
//    private val _chatEvent : MutableState<Chat?> = mutableStateOf(null)
//    val chatEvent : State<Chat?> = _chatEvent


    init {
        getCurrentChatMessages()
    }

    fun sendMessage(chat: Chat) {
        viewModelScope.launch (Dispatchers.IO){
            sendChatMessage(chat).collectLatest {

            }
        }
    }

    private fun getCurrentChatMessages() {
        viewModelScope.launch {
            getChatMessages(roomId!!).collectLatest {
                when (it) {
                    is Resource.Loading -> _chatMessagesState.value =
                        chatMessagesState.value.copy(isLoading = true)
                    is Resource.Success -> _chatMessagesState.value =
                        chatMessagesState.value.copy(isLoading = false, messages = mutableStateOf(it.data!!))
                    is Resource.Error -> _chatMessagesState.value =
                        chatMessagesState.value.copy(
                            isLoading = false,
                            error = it.message,
                            messages = null
                        )
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun registerChatChannel(roomId : Int, currentUserId:Int){
        viewModelScope.launch (Dispatchers.IO){
            registerChatEvent(roomId,currentUserId).collectLatest {
                Log.d(TAG, "registerChatChannel: ${it.data?.toString()}")
                when(it){
                    is Resource.Success->
                       addMessageToChat(it.data!!)
                }
            }
        }
    }


    fun addMessageToChat(
        newMessage: Chat
    ) {
        val tempList = mutableListOf<Chat>()
        tempList.addAll(_chatMessagesState.value.messages!!.value!!)
        tempList.add(newMessage)
        _chatMessagesState.value.messages!!.value = tempList
    }

}