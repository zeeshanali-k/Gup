package com.techsensei.gupp.main.chat.chat.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techsensei.domain.model.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//TOdo move message input to scaffold in @[ChatMessagesScreen.kt]
@ExperimentalAnimationApi
@Composable
fun MessagesSection(
    modifier: Modifier,
    lazyColumnState: LazyListState,
    chatList: List<Chat>?,
    coroutineScope: CoroutineScope,
    currentUserId:Int
) {
    Column(modifier) {
        chatList?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f), state = lazyColumnState
            ) {
                items(it) {
                    ChatItem(chat = it,currentUserId)
                }
            }
            coroutineScope.launch {
                if (chatList.isNotEmpty())
                    lazyColumnState.scrollToItem(chatList.size - 1)
            }
        }
    }

}

private const val TAG = "ChatSection"