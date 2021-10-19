package com.techsensei.gupp.main.notifications

import com.techsensei.domain.model.Notification

data class NotificationsState(
    val isLoading: Boolean = false,
    val notifications: List<Notification>? = null,
    val error:String? = null
)