package com.techsensei.domain.repository

import com.techsensei.domain.model.Notification
import com.techsensei.domain.model.Resource

interface NotificationsRepository {

    suspend fun getNotification():Resource<List<Notification>>;

}