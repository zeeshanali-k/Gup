package com.techsensei.gupp.main.chat.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techsensei.domain.model.Chat
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.AppBg2
import com.techsensei.gupp.ui.theme.SecondaryDark
import com.techsensei.gupp.ui.theme.Typography

@Composable
fun ChatItem(chat: Chat,currentUserId:Int) {
//    val context = LocalContext.current
    val isCurrentUser by remember {
        mutableStateOf(currentUserId == chat.user.id)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .background(AppBg)
            .padding(vertical = 2.dp),
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
    ) {

        Box(
            Modifier
                .fillMaxWidth(0.80f)
                .drawBehind {
                    itemBg(7.dp, 7.dp, this, isCurrentUser)
                }
                .padding(6.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = chat.user.name!!,
                    style = Typography.h4,
                    color = SecondaryDark,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 2.dp),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = chat.message, style = Typography.h4,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        Text(
            text = chat.messageTime!!,
            style = Typography.h5,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .offset(x = if (isCurrentUser) (-5).dp else 5.dp)
        )

    }
}


//@Composable
fun itemBg(cornerSizeX: Dp, cornerSizeY: Dp, drawScope: DrawScope, isCurrentUser: Boolean) {
    drawScope.apply {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(cornerSizeX.toPx(), cornerSizeY.toPx())
            lineTo(size.width, cornerSizeY.toPx())
            lineTo(size.width, size.height)
            lineTo(cornerSizeX.toPx(), size.height)
            lineTo(cornerSizeX.toPx(), cornerSizeY.toPx() * 2)
            close()
        }

        rotate(if (isCurrentUser) 180f else 0f) {
            clipPath(path = path) {
                drawRoundRect(
                    color = AppBg2,
                    size = size,
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun ShowBg() {
//    ChatItem(
//        chat = Chat(
//            User(name = "Zeeshan Ali", id = 1), message = "Hello! How Are You!",
//            messageTime = "12-5-2021"
//        )
//    )
//}