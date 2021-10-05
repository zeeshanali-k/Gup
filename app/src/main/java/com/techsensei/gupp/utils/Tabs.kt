package com.techsensei.gupp.utils

sealed class Tabs(val title:String, val route:String){

    object ChatsTab:Tabs("Chats","route_chats_tab")
    object UsersListTab:Tabs("Users","route_users_tab")
    object NotificationsTab:Tabs("Alerts","route_notifications_tab")
    object ProfileTab:Tabs("Profile","route_profile_tab")

}
