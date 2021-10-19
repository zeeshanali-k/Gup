package com.techsensei.gupp.main.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.techsensei.gupp.R
import com.techsensei.gupp.main.notifications.components.NotificationItem
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.PrimaryDark
import com.techsensei.gupp.ui.theme.Typography

@Composable
fun NotificationsTab(
    navController: NavController,
    notificationsViewModel: NotificationsViewModel = hiltViewModel()
) {
    val notificationsState = notificationsViewModel.notificationsState

    Box(modifier = Modifier
        .fillMaxSize()
        .background(AppBg)){
        when {
//            Loading
            notificationsState.value.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
//            Notifications list
            notificationsState.value.error==null -> {
                LazyColumn(){
                    items(notificationsState.value.notifications!!){
                        NotificationItem(it)
                    }
                }
            }
//            Error
            else -> {
                ErrorBody(Modifier.align(Alignment.Center),notificationsState.value.error!!)
            }
        }
    }
}

@Composable
fun ErrorBody(modifier: Modifier,error:String) {
    Column(
        modifier = modifier,
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
            text = error,
            style = Typography.h2
        )

    }
}
