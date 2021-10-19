package com.techsensei.data.network.dto

import com.techsensei.domain.model.Notification

data class NotificationDto(val id:Int,val notification:String,val title:String,val created_at:String)

fun NotificationDto.toNotification() =
    Notification(this.id,this.notification,this.title,this.created_at)