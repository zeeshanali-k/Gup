package com.techsensei.gupp.main.chat.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.techsensei.domain.model.Room
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.*
import com.techsensei.gupp.utils.constants.AppConstants

@ExperimentalCoilApi
@Composable
fun ChatsListItem(onItemClick: (id: Int) -> Unit, room: Room, currentUserId: Int) {

    Box(
        Modifier
            .fillMaxWidth()
            .background(AppBg)
            .padding(5.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .clickable { onItemClick(room.id) }
                .background(ItemBg)
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberImagePainter(
                    data = room.user.profileImage ?: R.drawable.man_avatar
                ),
                contentDescription = "Chat Person DP",
                Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(PrimaryDark)
                    .padding(5.dp)
            )

            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )

            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = room.user.name!!,
                    style = Typography.h2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Log.d(TAG, "ChatsListItem: ${room.user.id}")
                    Log.d(TAG, "ChatsListItem: $currentUserId")
                    Text(
                        text =
                        (if (room.lastMessage!!.user.id == currentUserId)
                            "You: "
                        else
                            "${room.user.name}: ") +
                        room.lastMessage!!.message,

                        style = Typography.caption,
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(3f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = AppConstants.getTimeFromDate(room.lastMessage!!.messageTime!!),
                        style = Typography.caption,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(1f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                }


            }

        }
    }
}

@Composable
fun ChatHeader(letter: String) {
    Text(
        text = letter,
        style = Typography.caption,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBg2)
            .padding(5.dp)
    )
}
private const val TAG = "ChatsListItem"
//
//@Preview
//@Composable
//fun ChatItemTest() {
//    ChatItem(user = User("Zeeshan Ali", profileImageTest = R.drawable.man_avatar))
//}