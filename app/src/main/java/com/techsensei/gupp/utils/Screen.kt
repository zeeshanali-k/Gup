package com.techsensei.gupp.utils

import com.techsensei.gupp.utils.constants.ArgConstants

sealed class Screen(val title: String, val route: String) {

    object SplashScreen : Screen("Splash", "route_splash")

    //    Authentication screens
    object LoginRegisterScreen : Screen("Login / Register", "route_login_register")
    object ForgetPassword : Screen("Forget Password", "route_forget_password")
    object ChangePassword : Screen("Change Password", "route_change_password?{${ArgConstants.USER_ID}}")

//    Main flow screens
    object HomeScreen:Screen("Home", "route_home")
//    class ChatScreen:Screen(title, route)
//    class ChatsListScreen:Screen(title, route)
//    class ProfileScreen(title: String, route: String) :Screen(title, route)
//    class FriendsScreen(title: String, route: String) :Screen(title, route)
////    For searching users
//    class SearchScreen(title: String, route: String) :Screen(title, route)
}
