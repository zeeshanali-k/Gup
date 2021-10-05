package com.techsensei.gupp.main.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.main.chat.components.ChatHeader
import com.techsensei.gupp.main.chat.components.ChatItem
import com.techsensei.gupp.main.chat.components.GoToTopButton
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.IconImageColor
import com.techsensei.gupp.ui.theme.PrimaryDark
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalAnimationApi
@Composable
fun ChatsTab(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg),
        scaffoldState = rememberScaffoldState(),
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_chat_24),
                    contentDescription = "Start New Chat", tint = IconImageColor
                )
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(PrimaryDark)
        ) {
            val lazyListState = rememberLazyListState()
            val dummyChatData = getDummyChatData()
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                val groupedChats = dummyChatData.groupBy { it.name!![0] }
                groupedChats.forEach { (initial, chatUsers) ->
                    item {
                        ChatHeader(letter = initial.toString())
                    }
                    items(chatUsers) {
                        ChatItem(onItemClick = {}, user = it)
                    }
                }
            }
//            Go to top button
            val isNotFirstItem = lazyListState.firstVisibleItemIndex > 0
            AnimatedVisibility(
                visible = isNotFirstItem,
                modifier =Modifier.align(Alignment.BottomCenter).padding(5.dp)
            ) {
                GoToTopButton {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                }
            }

        }
    }
}

fun getDummyChatData(): List<User> {
    val usersDummy = mutableListOf<User>()
    for (i in 0..10) {
        usersDummy.add(User(
            "User " + i,
            profileImageTest = getRandomImage(),
            id = i
        ))
    }
    return usersDummy
}

fun getRandomImage(): Int {
    return if (Random.nextInt(1, 2) == 1) R.drawable.man_avatar else R.drawable.woman_avatar
}
