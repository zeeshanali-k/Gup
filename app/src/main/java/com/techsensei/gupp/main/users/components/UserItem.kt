package com.techsensei.gupp.main.users.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.*

@ExperimentalCoilApi
@Composable
fun UserItem(user: User, onItemClick: (user: User) -> Unit) {

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
                .background(ItemBg)
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberImagePainter(
                    data = user.profileImage ?: R.drawable.man_avatar
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

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = user.name!!,
                    style = Typography.h2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(3f),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_chat_24),
                    contentDescription = "Start Chat",
                    tint = IconImageColor,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(ButtonColor)
                        .padding(5.dp)
                        .clickable {
                            onItemClick(user)
                        }
                )

            }

        }
    }
}
private const val TAG = "UserItem"
