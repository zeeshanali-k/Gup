package com.techsensei.gupp.ui.splash

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.Primary
import com.techsensei.gupp.utils.PrefsProvider
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.constants.PrefConstants
import com.techsensei.gupp.utils.navigator
import kotlinx.coroutines.delay

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(navController: NavHostController) {
    Log.d(TAG, "SplashScreen: ")
    var size by remember {
        mutableStateOf(50.dp)
    }

    val context = LocalContext.current
//    val prefsProvider by remember {
//        mutableStateOf(PrefsProvider(context))
//    }
    LaunchedEffect(key1 = true) {
//        delay(200)
        size = 150.dp
        delay(200)
        size = 100.dp
        delay(1000)
//        Log.d(TAG, "In SplashScreen: isLoggedIn: ${prefsProvider.getBool(PrefConstants.IS_LOGGED_IN)}")
//        if (prefsProvider.getBool(PrefConstants.IS_LOGGED_IN)) {
//            navController.navigator(
//                Screen.HomeScreen.route, Screen.SplashScreen.route,
//                true
//            )
//        } else {
            navController.navigator(
                Screen.LoginRegisterScreen.route, Screen.SplashScreen.route,
                true
            )
//        }
    }

    Log.d(TAG, "SplashScreen:Out ")
//    Log.d(TAG, "Out SplashScreen: isLoggedIn: ${prefsProvider.getBool(PrefConstants.IS_LOGGED_IN)}")
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
//
//@Preview
//@Composable
//fun RunSplash() {
//    SplashScreen(rememberNavController())
//}