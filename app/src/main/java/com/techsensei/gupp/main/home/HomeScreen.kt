package com.techsensei.gupp.main.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techsensei.gupp.main.chat.ChatsTab
import com.techsensei.gupp.main.home.components.HomeBottomBar
import com.techsensei.gupp.main.home.components.HomeTopBar
import com.techsensei.gupp.main.notifications.NotificationsTab
import com.techsensei.gupp.main.profile.ProfileTab
import com.techsensei.gupp.main.users.UsersTab
import com.techsensei.gupp.utils.Tabs

@ExperimentalAnimationApi
@Composable
fun HomeScreen(mainNavController: NavController) {

    val navController = rememberNavController()
    val tabsSelection = remember {
        mutableStateOf(TabSelection(isChatsTab = true))
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            HomeBottomBar(tabsSelection,navController)
        }) {
        NavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
            startDestination = Tabs.ChatsTab.route
        ) {
            composable(Tabs.ChatsTab.route) {
                ChatsTab(mainNavController)
            }
            composable(Tabs.UsersListTab.route) {
                UsersTab(navController)
            }
            composable(Tabs.NotificationsTab.route) {
                NotificationsTab(navController)
            }
            composable(Tabs.ProfileTab.route) {
                ProfileTab(navController)
            }
        }

    }

}