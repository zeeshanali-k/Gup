package com.techsensei.gupp.main.chat.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.*

@Composable
fun ChatItem(onItemClick:(id:Int)->Unit,user: User) {

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
                .padding(7.dp)
                .clickable { onItemClick(user.id!!) },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = user.profileImageTest),
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
                    text = user.name!!,
                    fontWeight = Typography.h2.fontWeight,
                    fontSize = Typography.h2.fontSize,
                    fontFamily = Typography.h2.fontFamily,
                    color = Typography.h2.color,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )

            }

        }
    }
}

@Composable
fun ChatHeader(letter:String) {
    Text(
        text = letter,
        style = Typography.caption,
        modifier = Modifier.fillMaxWidth()
            .background(AppBg2)
            .padding(5.dp)
    )
}
//
//@Preview
//@Composable
//fun ChatItemTest() {
//    ChatItem(user = User("Zeeshan Ali", profileImageTest = R.drawable.man_avatar))
//}