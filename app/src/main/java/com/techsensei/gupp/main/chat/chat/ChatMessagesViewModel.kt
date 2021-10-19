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
import com.techsensei.domain.use_case.chat.VerifyChat
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
    private val verifyChat: VerifyChat,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var roomId: Int = savedStateHandle.get<Int>(ArgConstants.ROOM_ID_ARG)!!
    private val _chatMessagesState: MutableState<ChatMessagesState> =
        mutableStateOf(ChatMessagesState(isLoading = true))

    val chatMessagesState: State<ChatMessagesState> = _chatMessagesState
//    private val _chatEvent : MutableState<Chat?> = mutableStateOf(null)
//    val chatEvent : State<Chat?> = _chatEvent


    init {
        if (roomId!=0)
            getCurrentChatMessages()
    }

    fun sendMessage(newMessage: Chat) {
        newMessage.roomId = roomId
        viewModelScope.launch (Dispatchers.IO){
            sendChatMessage(newMessage).collectLatest {
//                TODO
            }
        }
    }

    private fun getCurrentChatMessages() {
        if (roomId==0){

        }
        viewModelScope.launch {
            getChatMessages(roomId).collectLatest {
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
    fun verifyUsersChat(userId:Int, chatUserId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            verifyChat(userId, chatUserId).collectLatest {
                when(it){
                    is Resource.Success->{
                        roomId = it.data!!.id
                        getCurrentChatMessages()
                        registerChatChannel(userId)
                    }
                    is Resource.Error->{
                        _chatMessagesState.value =
                            ChatMessagesState(isLoading = false,error = it.message)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun registerChatChannel(currentUserId:Int){
        if (roomId==0)
            return
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
        newMessage.roomId = roomId
        val tempList = mutableListOf<Chat>()
        tempList.addAll(_chatMessagesState.value.messages!!.value!!)
        tempList.add(newMessage)
        _chatMessagesState.value.messages!!.value = tempList
    }

}