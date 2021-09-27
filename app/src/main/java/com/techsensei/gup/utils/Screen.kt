package com.techsensei.gup.utils

import com.techsensei.gup.utils.constants.ArgConstants

sealed class Screen(val title:String,val route:String){

    class SplashScreen:Screen("Splash", "route_splash")
//    Authentication screens
    class LoginRegisterScreen:Screen("Login / Register","route_login_register")
    class ForgetPassword :Screen("Forget Password", "route_forget_password")
    class ChangePassword:Screen("Change Password", "route_change_password?{${ArgConstants.USER_ID}}")

//    Main flow screens
//    class HomeScreen:Screen(title, route)
//    class ChatScreen:Screen(title, route)
//    class ChatsListScreen:Screen(title, route)
//    class ProfileScreen(title: String, route: String) :Screen(title, route)
//    class FriendsScreen(title: String, route: String) :Screen(title, route)
////    For searching users
//    class SearchScreen(title: String, route: String) :Screen(title, route)
}
