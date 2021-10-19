package com.techsensei.data.repository

import android.util.Log
import com.techsensei.data.network.NotificationsClient
import com.techsensei.data.network.dto.toNotification
import com.techsensei.domain.model.Notification
import com.techsensei.domain.model.Resource
import com.techsensei.domain.repository.NotificationsRepository
import java.lang.Exception

class NotificationsRepositoryImpl(private val notificationsClient: NotificationsClient) :
    NotificationsRepository {

    private val TAG = "NotificationsRepository"

    override suspend fun getNotification(): Resource<List<Notification>> {
        return try {
            Resource
                .Success(notificationsClient.getNotification()
                    .map { it.toNotification() }
                )
        } catch (e: Exception) {
            Log.d(TAG, "getNotification: " + e.cause)
            Log.d(TAG, "getNotification: " + e.localizedMessage)
            Resource.Error("Failed to get notification")
        }
    }
}