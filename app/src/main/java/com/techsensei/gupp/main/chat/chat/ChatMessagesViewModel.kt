package com.techsensei.gupp.main.chat.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.Resource
import com.techsensei.domain.use_case.chat.GetChatMessages
import com.techsensei.domain.use_case.chat.SendMessage
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.constants.ArgConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "ChatMessagesViewModel"

@HiltViewModel
class ChatMessagesViewModel @Inject constructor(
    private val getChatMessages: GetChatMessages,
    private val sendChatMessage: SendMessage,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roomId: Int = savedStateHandle.get<Int>(ArgConstants.ROOM_ID_ARG)!!
    private val _chatMessagesState: MutableState<ChatMessagesState> =
        mutableStateOf(ChatMessagesState(isLoading = true))

    private val _chatEvent : MutableState<Chat?> = mutableStateOf(null)
    val chatEvent : State<Chat?> = _chatEvent

    val chatMessagesState: State<ChatMessagesState> = _chatMessagesState

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

    fun registerChatChannel(roomId : Int){
        val options = PusherOptions()
        options.setCluster("ap2")

        val pusher = Pusher("81067ddc08c9872c59aa", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.d(TAG, "State changed from ${change.previousState} to ${change.currentState}")
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.d(TAG, "There was a problem connecting! code ($code), message ($message), exception($e)")
            }
        }, ConnectionState.ALL)


        val channel = pusher.subscribe("my-channel")
        channel.bind("App\\Events\\ChatEvent\\${roomId}") { event ->
            Log.d(TAG, "Received Data: "+event.data)
            Log.d(TAG, "Received Data: "
                    + Gson().fromJson(event.data, Chat::class.java).toString())

        }
    }

}