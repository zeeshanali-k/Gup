package com.techsensei.data.network

import com.techsensei.data.network.dto.NotificationDto
import retrofit2.http.GET

interface NotificationsClient {

    @GET("get-notifications")
    suspend fun getNotification():List<NotificationDto>

}