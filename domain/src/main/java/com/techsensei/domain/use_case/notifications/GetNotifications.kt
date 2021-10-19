package com.techsensei.domain.use_case.notifications

import com.techsensei.domain.model.Notification
import com.techsensei.domain.model.Resource
import com.techsensei.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotifications(private val notificationsRepository: NotificationsRepository) {

    operator fun invoke():Flow<Resource<List<Notification>>> = flow{
        emit(notificationsRepository.getNotification())
    }

}