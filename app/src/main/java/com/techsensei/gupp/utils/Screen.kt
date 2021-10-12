package com.techsensei.gupp.utils

import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.techsensei.gupp.utils.constants.ArgConstants

sealed class Screen(val title: String, val route: String) {

    object SplashScreen : Screen("Splash", "route_splash")

    //    Authentication screens
    object LoginRegisterScreen : Screen("Login / Register", "route_login_register")
    object ForgetPassword : Screen("Forget Password", "route_forget_password")
    object ChangePassword : Screen("Change Password", "route_change_password?{${ArgConstants.USER_ID}}")

//    Main flow screens
    object HomeScreen:Screen("Home", "route_home")
    object ChatScreen:Screen("Chat", "route_chat_screen/{${ArgConstants.ROOM_ID_ARG}}"){
        fun getArgsWithType() =
            listOf(navArgument(ArgConstants.ROOM_ID_ARG) {
                type = NavType.IntType
                nullable = false
                defaultValue = -1
            })


        fun getRouteWithArgument(roomId:Int) = "route_chat_screen/$roomId"
    }
}
