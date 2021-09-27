package com.techsensei.gup.ui.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.techsensei.gup.R
import com.techsensei.gup.ui.theme.Primary
import com.techsensei.gup.utils.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    var size by remember {
        mutableStateOf(0.dp)
    }

    LaunchedEffect(key1 = true) {
        delay(200)
        size = 50.dp
        navController.navigate(Screen.LoginRegisterScreen().route)
    }

    val logoSize = animateDpAsState(targetValue = size)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App LOGO",
            modifier = Modifier
                .padding(10.dp)
                .size(logoSize.value)
        )
    }
}

@Preview
@Composable
fun RunSplash() {
    SplashScreen(rememberNavController())
}