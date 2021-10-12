package com.techsensei.gupp.main.chat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.main.chat.components.ChatsListItem
import com.techsensei.gupp.main.chat.components.GoToTopButton
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.IconImageColor
import com.techsensei.gupp.ui.theme.PrimaryDark
import com.techsensei.gupp.utils.PrefsProvider
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.constants.ArgConstants
import com.techsensei.gupp.utils.constants.PrefConstants
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
//        if (chatsState.value.chats!=null) return@LaunchedEffect
        chatsViewModel.getUserChats(
            PrefsProvider(context)
                .getInt(PrefConstants.USER_ID)
        )
    }
    val coroutineScope = rememberCoroutineScope()
    if (!chatsState.value.isLoading) {
        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = chatsState) {
            chatsState.value.message?.let {
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
            floatingActionButton = {
                if (chatsState.value.message == null) {
                    FloatingActionButton(onClick = {
                        Toast.makeText(
                            context,
                            "Message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_chat_24),
                            contentDescription = "Start New Chat", tint = IconImageColor
                        )
                    }
                }
            }
        ) {
            if (chatsState.value.message == null) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(PrimaryDark)
                ) {
                    val lazyListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(bottom = 60.dp)
                    ) {
                        items(chatsState.value.chats?: listOf()) {room->
                            ChatsListItem(onItemClick = {
                                navController.currentBackStackEntry?.arguments =
                                    Bundle().apply {
                                        putParcelable(ArgConstants.ROOM_ARG, room)
                                    }
                                Log.d(TAG, "ChatsTab: "+Screen.ChatScreen.getRouteWithArgument(it))
                                navController.navigate(Screen.ChatScreen.getRouteWithArgument(it)){
                                    this.anim {
                                        this.enter = R.anim.onesignal_fade_in
                                        this.exit = R.anim.onesignal_fade_out
                                        this.popEnter = R.anim.onesignal_fade_out
                                        this.popExit = R.anim.onesignal_fade_in
                                    }
                                }
                            }, room = room)
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
    } else if (chatsState.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
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
