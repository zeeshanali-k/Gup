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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.main.chat.components.ChatsListItem
import com.techsensei.gupp.main.chat.components.GoToTopButton
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.PrimaryDark
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.constants.ArgConstants
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val TAG = "ChatsTab"

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun ChatsTab(navController: NavController, chatsViewModel: ChatsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val chatsState = chatsViewModel.chatsState

    LaunchedEffect(key1 = true) {
        chatsViewModel.getUserChats()
    }

    val coroutineScope = rememberCoroutineScope()

    chatsState.value?.let { chatsStateVal ->
            val scaffoldState = rememberScaffoldState()
            LaunchedEffect(key1 = chatsState) {
                chatsStateVal.message?.let {
                    if (it.isEmpty().not()) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            it, actionLabel = context.getString(R.string.dismiss),
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppBg),
                scaffoldState = scaffoldState,
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(PrimaryDark)
                ) {

                    if (chatsStateVal.isLoading) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    } else if (chatsStateVal.message == null) {
                        val lazyListState = rememberLazyListState()
                        LazyColumn(
                            state = lazyListState,
                            contentPadding = PaddingValues(bottom = 60.dp)
                        ) {
                            items(chatsStateVal.chats ?: listOf()) { room ->
                                ChatsListItem(onItemClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(ArgConstants.ROOM_ARG, room)
                                    navController.navigate(Screen.ChatScreen.getRouteWithArgument(it)) {
                                        this.anim {
                                            this.enter = R.anim.onesignal_fade_in
                                            this.exit = R.anim.onesignal_fade_out
                                            this.popEnter = R.anim.onesignal_fade_out
                                            this.popExit = R.anim.onesignal_fade_in
                                        }
                                    }
                                }, room = room, currentUserId = chatsViewModel.currentUserId)
                            }
                        }
//            Go to top button
                        val isNotFirstItem = lazyListState.firstVisibleItemIndex > 0
                        AnimatedVisibility(
                            visible = isNotFirstItem,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(5.dp)
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
        }
}

fun getDummyChatData(): List<User> {
    val usersDummy = mutableListOf<User>()
    for (i in 0..10) {
        usersDummy.add(
            User(
                "User " + i,
                profileImageTest = getRandomImage(),
                id = i
            )
        )
    }
    return usersDummy
}

fun getRandomImage(): Int {
    return if (Random.nextInt(1, 2) == 1) R.drawable.man_avatar else R.drawable.woman_avatar
}
