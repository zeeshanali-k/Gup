package com.techsensei.gup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techsensei.gup.ui.splash.SplashScreen
import com.techsensei.gup.ui.theme.GupTheme
import com.techsensei.gup.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

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

@Composable
fun AppFlow(navController: NavHostController) {
    NavHost(navController = navController,startDestination = Screen.SplashScreen().title){
//        Splash
        composable(Screen.SplashScreen().route){
            SplashScreen(navController)
        }
//        Login Register Screen
        composable(Screen.LoginRegisterScreen().route){
            SplashScreen(navController)
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
