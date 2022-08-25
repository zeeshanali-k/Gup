package com.techsensei.gupp.main.chat.chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.*

@ExperimentalCoilApi
@Composable
fun ChatScreenToolbar(user: User,onBackClicked:()->Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
            contentDescription = "Back",
            tint = TextColor,
            modifier = Modifier.clickable { onBackClicked() }
        )

        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = rememberImagePainter(user.profileImage ?: R.drawable.man_avatar),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(50))
                .background(PrimaryDark),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(10.dp))
        Text(text = user.name!!, style = Typography.h2)

    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun ShowChatScreenToolbar(){
    ChatScreenToolbar(user = User("Zeeshan Ali",
        profileImageTest = R.drawable.man_avatar)){

    }
}