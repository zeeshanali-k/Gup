package com.techsensei.gupp.main.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.techsensei.domain.model.Notification
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.*

@Composable
fun NotificationItem(notification: Notification) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(ItemBg)
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_circle_notifications_24),
            contentDescription = "Notification",
            tint = Secondary,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(50))
                .background(ItemBg)
                .padding(5.dp)
                .weight(1f)
        )

        Column(
            modifier = Modifier
                .weight(6f)
                .padding(5.dp)
        ) {
            Text(
                text = notification.title,
                style = Typography.h2,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = notification.notification,
                style = Typography.h4,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

//@Preview
//@Composable
//fun ShowNotificationItem() {
//    NotificationItem(
//        notification = Notification(
//            1,
//            "Test Notification dataTest Notification dataTest Notification dataTest Notification dataTest Notification dataTest Notification dataTest Notification dataTest Notification dataTest Notification data",
//            "Test Notification",
//            "12-12-2021"
//        )
//    )
//}
