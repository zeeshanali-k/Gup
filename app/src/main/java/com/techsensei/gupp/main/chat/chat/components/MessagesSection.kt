package com.techsensei.gupp.main.chat.chat.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.techsensei.domain.model.Chat
import com.techsensei.domain.model.User
import com.techsensei.gupp.utils.constants.AppConstants
import kotlinx.coroutines.launch
import kotlin.random.Random

//TOdo move message input to scaffold in @[ChatMessagesScreen.kt]
@ExperimentalAnimationApi
@Composable
fun MessagesSection(
    modifier: Modifier,
    lazyColumnState: LazyListState,
    chatList: List<Chat>?
) {
    Column(modifier) {
        chatList?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f), state = lazyColumnState
            ) {
                items(it) {
                    ChatItem(chat = it)
                }
            }
        }
    }

}

private const val TAG = "ChatSection"