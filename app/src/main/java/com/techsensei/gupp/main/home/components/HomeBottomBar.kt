package com.techsensei.gupp.main.home.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.techsensei.gupp.R
import com.techsensei.gupp.main.home.TabSelection
import com.techsensei.gupp.ui.theme.Primary
import com.techsensei.gupp.ui.theme.PrimaryDark
import com.techsensei.gupp.ui.theme.Typography
import com.techsensei.gupp.utils.Tabs
import com.techsensei.gupp.utils.navigator

private const val TAG = "HomeBottomBar"

@Composable
fun HomeBottomBar(tabsSelection: MutableState<TabSelection>,navController: NavController) {
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        destination.route?.let {
            if (it == Tabs.ChatsTab.route) {
                tabsSelection.value = TabSelection(isChatsTab = true)
            }
        }
    }
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Primary
    ) {
        Log.d(TAG, "HomeBottomBar: " + tabsSelection.value.isChatsTab)
        BottomTab(
            selected = tabsSelection.value.isChatsTab, rowScope = this, title = Tabs.ChatsTab.title,
            painter = painterResource(id = R.drawable.ic_round_chat_24)
        ) {
            if (tabsSelection.value.isChatsTab) return@BottomTab
            tabsSelection.value = TabSelection(isChatsTab = true)
            navController.navigateUp()
        }

        BottomTab(
            selected = tabsSelection.value.isUsersTab,
            title = Tabs.UsersListTab.title,
            painter = painterResource(id = R.drawable.ic_baseline_supervised_user_circle_24),
            rowScope = this
        ) {
            if (tabsSelection.value.isUsersTab) return@BottomTab
            tabsSelection.value = TabSelection(isUsersTab = true)
            navController.navigator(Tabs.UsersListTab.route,popUpTo = Tabs.ChatsTab.route,false)
        }

        BottomTab(
            title = Tabs.NotificationsTab.title,
            painter = painterResource(id = R.drawable.ic_baseline_circle_notifications_24),
            selected = tabsSelection.value.isNotificationsTab, rowScope = this
        ) {
            if (tabsSelection.value.isNotificationsTab) return@BottomTab
            tabsSelection.value = TabSelection(isNotificationsTab = true)
            navController.navigator(Tabs.NotificationsTab.route,popUpTo = Tabs.ChatsTab.route,false)
        }

        BottomTab(
            title = Tabs.ProfileTab.title,
            painter = painterResource(id = R.drawable.ic_baseline_person_pin_24),
            selected = tabsSelection.value.isProfileTab, rowScope = this
        ) {
            if (tabsSelection.value.isProfileTab) return@BottomTab
            tabsSelection.value = TabSelection(isProfileTab = true)
            navController.navigator(Tabs.ProfileTab.route,popUpTo = Tabs.ChatsTab.route,false)
        }
    }
}

@Composable
fun BottomTab(
    title: String,
    painter: Painter,
    rowScope: RowScope,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    rowScope.apply {
        BottomNavigationItem(
            selected = selected,
            onClick = onItemClick,
            icon = {
                Icon(
                    painter = painter,
                    contentDescription = "Tab Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(PrimaryDark)
                        .padding(3.dp)
                )
            },
            label = {
                Text(
                    text = title, fontFamily = Typography.h2.fontFamily,
//                    fontSize = Typography.h2.fontSize,
//                    fontWeight = Typography.h2.fontWeight,
                    color = Typography.h2.color
                )
            },
            alwaysShowLabel = false
        )
    }
}