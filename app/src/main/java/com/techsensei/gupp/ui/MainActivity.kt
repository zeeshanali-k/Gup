package com.techsensei.gupp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techsensei.gupp.authentication.login_register.LoginRegisterScreen
import com.techsensei.gupp.main.home.HomeScreen
import com.techsensei.gupp.ui.splash.SplashScreen
import com.techsensei.gupp.ui.theme.GupTheme
import com.techsensei.gupp.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GupTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    AppFlow(navController)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun AppFlow(navController: NavHostController) {
    NavHost(navController = navController,startDestination = Screen.SplashScreen.route){
//        Splash
        composable(Screen.SplashScreen.route){
            SplashScreen(navController)
        }
//        Login Register Screen
        composable(Screen.LoginRegisterScreen.route){
            LoginRegisterScreen(navController)
        }
//        Home Screen
        composable(Screen.HomeScreen.route){
            HomeScreen(navController)
        }

    }
}
/*

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GupTheme {
        Greeting("Android")
    }
}*/
