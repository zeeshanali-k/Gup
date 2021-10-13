package com.techsensei.gupp.main.chat.chat

import android.os.Parcel
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.techsensei.data.network.dto.toUser
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.Room
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.main.chat.ChatsState
import com.techsensei.gupp.main.chat.chat.components.ChatScreenToolbar
import com.techsensei.gupp.main.chat.chat.components.MessagesSection
import com.techsensei.gupp.main.chat.chat.components.MessageInput
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.Typography
import com.techsensei.gupp.utils.PrefsProvider
import com.techsensei.gupp.utils.constants.AppConstants
import com.techsensei.gupp.utils.constants.ArgConstants
import com.techsensei.gupp.utils.constants.PrefConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

private const val TAG = "ChatMessagesScreen"

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun ChatMessagesScreen(
    navController: NavController,
    chatMessagesViewModel: ChatMessagesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val prefsProvider by remember {
        mutableStateOf(PrefsProvider(context))
    }
    val currentUserId by remember {
        mutableStateOf(prefsProvider.getInt(PrefConstants.USER_ID))
    }
    val currentUserName by remember {
        mutableStateOf(prefsProvider.getString(PrefConstants.USER_NAME)!!)
    }
    val coroutineScope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()
    val chatMessagesState = chatMessagesViewModel.chatMessagesState

    val room =
        navController.previousBackStackEntry?.arguments!!.getParcelable<Room>(ArgConstants.ROOM_ARG)!!
    LaunchedEffect(key1 = true) {
        chatMessagesViewModel.registerChatChannel(room.id, currentUserId)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            ChatScreenToolbar(user = room.user) {
                navController.navigateUp()
            }
        },
        bottomBar = {
            ChatMessagesBottomBar(
                chatMessagesState, chatMessagesViewModel, room, coroutineScope,
                lazyColumnState,
                currentUserId,
                currentUserName
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBg), contentAlignment = Alignment.Center
        ) {
            if (chatMessagesState.value.isLoading) {
                CircularProgressIndicator()
            } else if (chatMessagesState.value.error == null) {
                MessagesSection(
                    Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = it.calculateBottomPadding(),
                            top = it.calculateTopPadding()
                        ), lazyColumnState,
                    chatMessagesState.value.messages?.value ?: listOf(),
                    coroutineScope, currentUserId
                )
            } else {
                Text(text = "No messages found", style = Typography.h2, color = Color.Red)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ChatMessagesBottomBar(
    chatMessagesState: State<ChatMessagesState>,
    chatMessagesViewModel: ChatMessagesViewModel,
    room: Room,
    coroutineScope: CoroutineScope,
    lazyColumnState: LazyListState,
    currentUserId : Int,
    currentUserName : String
) {

    AnimatedVisibility(
        !chatMessagesState.value.isLoading, enter = slideIn({
            IntOffset(0, it.height)
        }),
        exit = slideOut({
            IntOffset(0, it.height)
        })
    ) {
        MessageInput(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            onSendClicked = {
//                chatMessagesViewModel.chatMessagesState
                val newMessage = Chat(
                    room.user.copy(id = currentUserId,name = currentUserName),
                    it,
                    AppConstants.getCurrentTimeAndDate(),
                    room.id
                )
                chatMessagesViewModel.addMessageToChat(newMessage)
                coroutineScope.launch {
                    chatMessagesState.value.messages?.let {
                        it.value?.let { chatsList ->
                            lazyColumnState.scrollToItem(chatsList.size - 1)
                        }
                    }
                    chatMessagesViewModel.sendMessage(newMessage)
                }
            })
    }
}

