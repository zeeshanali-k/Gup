package com.techsensei.gupp.main.users

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.techsensei.domain.model.Room
import com.techsensei.gupp.R
import com.techsensei.gupp.main.users.components.UserItem
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.PrimaryDark
import com.techsensei.gupp.ui.theme.TextColor
import com.techsensei.gupp.ui.theme.Typography
import com.techsensei.gupp.utils.PrefsProvider
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.constants.ArgConstants
import com.techsensei.gupp.utils.constants.PrefConstants

@ExperimentalCoilApi
@Composable
fun UsersTab(
    navController: NavController,
    usersViewModel: UsersViewModel = hiltViewModel()
) {
    val userState = usersViewModel.usersState
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        usersViewModel.getAllUsers(
            PrefsProvider(context).getInt(PrefConstants.USER_ID)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
    ) {

        when {
            userState.value.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            userState.value.error?.isNotEmpty() == true -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_supervised_user_circle_24),
                        contentDescription = "No Users",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = userState.value.error!!,
                        style = Typography.h2
                    )

                }
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(userState.value.users!!) {
                        UserItem(user = it) { u ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                ArgConstants.ROOM_ARG,
                                Room(0, u, null)
                            )
                            navController.navigate(Screen.ChatScreen.getRouteWithArgument(0)) {
                                this.anim {
                                    this.enter = R.anim.onesignal_fade_in
                                    this.exit = R.anim.onesignal_fade_out
                                    this.popEnter = R.anim.onesignal_fade_out
                                    this.popExit = R.anim.onesignal_fade_in
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}